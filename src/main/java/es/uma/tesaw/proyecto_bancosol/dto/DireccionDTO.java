/**
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private Integer idDireccion;
    private String tipoVia;
    private String nombreVia;
    private String numero;
    private Integer idCp;
}
