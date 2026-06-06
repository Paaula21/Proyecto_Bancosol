package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.TipoNotificacionDTO;
import es.uma.tesaw.proyecto_bancosol.entities.TipoNotificacion;
import org.springframework.stereotype.Component;

@Component
public class TipoNotificacionMapper extends MapperDTO<TipoNotificacionDTO, TipoNotificacion> {

    @Override
    public TipoNotificacionDTO toDTO(TipoNotificacion entity) {
        if (entity == null) return null;

        TipoNotificacionDTO dto = new TipoNotificacionDTO();
        dto.setIdTipo(entity.getIdTipo());
        dto.setDescripcion(entity.getDescripcion());

        return dto;
    }
}
