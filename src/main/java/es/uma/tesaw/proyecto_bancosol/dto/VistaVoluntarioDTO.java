/*
Ainhoa García Rebollo: 100%
*/

package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VistaVoluntarioDTO {
    private String idVoluntario;
    private String idPersona;
    private String nombreCompleto;
    private String email;
    private Integer telefono;
    private String disponibilidad;
}
