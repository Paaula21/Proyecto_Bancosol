/*
Paula Fernández Jiménez: 75%
IA: 25%
*/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.dto.FormularioColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
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
    private final VistaColaboradoresRepository vistaColaboradoresRepository;
    private final ZonaGeograficaRepository zonaGeograficaRepository;
    private final DivisionTerritorialRepository divisionTerritorialRepository;
    private final CodigoPostalRepository codigoPostalRepository;
    private final PersonaRepository personaRepository;
    private final VistaColaboradoresMapper vistaColaboradoresMapper;


    @Transactional(readOnly = true)
    public List<ColaboradorDTO> listarColaboradoresDTO(String busqueda, String zona) {
        String busquedaNormalizada = busqueda == null ? "" : busqueda;
        String zonaNormalizada = zona == null || zona.isEmpty() ? TODAS_LAS_ZONAS : zona;

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

    @Transactional
    public void guardarColaborador(FormularioColaboradorDTO dto) {
        // Ponemos final para que se guarde correctamente el valor deseado
        final ZonaGeografica zona;
        if (dto.getNombreZona() != null && !dto.getNombreZona().isEmpty()) {
            zona = zonaGeograficaRepository.findByNombreZona(dto.getNombreZona())
                    .orElseGet(() -> {
                        ZonaGeografica z = new ZonaGeografica();
                        z.setIdZona((int) (System.currentTimeMillis() % 100000));
                        z.setNombreZona(dto.getNombreZona());
                        return zonaGeograficaRepository.save(z);
                    });
        } else {
            zona = null;
        }

        final DivisionTerritorial division;
        if (dto.getNombreDivision() != null && !dto.getNombreDivision().isEmpty()) {
            division = divisionTerritorialRepository.findByNombreDivision(dto.getNombreDivision())
                    .orElseGet(() -> {
                        DivisionTerritorial d = new DivisionTerritorial();
                        d.setIdDivision((int) (System.currentTimeMillis() % 100000));
                        d.setNombreDivision(dto.getNombreDivision());
                        d.setTipo(false);
                        d.setZona(zona);
                        return divisionTerritorialRepository.save(d);
                    });
        } else {
            division = null;
        }

        CodigoPostal cp = null;
        if (dto.getCodigoPostal() != null && !dto.getCodigoPostal().isEmpty()) {
            cp = codigoPostalRepository.findByCodigo(dto.getCodigoPostal())
                    .orElseGet(() -> {
                        CodigoPostal c = new CodigoPostal();
                        c.setIdCp((int) (System.currentTimeMillis() % 100000));
                        c.setCodigo(dto.getCodigoPostal());
                        c.setDivision(division);
                        return codigoPostalRepository.save(c);
                    });
        }

        Colaborador colaborador = null;
        if (dto.getIdColaborador() != null && !dto.getIdColaborador().isEmpty()) {
            colaborador = colaboradorRepository.findById(dto.getIdColaborador()).orElse(null);
        }

        Direccion direccion;
        if (colaborador != null && colaborador.getDireccion() != null) {
            direccion = colaborador.getDireccion();
        } else {
            direccion = new Direccion();
            direccion.setIdDireccion((int) (System.currentTimeMillis() % 100000));
        }
        direccion.setTipoVia(dto.getTipoVia());
        direccion.setNombreVia(dto.getNombreVia());
        direccion.setNumero(dto.getNumero());
        direccion.setCp(cp);
        direccion = direccionRepository.save(direccion);

        if (colaborador == null) {
            colaborador = new Colaborador();
            colaborador.setIdColaborador("COL-" + (System.currentTimeMillis() % 1000000));
        }
        colaborador.setNombreColaborador(dto.getNombreColaborador());
        colaborador.setObservaciones(dto.getObservaciones());
        colaborador.setDireccion(direccion);
        colaborador = colaboradorRepository.save(colaborador);

        if (dto.getContactoNombre() != null && !dto.getContactoNombre().isEmpty()) {
            ContactoColaborador contacto = contactoColaboradorRepository.findByColaborador(colaborador).orElse(null);
            Persona persona;

            if (contacto != null) {
                persona = contacto.getPersona();
            } else {
                contacto = new ContactoColaborador();
                contacto.setColaborador(colaborador);
                contacto.setEsPrincipal(true);
                persona = new Persona();
            }

            persona.setNombreCompleto(dto.getContactoNombre());
            persona.setEmail(dto.getContactoEmail());
            persona.setTelefono(dto.getContactoTel());
            persona = personaRepository.save(persona);

            contacto.setPersona(persona);
            contactoColaboradorRepository.save(contacto);
        }
    }

    @Transactional
    public void eliminarColaboradorCompleto(String idColaborador) {
        Colaborador colab = colaboradorRepository.findById(idColaborador).orElse(null);
        if (colab != null) {
            contactoColaboradorRepository.findByColaborador(colab)
                    .ifPresent(contactoColaboradorRepository::delete);

            colaboradorRepository.delete(colab);
        }
    }



    @Transactional(readOnly = true)
    public Optional<Colaborador> obtenerColaboradorEntidad(String id) {
        return this.colaboradorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ContactoColaborador> obtenerContactoPorColaborador(Colaborador colaborador) {
        return this.contactoColaboradorRepository.findByColaborador(colaborador);
    }

    @Transactional(readOnly = true)
    public long contarColaboradores() {
        return this.colaboradorRepository.count();
    }
}