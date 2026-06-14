/**
 * DTO de persona.
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
public class PersonaDTO {
    private Integer idPersona;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String observacion;
}
