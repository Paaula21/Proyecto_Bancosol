/**
 * Archivo Service, que se encarga de las diferentes operaciones CRUD respecto a los colaboradores
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.dto.FormularioColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import es.uma.tesaw.proyecto_bancosol.mapper.PersonaMapper;
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
    private final PersonaMapper personaMapper;


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
            ContactoColaborador contacto = contactoColaboradorRepository.findByColaborador(colaborador);

            if (contacto == null) {
                contacto = new ContactoColaborador();
                contacto.setColaborador(colaborador);
                contacto.setEsPrincipal(true);
            }

            Persona persona = null;

            if (dto.getContactoEmail() != null && !dto.getContactoEmail().isEmpty()) {
                persona = personaRepository.findByEmail(dto.getContactoEmail()).orElse(null);
            }

            if (persona == null && contacto.getPersona() != null) {
                persona = contacto.getPersona();
            }

            if (persona == null) {
                persona = new Persona();
            }

            persona.setNombreCompleto(dto.getContactoNombre());
            persona.setEmail(dto.getContactoEmail());
            persona.setTelefono(dto.getContactoTel());

            persona = personaRepository.save(persona);

            contacto.setPersona(persona);
            contactoColaboradorRepository.save(contacto);

        } else {
            ContactoColaborador contacto = contactoColaboradorRepository.findByColaborador(colaborador);

            if (contacto != null) {
                Persona persona = contacto.getPersona();

                contacto.setPersona(null);
                contactoColaboradorRepository.save(contacto);

                colaborador.setContacto(null);
                colaboradorRepository.save(colaborador);

                contactoColaboradorRepository.delete(contacto);

                if (persona != null) {
                    personaRepository.delete(persona);
                }
            }
        }
    }
    public void eliminarColaboradorCompleto(String idColaborador) {
        Colaborador colab = colaboradorRepository.findById(idColaborador).orElse(null);
        if (colab != null) {
            contactoColaboradorRepository.findByColaborador(colab);

            colaboradorRepository.delete(colab);
        }
    }

    //Funciones específicas
    public Optional<Colaborador> obtenerColaboradorEntidad(String id) {
        return this.colaboradorRepository.findById(id);
    }

    public ContactoColaborador obtenerContactoPorColaborador(Colaborador colaborador) {
        return this.contactoColaboradorRepository.findByColaborador(colaborador);
    }

    public long contarColaboradores() {
        return this.colaboradorRepository.count();
    }
}