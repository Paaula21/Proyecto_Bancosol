package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.AsignacionTurnoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.dao.EstablecimientoRepository;
import es.uma.tesaw.proyecto_bancosol.dao.VoluntarioRepository;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final AsignacionTurnoColaboradorRepository asignacionTurnoRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final EntityManager entityManager;  // para forzar el flush entre delete e insert

    public List<Campana> listarCampanas() {
        return campanaRepository.findAll();
    }

    public List<Campana> buscarCadenasPorCampana(String idCampana) {
        return campanaRepository.findByEstado(idCampana);
    }

    @Transactional
    public void guardarTurnos(String idCampana, String idTienda, HttpServletRequest request) {

        Campana campana = campanaRepository.findById(idCampana).orElseThrow(
                () -> new IllegalArgumentException("Campaña no encontrada: " + idCampana)
        );
        Establecimiento tienda = establecimientoRepository.findById(Integer.parseInt(idTienda)).orElseThrow(
                () -> new IllegalArgumentException("Tienda no encontrada: " + idTienda)
        );

        // 1. Borramos las asignaciones previas y forzamos el flush para que el DELETE
        //    llegue a la BD ANTES de los INSERT, evitando conflictos de constraint
        asignacionTurnoRepository.deleteByCampanaAndTienda(campana, tienda);
        entityManager.flush();

        // 2. Construimos las nuevas asignaciones en una lista y hacemos un saveAll al final
        String[]  dias      = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};
        LocalTime iniManana = LocalTime.of(9, 0);
        LocalTime finManana = LocalTime.of(14, 0);
        LocalTime iniTarde  = LocalTime.of(15, 0);
        LocalTime finTarde  = LocalTime.of(20, 0);
        LocalDate fechaBase = campana.getFechaInicio();

        List<AsignacionTurnoColaborador> nuevasAsignaciones = new ArrayList<>();

        for (int i = 0; i < dias.length; i++) {
            String    dia      = dias[i];
            LocalDate fechaDia = fechaBase.plusDays(i);

            String idVolManana = request.getParameter("asignacion_manana_" + dia);
            String idVolTarde  = request.getParameter("asignacion_tarde_"  + dia);

            // --- Turno mañana ---
            if (idVolManana != null && !idVolManana.isEmpty()) {
                Voluntario vol = voluntarioRepository.findById(Integer.parseInt(idVolManana)).orElse(null);
                if (vol != null && vol.getColaborador() != null) {
                    AsignacionTurnoColaborador t = new AsignacionTurnoColaborador();
                    t.setCampana(campana);
                    t.setTienda(tienda);
                    t.setColaborador(vol.getColaborador());
                    t.setVoluntario(vol);
                    t.setFecha(fechaDia);
                    t.setHoraInicio(iniManana);
                    t.setHoraFin(finManana);
                    nuevasAsignaciones.add(t);
                }
            }

            // --- Turno tarde ---
            if (idVolTarde != null && !idVolTarde.isEmpty()) {
                Voluntario vol = voluntarioRepository.findById(Integer.parseInt(idVolTarde)).orElse(null);
                if (vol != null && vol.getColaborador() != null) {
                    AsignacionTurnoColaborador t = new AsignacionTurnoColaborador();
                    t.setCampana(campana);
                    t.setTienda(tienda);
                    t.setColaborador(vol.getColaborador());
                    t.setVoluntario(vol);
                    t.setFecha(fechaDia);
                    t.setHoraInicio(iniTarde);
                    t.setHoraFin(finTarde);
                    nuevasAsignaciones.add(t);
                }
            }
        }

        // 3. Un único saveAll con todas las filas nuevas
        asignacionTurnoRepository.saveAll(nuevasAsignaciones);
    }
}