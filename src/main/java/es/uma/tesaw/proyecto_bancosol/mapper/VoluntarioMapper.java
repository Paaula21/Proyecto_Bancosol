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
        dto.setIdVoluntario(entity.getIdVoluntario());
        dto.setPreferenciaHorario(entity.getPreferenciaHorario());

        if (entity.getPersona() != null) {
            dto.setIdPersona(entity.getPersona().getIdPersona());
            dto.setNombreVoluntario(entity.getPersona().getNombreCompleto());
        }

        if (entity.getColaborador() != null) {
            dto.setIdColaborador(entity.getColaborador().getIdColaborador());
            dto.setNombreColaborador(entity.getColaborador().getNombreColaborador());
        }

        return dto;
    }
}
