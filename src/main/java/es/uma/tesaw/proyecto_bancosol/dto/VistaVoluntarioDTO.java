/**
 * DTO de voluntario.
 *
 * Autores:
 * - Ainhoa García Rebollo: 100%
 */
package es.uma.tesaw.proyecto_bancosol.dto;

import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VistaVoluntarioDTO extends VoluntarioDTO {
    private String idVoluntario;
    private String idPersona;
    private String nombreCompleto;
    private String email;
    private Integer telefono;
    private String disponibilidad;
}
