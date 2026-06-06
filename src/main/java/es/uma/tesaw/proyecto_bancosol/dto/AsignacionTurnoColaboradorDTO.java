package es.uma.tesaw.proyecto_bancosol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionTurnoColaboradorDTO {
    private Integer idAsignacionTurno;
    private String idCampana;
    private Integer idTienda;
    private String idColaborador;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer idVoluntario;
}
