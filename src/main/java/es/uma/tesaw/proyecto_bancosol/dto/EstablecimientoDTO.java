package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablecimientoDTO {
    private Integer idEstablecimiento;
    private String idCadena;
    private String nombreResena;
    private Integer lineales;
    private Integer idDireccion;
}
