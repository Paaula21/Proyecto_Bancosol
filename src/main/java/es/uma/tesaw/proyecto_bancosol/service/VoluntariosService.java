/*
Ainhoa García Rebollo: 80%
IA: 20%
*/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.PersonaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VistaVoluntariosRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
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

@Service
@AllArgsConstructor
public class VoluntariosService {

    private final VoluntarioRepository voluntarioRepository;
    private final PersonaRepository personaRepository;
    private final VistaVoluntariosRepository vistaVoluntarioRepository;

    private final VoluntarioMapper voluntarioMapper;
    private final VistaVoluntarioMapper vistaVoluntarioMapper;

    public List<VistaVoluntarioDTO> listarVoluntarios(String disponibilidad) {
        List<Voluntario> lista;

        if (disponibilidad == null) {
            lista = this.voluntarioRepository.findAll();
        } else {
            lista = this.voluntarioRepository.findByDisponibilidadContainingIgnoreCase(disponibilidad);
        }

        List<VistaVoluntarioDTO> resultadoVista = new ArrayList<>();

        for (Voluntario v : lista) {
            VistaVoluntarioDTO dto = new VistaVoluntarioDTO();

            dto.setIdVoluntario(String.valueOf(v.getIdVoluntario()));

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


    public VoluntarioDTO buscarVoluntario(Integer id) {
        Voluntario voluntario = this.voluntarioRepository.findById(id).orElse(null);
        return this.voluntarioMapper.toDTO(voluntario);
    }



    @Transactional(readOnly = true)
    public Optional<VistaVoluntarioDTO> obtenerVoluntario(Integer id) {
        return vistaVoluntarioRepository.findById(id)
                .map(vistaVoluntarioMapper::toDTO);
    }


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
        persona = personaRepository.save(persona);

        if (voluntario == null) {
            voluntario = new Voluntario();
        }
        voluntario.setPersona(persona);
        voluntario.setDisponibilidad(
                disponibilidad != null && !disponibilidad.isBlank() ? disponibilidad.trim() : null);

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

}