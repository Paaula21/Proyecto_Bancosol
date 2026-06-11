package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.ColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VistaVoluntariosRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Persona;
import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import es.uma.tesaw.proyecto_bancosol.mapper.VistaVoluntarioMapper;
import es.uma.tesaw.proyecto_bancosol.mapper.VoluntarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoluntariosService {

    private final VoluntarioRepository voluntarioRepository;
    private final PersonaRepository personaRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final VistaVoluntariosRepository vistaVoluntarioRepository;

    private final VoluntarioMapper voluntarioMapper;
    private final VistaVoluntarioMapper vistaVoluntarioMapper;

    // Listado
    public List<VistaVoluntarioDTO> listarVoluntarios(String disponibilidad) {
        List<Voluntario> lista;

        // Si no hay filtro, viene vacío o es la opción de "todos", traemos todo
        if (disponibilidad == null || disponibilidad.trim().isEmpty() || disponibilidad.equalsIgnoreCase("todos") || disponibilidad.equalsIgnoreCase("todos los turnos")) {
            lista = this.voluntarioRepository.findAll();
        } else {
            // Reemplazamos los guiones por espacios para que coincida con la BD
            String busquedaLimpia = disponibilidad.replace("-", " ").trim();
            lista = this.voluntarioRepository.findByDisponibilidadContainingIgnoreCase(busquedaLimpia);
        }

        List<VistaVoluntarioDTO> resultadoVista = new ArrayList<>();
        for (Voluntario v : lista) {
            VistaVoluntarioDTO dto = new VistaVoluntarioDTO();
            if (v.getPersona() != null) {
                dto.setIdPersona(String.valueOf(v.getPersona().getIdPersona()));
                dto.setNombreCompleto(v.getPersona().getNombreCompleto());
                dto.setEmail(v.getPersona().getEmail());

                if (v.getPersona().getTelefono() != null && !v.getPersona().getTelefono().isEmpty()) {
                    dto.setTelefono(Integer.valueOf(v.getPersona().getTelefono()));
                }
            }
            dto.setDisponibilidad(v.getDisponibilidad());
            resultadoVista.add(dto);
        }

        return resultadoVista;
    }

    // Filtrado
    public List<VoluntarioDTO> filtrarVoluntarios (String disponibilidad) { // ¡Ojo! Cambiado de Integer a String
        List<Voluntario> voluntario;

        // Misma lógica para cuando se quiere ver todo
        if (disponibilidad == null || disponibilidad.trim().isEmpty() || disponibilidad.equalsIgnoreCase("todos") || disponibilidad.equalsIgnoreCase("todos los turnos")) {
            voluntario = this.voluntarioRepository.findAll();
        } else {
            voluntario = this.voluntarioRepository.findByDisponibilidadContainingIgnoreCase(disponibilidad.trim());
        }

        return this.voluntarioMapper.toDTOList(voluntario);
    }

    public VoluntarioDTO buscarVoluntario(Integer id) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);
        return this.voluntarioMapper.toDTO(voluntario);
    }



    @Transactional(readOnly = true)
    public Optional<VistaVoluntarioDTO> obtenerVoluntario(Integer id) {
        // La vista usa id_voluntario como String; buscamos por Integer en la tabla real
        // y luego cruzamos con la vista para no perder datos desnormalizados
        return vistaVoluntarioRepository.findById(id)
                .map(vistaVoluntarioMapper::toDTO);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Guardar (crear o actualizar) un voluntario desde el formulario
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public void guardarVoluntario(Integer id, String nombreCompleto,
                                  String email, String telefono,
                                  String disponibilidad) {
        Voluntario voluntario = null;
        Persona    persona    = null;

        if (id != null) {
            voluntario = voluntarioRepository.findById(id).orElse(null);
            if (voluntario != null) {
                persona = voluntario.getPersona();
            }
        }

        if (persona == null) {
            persona = new Persona();
        }

        persona.setNombreCompleto(nombreCompleto != null ? nombreCompleto.trim() : "");
        persona.setEmail(email != null && !email.isBlank() ? email.trim() : null);
        persona.setTelefono(telefono != null && !telefono.isBlank() ? telefono.trim() : null);
        persona = personaRepository.save(persona);

        if (voluntario == null) {
            voluntario = new Voluntario();
        }
        voluntario.setPersona(persona);
        voluntario.setDisponibilidad(
                disponibilidad != null && !disponibilidad.isBlank() ? disponibilidad.trim() : null);

        voluntarioRepository.save(voluntario);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Eliminar voluntario y su persona vinculada
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public void eliminarVoluntarioConPersona(Integer idVoluntario) {
        Voluntario voluntario = voluntarioRepository.findById(idVoluntario).orElse(null);
        if (voluntario != null) {
            Persona persona = voluntario.getPersona();
            voluntarioRepository.delete(voluntario);
            if (persona != null) {
                personaRepository.delete(persona);
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Métodos de bajo nivel (para uso interno / API REST)
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<Voluntario> filtrarVoluntariosEntidad(
            String nombre, String email, String telefono, String disponibilidad) {

        String nombreNorm = nombre      == null ? "" : nombre.trim().toLowerCase();
        String emailNorm  = email       == null ? "" : email.trim().toLowerCase();
        String telNorm    = telefono    == null ? "" : telefono.trim();
        String dispNorm   = disponibilidad == null ? "" : disponibilidad.trim().toLowerCase();

        return voluntarioRepository.findAll().stream()
                .filter(v -> nombreNorm.isEmpty()
                        || (v.getPersona() != null
                        && v.getPersona().getNombreCompleto() != null
                        && v.getPersona().getNombreCompleto().toLowerCase().contains(nombreNorm)))
                .filter(v -> emailNorm.isEmpty()
                        || (v.getPersona() != null
                        && v.getPersona().getEmail() != null
                        && v.getPersona().getEmail().toLowerCase().contains(emailNorm)))
                .filter(v -> telNorm.isEmpty()
                        || (v.getPersona() != null
                        && v.getPersona().getTelefono() != null
                        && v.getPersona().getTelefono().contains(telNorm)))
                .filter(v -> dispNorm.isEmpty()
                        || (v.getDisponibilidad() != null
                        && v.getDisponibilidad().toLowerCase().contains(dispNorm)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Voluntario> obtenerVoluntarioEntidad(Integer id) {
        return voluntarioRepository.findById(id);
    }

    @Transactional
    public VoluntarioDTO crearVoluntario(VoluntarioDTO dto) {
        Voluntario voluntario = new Voluntario();
        voluntario.setDisponibilidad(dto.getPreferenciaHorario());

        if (dto.getIdPersona() != null) {
            voluntario.setPersona(buscarPersonaObligatoria(Integer.valueOf(dto.getIdPersona())));
        }
        if (dto.getIdColaborador() != null && !dto.getIdColaborador().isBlank()) {
            voluntario.setColaborador(
                    colaboradorRepository.findById(dto.getIdColaborador()).orElse(null));
        }

        return voluntarioMapper.toDTO(voluntarioRepository.save(voluntario));
    }

    @Transactional
    public Optional<VoluntarioDTO> actualizarVoluntario(Integer id, VoluntarioDTO dto) {
        return voluntarioRepository.findById(id).map(v -> {
            aplicarCambios(v, dto);
            return voluntarioMapper.toDTO(voluntarioRepository.save(v));
        });
    }

    @Transactional
    public boolean eliminarVoluntario(Integer id) {
        if (!voluntarioRepository.existsById(id)) return false;
        voluntarioRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Voluntario guardarVoluntarioEntidad(Voluntario voluntario) {
        return voluntarioRepository.save(voluntario);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers privados
    // ─────────────────────────────────────────────────────────────────────────

    private void aplicarCambios(Voluntario voluntario, VoluntarioDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO de voluntario es obligatorio");

        if (dto.getPreferenciaHorario() != null) {
            voluntario.setDisponibilidad(dto.getPreferenciaHorario());
        }
        if (dto.getIdPersona() != null) {
            voluntario.setPersona(buscarPersonaObligatoria(Integer.valueOf(dto.getIdPersona())));
        }
        if (dto.getIdColaborador() != null) {
            Colaborador colaborador =
                    colaboradorRepository.findById(dto.getIdColaborador()).orElse(null);
            voluntario.setColaborador(colaborador);
        }
    }

    private Persona buscarPersonaObligatoria(Integer idPersona) {
        return personaRepository.findById(idPersona)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe ninguna persona con id " + idPersona));
    }
}