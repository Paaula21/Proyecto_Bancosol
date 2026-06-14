/**
 * DTO de rol.
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
public class RolDTO {
    private Integer idRol;
    private String nombreRol;
}
