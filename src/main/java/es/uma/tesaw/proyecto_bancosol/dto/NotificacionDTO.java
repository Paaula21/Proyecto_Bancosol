package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private Integer idNotificacion;
    private Integer idPersonaDestino;
    private String idTipo;
    private String titulo;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvioProgramado;
    private Integer idAsignacionRef;
}
