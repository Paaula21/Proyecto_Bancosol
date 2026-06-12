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
import java.util.Arrays;
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

    public List<Campana> buscarCadenasPorCampana(String idCampana) {
        return campanaRepository.findByEstado(idCampana);
    }

    @Transactional
    public void guardarTurnos(String idCampana,
                              String idTienda,
                              HttpServletRequest request) {

        Campana campana = campanaRepository.findById(idCampana)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Campaña no encontrada: " + idCampana));

        Establecimiento tienda = establecimientoRepository
                .findById(Integer.parseInt(idTienda))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tienda no encontrada: " + idTienda));

        // Eliminar asignaciones anteriores
        asignacionTurnoRepository.deleteByCampanaAndTienda(
                campana,
                tienda
        );

        entityManager.flush();

        LocalTime iniManana = LocalTime.of(9, 0);
        LocalTime finManana = LocalTime.of(14, 0);

        LocalTime iniTarde = LocalTime.of(15, 0);
        LocalTime finTarde = LocalTime.of(20, 0);

        LocalDate fechaBase = campana.getFechaInicio();

        // AQUÍ ESTÁ EL CAMBIO: Forzamos a buscar el Lunes de la semana de inicio
        LocalDate lunesDeLaSemana = fechaBase.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        System.out.println("===== GUARDANDO TURNOS =====");
        System.out.println("Campaña: " + idCampana);
        System.out.println("Tienda: " + idTienda);

        request.getParameterMap().forEach((k, v) ->
                System.out.println(
                        k + " -> " +
                                java.util.Arrays.toString(v)
                )
        );

        List<AsignacionTurnoColaborador> nuevasAsignaciones =
                new ArrayList<>();

        String[] dias = {
                "lunes",
                "martes",
                "miercoles",
                "jueves",
                "viernes",
                "sabado"
        };

        for (int i = 0; i < dias.length; i++) {

            String diaEs = dias[i];

            // AQUÍ EL OTRO CAMBIO: Usamos 'lunesDeLaSemana' en lugar de 'fechaBase'
            LocalDate fechaDia = lunesDeLaSemana.plusDays(i);

            String idVolManana =
                    request.getParameter(
                            "asignacion_manana_" + diaEs
                    );

            String idVolTarde =
                    request.getParameter(
                            "asignacion_tarde_" + diaEs
                    );

            System.out.println(
                    diaEs
                            + " -> mañana=" + idVolManana
                            + ", tarde=" + idVolTarde
            );

            // MAÑANA
            if (idVolManana != null && !idVolManana.isBlank()) {

                Voluntario vol =
                        voluntarioRepository
                                .findById(Integer.parseInt(idVolManana))
                                .orElse(null);

                if (vol == null) {

                    System.out.println(
                            "No existe voluntario mañana: "
                                    + idVolManana
                    );

                } else {

                    AsignacionTurnoColaborador t =
                            new AsignacionTurnoColaborador();

                    t.setCampana(campana);
                    t.setTienda(tienda);

                    t.setVoluntario(vol);

                    // Ahora permitimos NULL
                    t.setColaborador(null);

                    t.setFecha(fechaDia);
                    t.setHoraInicio(iniManana);
                    t.setHoraFin(finManana);

                    nuevasAsignaciones.add(t);
                }
            }

            // TARDE
            if (idVolTarde != null && !idVolTarde.isBlank()) {

                Voluntario vol =
                        voluntarioRepository
                                .findById(Integer.parseInt(idVolTarde))
                                .orElse(null);

                if (vol == null) {

                    System.out.println(
                            "No existe voluntario tarde: "
                                    + idVolTarde
                    );

                } else {

                    AsignacionTurnoColaborador t =
                            new AsignacionTurnoColaborador();

                    t.setCampana(campana);
                    t.setTienda(tienda);

                    t.setVoluntario(vol);

                    // Ahora permitimos NULL
                    t.setColaborador(null);

                    t.setFecha(fechaDia);
                    t.setHoraInicio(iniTarde);
                    t.setHoraFin(finTarde);

                    nuevasAsignaciones.add(t);
                }
            }
        }

        System.out.println(
                "Asignaciones creadas: "
                        + nuevasAsignaciones.size()
        );

        for (AsignacionTurnoColaborador a :
                nuevasAsignaciones) {

            System.out.println(
                    a.getFecha()
                            + " | "
                            + a.getVoluntario()
                            .getIdVoluntario()
                            + " | "
                            + a.getHoraInicio()
            );
        }

        asignacionTurnoRepository.saveAll(
                nuevasAsignaciones
        );

        entityManager.flush();

        System.out.println(
                "Asignaciones en BD: "
                        + asignacionTurnoRepository
                        .countByCampanaAndTienda(
                                campana,
                                tienda
                        )
        );
    }
}