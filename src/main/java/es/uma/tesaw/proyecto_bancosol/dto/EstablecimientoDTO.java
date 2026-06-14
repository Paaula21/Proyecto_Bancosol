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
public class EstablecimientoDTO {
    private Integer idEstablecimiento;
    private String idCadena;
    private String nombreCadena;
    private String nombreResena;
    private Integer lineales;
    private Integer idDireccion;
    private String tipoVia;
    private String nombreVia;
    private String numero;
    private String codigo;
    private String localidad;
    private Integer idZona;
    private String nombreZona;
    private String coordinadorNombre;
    private List<String> campanasIds;
}
