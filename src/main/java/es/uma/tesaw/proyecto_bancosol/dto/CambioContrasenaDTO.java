/**
 * DTO de cambio de contraseña.
 *
 * Autores:
 * - Paula Fernández Jiménez: 100%
 */
package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.Data;

@Data
public class CambioContrasenaDTO {
    private String actual;
    private String nueva;
    private String confirmacion;
}