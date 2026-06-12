package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.AsignacionTurnoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import es.uma.tesaw.proyecto_bancosol.mapper.CampanaMapper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final AsignacionTurnoColaboradorRepository asignacionTurnoRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final EntityManager entityManager;
    private final CampanaMapper campanaMapper;

    public List<Campana> listarCampanas() {
        return campanaRepository.findAll();
    }

    public List<CampanaDTO> listarCampanasDTO() {
        return campanaMapper.toDTOList(campanaRepository.findAll());
    }

    @Transactional
    public void guardarTurnos(String idCampana, String idTienda, HttpServletRequest request) {

        Campana campana = campanaRepository.findById(idCampana)
                .orElseThrow(() -> new IllegalArgumentException("Campaña no encontrada: " + idCampana));

        Establecimiento tienda = establecimientoRepository.findById(Integer.parseInt(idTienda))
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada: " + idTienda));

        asignacionTurnoRepository.deleteByCampanaAndTienda(campana, tienda);
        entityManager.flush();

        LocalDate lunesDeLaSemana = campana.getFechaInicio().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<AsignacionTurnoColaborador> nuevasAsignaciones = new ArrayList<>();
        String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};

        for (int i = 0; i < dias.length; i++) {
            String diaEs = dias[i];
            LocalDate fechaDia = lunesDeLaSemana.plusDays(i);

            String idVolManana = request.getParameter("asignacion_manana_" + diaEs);
            String idVolTarde = request.getParameter("asignacion_tarde_" + diaEs);

            procesarTurno(idVolManana, campana, tienda, fechaDia, LocalTime.of(9, 0), LocalTime.of(14, 0), nuevasAsignaciones);
            procesarTurno(idVolTarde, campana, tienda, fechaDia, LocalTime.of(15, 0), LocalTime.of(20, 0), nuevasAsignaciones);
        }

        asignacionTurnoRepository.saveAll(nuevasAsignaciones);
        entityManager.flush();
    }

    private void procesarTurno(String idVoluntario, Campana campana, Establecimiento tienda,
                               LocalDate fechaDia, LocalTime inicio, LocalTime fin,
                               List<AsignacionTurnoColaborador> listaNuevasAsignaciones) {

        if (idVoluntario != null && !idVoluntario.isBlank()) {
            voluntarioRepository.findById(Integer.parseInt(idVoluntario)).ifPresent(vol -> {
                AsignacionTurnoColaborador turno = new AsignacionTurnoColaborador();
                turno.setCampana(campana);
                turno.setTienda(tienda);
                turno.setVoluntario(vol);
                turno.setColaborador(null);
                turno.setFecha(fechaDia);
                turno.setHoraInicio(inicio);
                turno.setHoraFin(fin);

                listaNuevasAsignaciones.add(turno);
            });
        }
    }
}