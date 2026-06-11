package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import es.uma.tesaw.proyecto_bancosol.mapper.VoluntarioMapper;
import es.uma.tesaw.proyecto_bancosol.mapper.VistaVoluntarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional(readOnly = true)
    public List<VistaVoluntarioDTO> listarVoluntariosFiltrados(String nombre, String email, String telefono, String disponibilidad) {
        String nombreNorm = nombre == null ? "" : nombre.trim().toLowerCase();
        String emailNorm = email == null ? "" : email.trim().toLowerCase();
        String telefonoNorm = telefono == null ? "" : telefono.trim();
        String dispNorm = disponibilidad == null ? "" : disponibilidad.trim().toLowerCase();

        List<VistaVoluntarios> vistas = this.vistaVoluntarioRepository.findAll();

        List<VistaVoluntarios> vistasFiltradas = vistas.stream()
                .filter(v -> nombreNorm.isEmpty() || (v.getNombreCompleto() != null && v.getNombreCompleto().toLowerCase().contains(nombreNorm)))
                .filter(v -> emailNorm.isEmpty() || (v.getEmail() != null && v.getEmail().toLowerCase().contains(emailNorm)))
                // SOLUCIÓN CONTAINS: Convertimos el teléfono a String por si es de tipo Integer/Long
                .filter(v -> telefonoNorm.isEmpty() || (v.getTelefono() != null && String.valueOf(v.getTelefono()).contains(telefonoNorm)))
                // NOTA: Si getDisponibilidad() sigue dando error, cámbialo por getPreferenciaHorario() dependiendo de tu entidad
                .filter(v -> dispNorm.isEmpty() || (v.getDisponibilidad() != null && v.getDisponibilidad().toLowerCase().contains(dispNorm)))
                .collect(Collectors.toList());

        // SOLUCIÓN MAPPER: Cambiado a mayúsculas (toDTOList)
        return this.vistaVoluntarioMapper.toDTOList(vistasFiltradas);
    }

    @Transactional
    public void guardarVoluntario(Integer id, String nombreCompleto, String email, String telefono, String disponibilidad) {
        Voluntario voluntario = null;
        Persona persona = null;

        if (id != null) {
            voluntario = voluntarioRepository.findById(id).orElse(null);
            if (voluntario != null) {
                persona = voluntario.getPersona();
            }
        }

        if (persona == null) {
            persona = new Persona();
        }

        persona.setNombreCompleto(nombreCompleto);
        persona.setEmail(email);
        persona.setTelefono(telefono);
        persona = personaRepository.save(persona);

        if (voluntario == null) {
            voluntario = new Voluntario();
        }

        voluntario.setPersona(persona);
        voluntario.setPreferenciaHorario(disponibilidad);

        voluntarioRepository.save(voluntario);
    }

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


    @Transactional(readOnly = true)
    public List<VoluntarioDTO> listarVoluntarios(String nombre, String email, String telefono, String disponibilidad) {
        List<Voluntario> voluntarios = this.filtrarVoluntariosEntidad(nombre, email, telefono, disponibilidad);
        return this.voluntarioMapper.toDTOList(voluntarios);
    }

    @Transactional(readOnly = true)
    public Optional<VistaVoluntarioDTO> obtenerVoluntario(Integer id) {
        return this.vistaVoluntarioRepository.findById(id)
                // SOLUCIÓN MAPPER: Cambiado a mayúsculas (toDTO)
                .map(this.vistaVoluntarioMapper::toDTO);
    }

    @Transactional
    public VoluntarioDTO crearVoluntario(VoluntarioDTO dto) {
        Voluntario voluntario = new Voluntario();
        voluntario.setPreferenciaHorario(dto.getPreferenciaHorario());

        if (dto.getIdPersona() != null) {
            voluntario.setPersona(this.buscarPersonaObligatoria(dto.getIdPersona()));
        }

        if (dto.getIdColaborador() != null && !dto.getIdColaborador().isBlank()) {
            voluntario.setColaborador(this.colaboradorRepository.findById(dto.getIdColaborador()).orElse(null));
        }

        Voluntario voluntarioGuardado = this.voluntarioRepository.save(voluntario);
        return this.voluntarioMapper.toDTO(voluntarioGuardado);
    }

    @Transactional
    public Optional<VoluntarioDTO> actualizarVoluntario(Integer id, VoluntarioDTO dto) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);

        if (voluntario != null) {
            this.aplicarCambios(voluntario, dto);
            Voluntario voluntarioActualizado = this.voluntarioRepository.save(voluntario);
            return Optional.ofNullable(this.voluntarioMapper.toDTO(voluntarioActualizado));
        }

        return Optional.empty();
    }

    @Transactional
    public boolean eliminarVoluntario(Integer id) {
        if (!this.voluntarioRepository.existsById(id)) {
            return false;
        }

        this.voluntarioRepository.deleteById(id);
        return true;
    }


    @Transactional(readOnly = true)
    public List<Voluntario> filtrarVoluntariosEntidad(String nombre, String email, String telefono, String disponibilidad) {
        String nombreNorm = nombre == null ? "" : nombre.trim().toLowerCase();
        String emailNorm = email == null ? "" : email.trim().toLowerCase();
        String telefonoNorm = telefono == null ? "" : telefono.trim();
        String dispNorm = disponibilidad == null ? "" : disponibilidad.trim().toLowerCase();

        List<Voluntario> voluntarios = this.voluntarioRepository.findAll();

        return voluntarios.stream()
                .filter(v -> nombreNorm.isEmpty() || (v.getPersona() != null && v.getPersona().getNombreCompleto() != null && v.getPersona().getNombreCompleto().toLowerCase().contains(nombreNorm)))
                .filter(v -> emailNorm.isEmpty() || (v.getPersona() != null && v.getPersona().getEmail() != null && v.getPersona().getEmail().toLowerCase().contains(emailNorm)))
                // SOLUCIÓN CONTAINS: Convertimos el teléfono a String en la entidad normal
                .filter(v -> telefonoNorm.isEmpty() || (v.getPersona() != null && v.getPersona().getTelefono() != null && String.valueOf(v.getPersona().getTelefono()).contains(telefonoNorm)))
                .filter(v -> dispNorm.isEmpty() || (v.getPreferenciaHorario() != null && v.getPreferenciaHorario().toLowerCase().contains(dispNorm)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Voluntario> obtenerVoluntarioEntidad(Integer id) {
        return this.voluntarioRepository.findById(id);
    }

    @Transactional
    public Voluntario guardarVoluntarioEntidad(Voluntario voluntario) {
        return this.voluntarioRepository.save(voluntario);
    }


    private void aplicarCambios(Voluntario voluntario, VoluntarioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El voluntario es obligatorio");
        }

        if (dto.getPreferenciaHorario() != null) {
            voluntario.setPreferenciaHorario(dto.getPreferenciaHorario());
        }

        if (dto.getIdPersona() != null) {
            voluntario.setPersona(this.buscarPersonaObligatoria(dto.getIdPersona()));
        }

        if (dto.getIdColaborador() != null) {
            Colaborador colaborador = this.colaboradorRepository.findById(dto.getIdColaborador()).orElse(null);
            voluntario.setColaborador(colaborador);
        }
    }

    private Persona buscarPersonaObligatoria(Integer idPersona) {
        return this.personaRepository.findById(idPersona)
                .orElseThrow(() -> new IllegalArgumentException("No existe persona con id " + idPersona));
    }
}