/*
Ainhoa García Rebollo: 100%
*/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.CoberturaZonaDTO;
import es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import es.uma.tesaw.proyecto_bancosol.mapper.EstablecimientoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;
    private final CadenaRepository cadenaRepository;
    private final DireccionRepository direccionRepository;
    private final CodigoPostalRepository codigoPostalRepository;
    private final DivisionTerritorialRepository divisionTerritorialRepository;
    private final ZonaGeograficaRepository zonaGeograficaRepository;
    private final AsignacionCoordinadorRepository asignacionCoordinadorRepository;
    private final EstablecimientoMapper establecimientoMapper;

    public List<EstablecimientoDTO> listarTiendas(String idCadena, String nombre, String idCampana,
                                                    String tipoVia, String nombreVia, String codigo,
                                                    String localidad, Integer idZona, String coordinador) {
        String cadenaFiltro = normalizarFiltro(idCadena);
        String nombreFiltro = normalizarFiltro(nombre);
        String campanaFiltro = normalizarFiltro(idCampana);
        String tipoViaFiltro = normalizarFiltro(tipoVia);
        String nombreViaFiltro = normalizarFiltro(nombreVia);
        String codigoFiltro = normalizarFiltro(codigo);
        String localidadFiltro = normalizarFiltro(localidad);
        String coordinadorFiltro = normalizarFiltro(coordinador);

        List<Establecimiento> lista = establecimientoRepository.findAllFiltrados(
                cadenaFiltro, nombreFiltro, campanaFiltro, tipoViaFiltro,
                nombreViaFiltro, codigoFiltro, localidadFiltro, idZona);

        List<Integer> ids = lista.stream().map(Establecimiento::getIdEstablecimiento).collect(Collectors.toList());
        Map<Integer, List<AsignacionCoordinador>> asignacionesPorTienda;
        if (ids.isEmpty()) {
            asignacionesPorTienda = Map.of();
        } else {
            List<AsignacionCoordinador> todas = asignacionCoordinadorRepository.findAll().stream()
                    .filter(a -> a.getTienda() != null && ids.contains(a.getTienda().getIdEstablecimiento()))
                    .collect(Collectors.toList());
            asignacionesPorTienda = todas.stream()
                    .collect(Collectors.groupingBy(a -> a.getTienda().getIdEstablecimiento()));
        }

        List<EstablecimientoDTO> dtos = new ArrayList<>();
        for (Establecimiento e : lista) {
            List<AsignacionCoordinador> asigs = asignacionesPorTienda.getOrDefault(e.getIdEstablecimiento(), List.of());
            EstablecimientoDTO dto = establecimientoMapper.toDTO(e, asigs);
            if (coordinadorFiltro != null) {
                if (dto.getCoordinadorNombre() == null) continue;
                if (!dto.getCoordinadorNombre().toLowerCase().contains(coordinadorFiltro.toLowerCase())) continue;
            }
            dtos.add(dto);
        }

        return dtos;
    }

    public List<EstablecimientoDTO> listarTiendas() {
        return listarTiendas(null, null, null, null, null, null, null, null, null);
    }

    public List<Establecimiento> buscarEstablecimientosPorCampanaConFiltros(String idCampana, String nombreCadena, Integer idTienda) {
        String cadenaFiltro = (nombreCadena != null) ? nombreCadena.trim() : null;
        return establecimientoRepository.buscarEstablecimientosFiltrados(idCampana, cadenaFiltro, idTienda);
    }

    public EstablecimientoDTO buscarTienda(Integer idEstablecimiento) {
        if (idEstablecimiento == null) return new EstablecimientoDTO();
        Establecimiento entidad = establecimientoRepository.findById(idEstablecimiento).orElse(null);
        if (entidad == null) return new EstablecimientoDTO();
        List<AsignacionCoordinador> asigs = asignacionCoordinadorRepository.findAll().stream()
                .filter(a -> a.getTienda() != null && a.getTienda().getIdEstablecimiento().equals(idEstablecimiento))
                .collect(Collectors.toList());
        return establecimientoMapper.toDTO(entidad, asigs);
    }

    @Transactional
    public void guardarTienda(Integer idEstablecimiento, String idCadena, String nombreResena,
                              Integer lineales, String tipoVia, String nombreVia, String numero,
                              String codigoPostal, String localidad, Integer idZona) {

        Cadena cadena = cadenaRepository.findById(idCadena)
                .orElseThrow(() -> new IllegalArgumentException("Cadena no encontrada: " + idCadena));

        ZonaGeografica zona = zonaGeograficaRepository.findById(idZona)
                .orElseThrow(() -> new IllegalArgumentException("Zona no encontrada: " + idZona));

        List<DivisionTerritorial> divisiones = divisionTerritorialRepository.buscarPorNombre(localidad);
        DivisionTerritorial division;
        if (!divisiones.isEmpty()) {
            division = divisiones.get(0);
        } else {
            division = new DivisionTerritorial();
            Integer maxId = divisionTerritorialRepository.findAll().stream()
                    .mapToInt(DivisionTerritorial::getIdDivision)
                    .max().orElse(0);
            division.setIdDivision(maxId + 1);
            division.setNombreDivision(localidad);
            division.setTipo(false);
            division.setZona(zona);
            divisionTerritorialRepository.save(division);
        }
        final DivisionTerritorial divisionFinal = division;

        CodigoPostal cp = codigoPostalRepository.findByCodigo(codigoPostal)
                .orElseGet(() -> {
                    CodigoPostal nuevoCp = new CodigoPostal();
                    Integer maxId = codigoPostalRepository.findAll().stream()
                            .mapToInt(CodigoPostal::getIdCp)
                            .max().orElse(0);
                    nuevoCp.setIdCp(maxId + 1);
                    nuevoCp.setCodigo(codigoPostal);
                    nuevoCp.setDivision(divisionFinal);
                    return codigoPostalRepository.save(nuevoCp);
                });

        Direccion direccion = direccionRepository.findAll().stream()
                .filter(d -> d.getTipoVia() != null && d.getTipoVia().equals(tipoVia)
                        && d.getNombreVia().equals(nombreVia)
                        && d.getNumero() != null && d.getNumero().equals(numero)
                        && d.getCp() != null && d.getCp().getIdCp().equals(cp.getIdCp()))
                .findFirst()
                .orElseGet(() -> {
                    Direccion nuevaDir = new Direccion();
                    Integer maxId = direccionRepository.findAll().stream()
                            .mapToInt(Direccion::getIdDireccion)
                            .max().orElse(0);
                    nuevaDir.setIdDireccion(maxId + 1);
                    nuevaDir.setTipoVia(tipoVia);
                    nuevaDir.setNombreVia(nombreVia);
                    nuevaDir.setNumero(numero);
                    nuevaDir.setCp(cp);
                    return direccionRepository.save(nuevaDir);
                });

        Establecimiento tienda;
        if (idEstablecimiento == null) {
            tienda = new Establecimiento();
            tienda.setIdEstablecimiento(establecimientoRepository.findMaxId() + 1);
        } else {
            tienda = establecimientoRepository.findById(idEstablecimiento)
                    .orElse(new Establecimiento());
        }

        tienda.setCadena(cadena);
        tienda.setNombreResena(nombreResena);
        tienda.setLineales(lineales);
        tienda.setDireccion(direccion);

        establecimientoRepository.save(tienda);
    }

    @Transactional
    public void borrarTienda(Integer idEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada: " + idEstablecimiento));
        establecimientoRepository.delete(establecimiento);
    }

    private static String normalizarFiltro(String valor) {
        if (valor == null) return null;
        String trim = valor.trim();
        return trim.isEmpty() ? null : trim;
    }

    @Transactional(readOnly = true)
    public long contarEstablecimientos() {
        return this.establecimientoRepository.count();
    }

    @Transactional(readOnly = true)
    public List<CoberturaZonaDTO> obtenerCoberturaPorZona() {
        List<Object[]> conteoPorZona = this.establecimientoRepository.countEstablecimientosPorZona();
        List<CoberturaZonaDTO> coberturas = new ArrayList<>();

        if (!conteoPorZona.isEmpty()) {
            Long maxTiendas = (Long) conteoPorZona.get(0)[1];

            for (Object[] fila : conteoPorZona) {
                String nombreZona = (String) fila[0];
                Long tiendas = (Long) fila[1];
                int porcentaje = (int) Math.round((tiendas.doubleValue() / maxTiendas) * 100);
                coberturas.add(new CoberturaZonaDTO(nombreZona, tiendas, porcentaje));
            }
        }
        return coberturas;
    }
}
