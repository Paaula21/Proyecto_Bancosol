/*
Ainhoa García Rebollo: 80%
IA: 20%
*/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final AsignacionTurnoColaboradorRepository asignacionTurnoRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final EntityManager entityManager;
    private final CampanaMapper campanaMapper;
    private final CadenaRepository cadenaRepository;


    private static final Map<String, String> DIAS_ES = Map.of(
            "MONDAY",    "lunes",
            "TUESDAY",   "martes",
            "WEDNESDAY", "miercoles",
            "THURSDAY",  "jueves",
            "FRIDAY",    "viernes",
            "SATURDAY",  "sabado"
    );


    @Transactional(readOnly = true)
    public Map<String, String> obtenerAsignaciones(String idCampana, Integer idTienda) {
        Map<String, String> asignacionesGuardadas = new HashMap<>();

        Campana campana = campanaRepository.findById(idCampana).orElse(null);
        Establecimiento tienda = establecimientoRepository.findById(idTienda).orElse(null);

        if (campana != null && tienda != null) {
            List<AsignacionTurnoColaborador> turnos = asignacionTurnoRepository.findByCampanaAndTienda(campana, tienda);

            for (AsignacionTurnoColaborador t : turnos) {
                String diaEs = DIAS_ES.get(t.getFecha().getDayOfWeek().name());
                if (diaEs == null) continue;

                String turno = (t.getHoraInicio().getHour() < 14) ? "manana" : "tarde";

                if (t.getVoluntario() != null) {
                    asignacionesGuardadas.put("asignacion_" + turno + "_" + diaEs, String.valueOf(t.getVoluntario().getIdVoluntario()));
                }
            }
        }
        return asignacionesGuardadas;
    }

    public List<Campana> listarCampanas() {
        return campanaRepository.findAll();
    }

    public List<CampanaDTO> listarCampanasDTO() {
        return campanaMapper.toDTOList(campanaRepository.findAll());
    }

    @Transactional
    public void guardarTurnos(String idCampana, String idTienda, Map<String, String> formData) { // <-- Cambia la firma

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

            // Cambiamos request.getParameter por formData.get()
            String idVolManana = formData.get("asignacion_manana_" + diaEs);
            String idVolTarde = formData.get("asignacion_tarde_" + diaEs);

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

    // Filtrar para el listado
    public List<CampanaDTO> listarCampanasDTO(String estado, String busqueda) {
        String estadoFinal = (estado == null || estado.isBlank()) ? "Todos" : estado;
        String busquedaFinal = (busqueda == null) ? "" : busqueda;

        List<Campana> lista = campanaRepository.filtrarCampanas(estadoFinal, busquedaFinal);
        return campanaMapper.toDTOList(lista);
    }

    // Buscar una específica
    public CampanaDTO buscarCampana(String id) {
        return campanaRepository.findById(id).map(campanaMapper::toDTO).orElse(null);
    }

    // Guardar (Crear o Actualizar)
    @Transactional
    public String guardarCampana(String idCampana, String nombre, LocalDate inicio, LocalDate fin, String estado, List<String> cadenasIds) {
        Campana campana;
        if (idCampana == null || idCampana.isEmpty()) {
            campana = new Campana();
            // Generar ID básico (por ejemplo "VERANO_2024")
            campana.setIdCampana(nombre.toUpperCase().replaceAll("\\s+", "_"));
        } else {
            campana = campanaRepository.findById(idCampana).orElse(new Campana());
        }

        campana.setNombreCampana(nombre);
        campana.setFechaInicio(inicio);
        campana.setFechaFin(fin);
        campana.setEstado(estado);
        campanaRepository.save(campana);

        // Asignar cadenas a la campaña
        if (cadenasIds != null) {
            List<Cadena> todasLasCadenas = cadenaRepository.findAll();
            for (Cadena c : todasLasCadenas) {
                if (cadenasIds.contains(c.getIdCadena())) {
                    if (!c.getCampanas().contains(campana)) {
                        c.getCampanas().add(campana);
                    }
                } else {
                    c.getCampanas().remove(campana);
                }
            }
            cadenaRepository.saveAll(todasLasCadenas);
        }

        return campana.getIdCampana();
    }

    // Eliminar
    @Transactional
    public void borrarCampana(String idCampana) {
        campanaRepository.findById(idCampana).ifPresent(campana -> {
            // Desvincular de cadenas antes de borrar
            for (Cadena c : campana.getCadenas()) {
                c.getCampanas().remove(campana);
            }
            cadenaRepository.saveAll(campana.getCadenas());
            campanaRepository.delete(campana);
        });
    }
}