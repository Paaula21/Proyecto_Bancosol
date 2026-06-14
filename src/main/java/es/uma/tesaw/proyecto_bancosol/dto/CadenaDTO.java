/**
 * DTO de cadena.
 *
 * Autores:
 * - María Muñoz Martín: 100%
 */


package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.Data;

import java.util.List;

@Data
public class CadenaDTO {
    private String idCadena;
    private String nombreCadena;
    private List<String> campanasIds;
    private Integer numEstablecimientos;
}
