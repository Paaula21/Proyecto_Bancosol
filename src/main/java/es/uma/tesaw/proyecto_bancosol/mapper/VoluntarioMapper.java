package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.VoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import org.springframework.stereotype.Component;

@Component
public class VoluntarioMapper extends MapperDTO<VoluntarioDTO, Voluntario> {

    @Override
    public VoluntarioDTO toDTO(Voluntario entity) {
        if (entity == null) return null;

        VoluntarioDTO dto = new VoluntarioDTO();
        dto.setIdVoluntario(String.valueOf(entity.getIdVoluntario()));
        dto.setPreferenciaHorario(entity.getDisponibilidad());

        if (entity.getPersona() != null) {
            dto.setIdPersona(String.valueOf(entity.getPersona().getIdPersona()));
            dto.setNombreVoluntario(entity.getPersona().getNombreCompleto());
        }

        if (entity.getIdVoluntario() != null) {
            dto.setIdVoluntario(String.valueOf(entity.getIdVoluntario()));
        }

        return dto;
    }
}
