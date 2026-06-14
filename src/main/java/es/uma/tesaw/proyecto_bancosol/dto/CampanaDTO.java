/**
 * DTO de campaña.
 *
 * Autores:
 * - Paula Fernández Jiménez: 100%
 */
package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampanaDTO {
    private String idCampana;
    private String nombreCampana;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Set<String> idsCadenas;
}
