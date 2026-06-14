/**
 * DTO de division territorial.
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
public class DivisionTerritorialDTO {
    private Integer idDivision;
    private String nombreDivision;
    private Boolean tipo;
    private Integer idZona;
}
