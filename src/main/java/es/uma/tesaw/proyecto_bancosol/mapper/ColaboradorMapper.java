package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import org.springframework.stereotype.Component;

@Component
public class ColaboradorMapper extends MapperDTO<ColaboradorDTO, Colaborador> {

    @Override
    public ColaboradorDTO toDTO(Colaborador entity) {
        if (entity == null) return null;

        ColaboradorDTO dto = new ColaboradorDTO();
        dto.setIdColaborador(entity.getIdColaborador());
        dto.setNombreColaborador(entity.getNombreColaborador());
        dto.setObservaciones(entity.getObservaciones());
        dto.setIdDireccion(entity.getDireccion() != null ? entity.getDireccion().getIdDireccion() : null);

        return dto;
    }
}
