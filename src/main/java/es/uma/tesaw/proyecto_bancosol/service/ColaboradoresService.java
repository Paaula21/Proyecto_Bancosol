
package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VistaColaboradoresDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Direccion;
import es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores;
import es.uma.tesaw.proyecto_bancosol.mapper.ColaboradorMapper;
import es.uma.tesaw.proyecto_bancosol.mapper.VistaColaboradoresMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ColaboradoresService {

    private static final String TODAS_LAS_ZONAS = "Todas";

    private final ColaboradorRepository colaboradorRepository;
    private final ContactoColaboradorRepository contactoColaboradorRepository;
    private final DireccionRepository direccionRepository;
    private final ColaboradorMapper colaboradorMapper;
    private final VistaColaboradoresMapper vistaColaboradoresMapper;
    private final VistaColaboradoresRepository vistaColaboradoresRepository;
    private final ZonaGeograficaRepository zonaGeograficaRepository;

    @Transactional(readOnly = true)
    public List<VistaColaboradoresDTO> listarColaboradoresDesdeVistaDTO(String busqueda, String zona) {
        String busquedaNormalizada = busqueda == null ? "" : busqueda;
        String zonaNormalizada = zona == null || zona.isBlank() ? TODAS_LAS_ZONAS : zona;

        List<VistaColaboradores> vistas;

        if (busquedaNormalizada.isEmpty() && TODAS_LAS_ZONAS.equals(zonaNormalizada)) {
            vistas = this.vistaColaboradoresRepository.findAll();
        } else if (TODAS_LAS_ZONAS.equals(zonaNormalizada)) {
            vistas = this.vistaColaboradoresRepository.findByNombreColaboradorContainingIgnoreCase(busquedaNormalizada);
        } else if (busquedaNormalizada.isEmpty()) {
            vistas = this.vistaColaboradoresRepository.findByNombreZona(zonaNormalizada);
        } else {
            vistas = this.vistaColaboradoresRepository.findByNombreColaboradorContainingIgnoreCaseAndNombreZona(busquedaNormalizada, zonaNormalizada);
        }

        return this.vistaColaboradoresMapper.toDTOList(vistas);
    }
    @Transactional(readOnly = true)
    public List<ColaboradorDTO> listarColaboradores (String busqueda, String zona) {
        List<Colaborador> colaboradores = this.filtrarColaboradoresEntidad(busqueda, zona);
        return this.colaboradorMapper.toDTOList(colaboradores);
    }

    @Transactional(readOnly = true)
    public Optional<ColaboradorDTO> obtenerColaborador (String id) {
        Colaborador colaborador = this.colaboradorRepository.findById(id).orElse(null);
        return Optional.ofNullable(this.colaboradorMapper.toDTO(colaborador));
    }

    @Transactional
    public ColaboradorDTO crearColaborador (ColaboradorDTO dto) {

        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(dto.getIdColaborador());
        colaborador.setNombreColaborador(dto.getNombreColaborador());
        colaborador.setObservaciones(dto.getObservaciones());
        colaborador.setDireccion(this.buscarDireccionObligatoria(dto.getIdDireccion()));

        Colaborador colaboradorGuardado = this.colaboradorRepository.save(colaborador);
        return this.colaboradorMapper.toDTO(colaboradorGuardado);
    }

    @Transactional
    public Optional<ColaboradorDTO> actualizarColaborador (String id, ColaboradorDTO dto) {
        Colaborador colaborador = this.colaboradorRepository.findById(id).orElse(null);

        if (colaborador != null) {
            this.aplicarCambios(colaborador, dto);
            Colaborador colaboradorActualizado = this.colaboradorRepository.save(colaborador);
            return Optional.ofNullable(this.colaboradorMapper.toDTO(colaboradorActualizado));
        }

        return Optional.empty();
    }

    @Transactional
    public boolean eliminarColaborador (String id) {
        if (!this.colaboradorRepository.existsById(id)) {
            return false;
        }

        this.colaboradorRepository.deleteById(id);
        return true;
    }


    @Transactional(readOnly = true)
    public List<Colaborador> filtrarColaboradoresEntidad (String busqueda, String zona) {
        String busquedaNormalizada = busqueda == null ? "" : busqueda;
        String zonaNormalizada = zona == null || zona.isBlank() ? TODAS_LAS_ZONAS : zona;

        if (busquedaNormalizada.isEmpty() && TODAS_LAS_ZONAS.equals(zonaNormalizada)) {
            return this.colaboradorRepository.findAll();
        } else if (TODAS_LAS_ZONAS.equals(zonaNormalizada)) {
            return this.colaboradorRepository.findByNombreColaboradorContainingIgnoreCase(busquedaNormalizada);
        } else if (busquedaNormalizada.isEmpty()) {
            return this.colaboradorRepository.findByZona(zonaNormalizada);
        } else {
            return this.colaboradorRepository.findByNombreColaboradorContainingIgnoreCaseAndZona(busquedaNormalizada, zonaNormalizada);
        }
    }

    private void aplicarCambios (Colaborador colaborador, ColaboradorDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El colaborador es obligatorio");
        }

        if (dto.getNombreColaborador() != null) {
            colaborador.setNombreColaborador(dto.getNombreColaborador());
        }
        colaborador.setObservaciones(dto.getObservaciones());

        if (dto.getIdDireccion() != null) {
            colaborador.setDireccion(this.buscarDireccionObligatoria(dto.getIdDireccion()));
        }
    }

    private Direccion buscarDireccionObligatoria (Integer idDireccion) {
        return this.direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new IllegalArgumentException("No existe direccion con id " + idDireccion));
    }

    @Transactional(readOnly = true)
    public Optional<Colaborador> obtenerColaboradorEntidad (String id) {
        return this.colaboradorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ContactoColaborador> obtenerTodosLosContactos () {
        return this.contactoColaboradorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ContactoColaborador> obtenerContactoPorColaborador (Colaborador colaborador) {
        return this.contactoColaboradorRepository.findByColaborador(colaborador);
    }

    @Transactional
    public Colaborador guardarColaboradorEntidad (Colaborador colaborador) {
        return this.colaboradorRepository.save(colaborador);
    }
}