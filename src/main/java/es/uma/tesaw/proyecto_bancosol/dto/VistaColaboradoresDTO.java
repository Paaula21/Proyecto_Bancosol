/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VistaColaboradoresDTO {
    private String idColaborador;
    private String nombreColaborador;
    private String observaciones;
    private String nombreDivision;
    private String nombreZona;
    private String nombreContacto;
    private String emailContacto;
    private String telefonoContacto;
}