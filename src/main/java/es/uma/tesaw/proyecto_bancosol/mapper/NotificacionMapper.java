package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.NotificacionDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Notificacion;
import org.springframework.stereotype.Component;

@Component
public class NotificacionMapper extends MapperDTO<NotificacionDTO, Notificacion> {

    @Override
    public NotificacionDTO toDTO(Notificacion entity) {
        if (entity == null) return null;

        NotificacionDTO dto = new NotificacionDTO();
        dto.setIdNotificacion(entity.getIdNotificacion());
        dto.setIdPersonaDestino(entity.getPersonaDestino() != null ? entity.getPersonaDestino().getIdPersona() : null);
        dto.setIdTipo(entity.getTipo() != null ? entity.getTipo().getIdTipo() : null);
        dto.setTitulo(entity.getTitulo());
        dto.setMensaje(entity.getMensaje());
        dto.setLeida(entity.getLeida());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaEnvioProgramado(entity.getFechaEnvioProgramado());
        dto.setIdAsignacionRef(entity.getAsignacionRef() != null ? entity.getAsignacionRef().getIdAsignacionTurno() : null);

        return dto;
    }
}
