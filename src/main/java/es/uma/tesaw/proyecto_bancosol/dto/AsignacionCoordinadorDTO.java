/**
 * DTO de asignacion de coordinador.
 *
 * Autores:
 * - Paula Fernández Jiménez: 100%
 */
package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionCoordinadorDTO {
    private Integer idAsignacionCoord;
    private String idCampana;
    private Integer idTienda;
    private Integer idUsuarioCoordinador;
}
