/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.dto;
// Conjunto de datos para manejar cada componente de un colaborador. Contiene el colaborador, la dirección y el contacto del colaborador
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Conjunto de datos para manejar cada componente de un colaborador. Contiene el colaborador, la dirección y el contacto del colaborador
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioColaboradorDTO {
    private String idColaborador;
    private String nombreColaborador;
    private String nombreZona;
    private String contactoNombre;
    private String contactoEmail;
    private String contactoTel;
    private String tipoVia;
    private String nombreVia;
    private String numero;
    private String codigoPostal;
    private String nombreDivision;
    private String observaciones;
}
