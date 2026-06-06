package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorDTO {
    private String idColaborador;
    private String nombreColaborador;
    private String observaciones;
    private Integer idDireccion;
}
