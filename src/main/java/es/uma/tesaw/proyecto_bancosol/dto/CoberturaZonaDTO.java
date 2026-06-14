/**
 * DTO para poder calcular cómodamente el porcentaje de cobertura de cada zona
 * Autora:
 * - Andrea Pérez Rodríguez: 100%
 */

package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoberturaZonaDTO {
    private String nombreZona;
    private Long tiendas;
    private Integer porcentaje;
}
