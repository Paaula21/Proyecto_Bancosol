package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.ColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoluntariosService {

    private final VoluntarioRepository voluntarioRepository;
    private final PersonaRepository personaRepository;
    private final ColaboradorRepository colaboradorRepository;

    private final VoluntarioMapper voluntarioMapper;

    // Listar

    @Transactional(readOnly = true)
    public List<VoluntarioDTO> listarVoluntarios() {
        List<Voluntario> voluntarios = this.voluntarioRepository.findAll();
        return this.voluntarioMapper.toDTOList(voluntarios);
    }

    // Buscar por ID

    @Transactional(readOnly = true)
    public Optional<VoluntarioDTO> obtenerVoluntario(Integer id) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);
        return Optional.ofNullable(this.voluntarioMapper.toDTO(voluntario));
    }

    // Crear

    @Transactional
    public VoluntarioDTO crearVoluntario(VoluntarioDTO dto) {
        Persona persona = this.buscarPersonaObligatoria(dto.getIdPersona());

        Colaborador colaborador = null;
        if (dto.getIdColaborador() != null && !dto.getIdColaborador().isBlank()) {
            colaborador = this.colaboradorRepository.findById(dto.getIdColaborador()).orElse(null);
        }

        Voluntario voluntario = new Voluntario();
        voluntario.setPersona(persona);
        voluntario.setPreferenciaHorario(dto.getPreferenciaHorario());
        voluntario.setColaborador(colaborador);

        Voluntario guardado = this.voluntarioRepository.save(voluntario);
        return this.voluntarioMapper.toDTO(guardado);
    }

    // Actualizar

    @Transactional
    public Optional<VoluntarioDTO> actualizarVoluntario(Integer id, VoluntarioDTO dto) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);

        if (voluntario == null) {
            return Optional.empty();
        }

        this.aplicarCambios(voluntario, dto);
        Voluntario actualizado = this.voluntarioRepository.save(voluntario);
        return Optional.ofNullable(this.voluntarioMapper.toDTO(actualizado));
    }

    // Eliminar

    @Transactional
    public boolean eliminarVoluntario(Integer id) {
        if (!this.voluntarioRepository.existsById(id)) {
            return false;
        }
        this.voluntarioRepository.deleteById(id);
        return true;
    }


    @Transactional
    public boolean eliminarVoluntarioConPersona(Integer id) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);
        if (voluntario == null) {
            return false;
        }

        Persona persona = voluntario.getPersona();
        this.voluntarioRepository.delete(voluntario);

        if (persona != null) {
            this.personaRepository.delete(persona);
        }

        return true;
    }

    //Guardar


    @Transactional
    public void guardarVoluntario(
            Integer id,
            String nombre_completo,
            String email,
            String telefono,
            String disponibilidad) {

        Voluntario voluntario;
        Persona persona;

        if (id == null) {
            // Si el ID es nulo, estamos creando un nuevo registro
            voluntario = new Voluntario();
            persona = new Persona();
        } else {
            // Si el ID existe, lo buscamos para editarlo
            voluntario = voluntarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe el voluntario con ID: " + id));
            persona = voluntario.getPersona();
            if (persona == null) {
                persona = new Persona();
            }
        }

        persona.setNombreCompleto(nombre_completo);
        persona.setEmail(email);
        persona.setTelefono(telefono);
        voluntario.setPreferenciaHorario(disponibilidad);

        Persona personaGuardada = personaRepository.save(persona);

        voluntario.setPersona(personaGuardada);
        voluntario.setPreferenciaHorario(disponibilidad);

        voluntarioRepository.save(voluntario);
    }


    // Otros

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
            Colaborador colaborador = this.colaboradorRepository
                    .findById(dto.getIdColaborador())
                    .orElse(null);
            voluntario.setColaborador(colaborador);
        }
    }

    private Persona buscarPersonaObligatoria(Integer idPersona) {
        return this.personaRepository.findById(idPersona)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una persona con id " + idPersona));
    }
}
