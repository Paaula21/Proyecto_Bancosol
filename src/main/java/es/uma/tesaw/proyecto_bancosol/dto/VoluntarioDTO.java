/**
 * DTO de voluntario.
 *
 * Autores:
 * - Ainhoa García Rebollo: 100%
 */
package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioDTO {
    private String idVoluntario;
    private String preferenciaHorario;
    private String idPersona;
    private String nombreVoluntario;
    private String idColaborador;
    private String nombreColaborador;
}
