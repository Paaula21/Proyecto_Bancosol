package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioDTO {
    private Integer idVoluntario;
    private String preferenciaHorario;
    private Integer idPersona;
    private String nombreVoluntario;
    private String idColaborador;
    private String nombreColaborador;
}
