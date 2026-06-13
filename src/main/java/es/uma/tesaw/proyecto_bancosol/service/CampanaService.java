package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.*;
import es.uma.tesaw.proyecto_bancosol.dto.CampanaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.*;
import es.uma.tesaw.proyecto_bancosol.mapper.CampanaMapper;
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
    private final CadenaRepository cadenaRepository;
    private final CampanaMapper campanaMapper;

    private static final Map<String, String> DIAS_ES = Map.of(
            "MONDAY",    "lunes",
            "TUESDAY",   "martes",
            "WEDNESDAY", "miercoles",
            "THURSDAY",  "jueves",
            "FRIDAY",    "viernes",
            "SATURDAY",  "sabado"
    );

    // ==========================================
    // MÉTODOS DE BÚSQUEDA Y LISTADO
    // ==========================================

    @Transactional(readOnly = true)
    public List<CampanaDTO> listarCampanasDTO() {
        return campanaMapper.toDTOList(this.campanaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CampanaDTO> listarCampanasDTO(String estado, String busqueda) {
        String estadoFinal = (estado == null || estado.isBlank()) ? "Todos" : estado;
        String busquedaFinal = (busqueda == null) ? "" : busqueda;

        List<Campana> lista = this.campanaRepository.filtrarCampanas(estadoFinal, busquedaFinal);
        return campanaMapper.toDTOList(lista);
    }

    @Transactional(readOnly = true)
    public CampanaDTO buscarCampana(String id) {
        if (id == null || id.isEmpty()) {
            return new CampanaDTO();
        }
        return this.campanaRepository.findById(id).map(campanaMapper::toDTO).orElse(new CampanaDTO());
    }

    @Transactional(readOnly = true)
    public Map<String, String> obtenerAsignaciones(String idCampana, Integer idTienda) {
        Map<String, String> asignacionesGuardadas = new HashMap<>();

        Campana campana = this.campanaRepository.findById(idCampana).orElse(null);
        Establecimiento tienda = this.establecimientoRepository.findById(idTienda).orElse(null);

        if (campana != null && tienda != null) {
            List<AsignacionTurnoColaborador> turnos = this.asignacionTurnoRepository.findByCampanaAndTienda(campana, tienda);

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

    // ==========================================
    // MÉTODOS DE GUARDADO Y EDICIÓN
    // ==========================================

    @Transactional
    public String guardarCampana(String idCampana, String nombre, LocalDate inicio, LocalDate fin, String estado, List<String> cadenasIds) {
        Campana campana;
        if (idCampana == null || idCampana.isEmpty()) {
            campana = new Campana();
            campana.setIdCampana(nombre.toUpperCase().replaceAll("\\s+", "_"));
        } else {
            campana = this.campanaRepository.findById(idCampana).orElse(new Campana());
        }

        campana.setNombreCampana(nombre);
        campana.setFechaInicio(inicio);
        campana.setFechaFin(fin);
        campana.setEstado(estado);
        this.campanaRepository.save(campana);

        List<String> cadenasSeleccionadas = (cadenasIds != null) ? cadenasIds : new ArrayList<>();
        List<Cadena> todasLasCadenas = this.cadenaRepository.findAll();

        for (Cadena c : todasLasCadenas) {
            if (cadenasSeleccionadas.contains(c.getIdCadena())) {
                if (!c.getCampanas().contains(campana)) {
                    c.getCampanas().add(campana);
                }
            } else {
                c.getCampanas().remove(campana);
            }
        }
        this.cadenaRepository.saveAll(todasLasCadenas);

        return campana.getIdCampana();
    }

    @Transactional
    public void guardarTurnos(String idCampana, String idTienda, Map<String, String> formData) {
        Campana campana = this.campanaRepository.findById(idCampana).get();
        Establecimiento tienda = this.establecimientoRepository.findById(Integer.parseInt(idTienda)).get();

        // Borramos usando el repositorio estándar
        this.asignacionTurnoRepository.deleteByCampanaAndTienda(campana, tienda);

        LocalDate lunesDeLaSemana = campana.getFechaInicio().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        List<AsignacionTurnoColaborador> nuevasAsignaciones = new ArrayList<>();
        String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado"};

        for (int i = 0; i < dias.length; i++) {
            String diaEs = dias[i];
            LocalDate fechaDia = lunesDeLaSemana.plusDays(i);

            String idVolManana = formData.get("asignacion_manana_" + diaEs);
            String idVolTarde = formData.get("asignacion_tarde_" + diaEs);

            procesarTurno(idVolManana, campana, tienda, fechaDia, LocalTime.of(9, 0), LocalTime.of(14, 0), nuevasAsignaciones);
            procesarTurno(idVolTarde, campana, tienda, fechaDia, LocalTime.of(15, 0), LocalTime.of(20, 0), nuevasAsignaciones);
        }

        this.asignacionTurnoRepository.saveAll(nuevasAsignaciones);
    }

    private void procesarTurno(String idVoluntario, Campana campana, Establecimiento tienda,
                               LocalDate fechaDia, LocalTime inicio, LocalTime fin,
                               List<AsignacionTurnoColaborador> listaNuevasAsignaciones) {
        if (idVoluntario != null && !idVoluntario.isBlank()) {
            Voluntario vol = this.voluntarioRepository.findById(Integer.parseInt(idVoluntario)).orElse(null);
            if(vol != null) {
                AsignacionTurnoColaborador turno = new AsignacionTurnoColaborador();
                turno.setCampana(campana);
                turno.setTienda(tienda);
                turno.setVoluntario(vol);
                turno.setColaborador(null);
                turno.setFecha(fechaDia);
                turno.setHoraInicio(inicio);
                turno.setHoraFin(fin);
                listaNuevasAsignaciones.add(turno);
            }
        }
    }

    // ==========================================
    // MÉTODOS DE BORRADO
    // ==========================================

    @Transactional
    public void borrarCampana(String idCampana) {
        Campana campana = this.campanaRepository.findById(idCampana).orElse(null);

        if (campana != null) {
            // 1. Borramos los turnos asociados para evitar el error de base de datos
            List<AsignacionTurnoColaborador> turnosAsignados = this.asignacionTurnoRepository.findAll();
            for(AsignacionTurnoColaborador turno : turnosAsignados){
                if(turno.getCampana().getIdCampana().equals(campana.getIdCampana())){
                    this.asignacionTurnoRepository.delete(turno);
                }
            }

            // 2. Limpiamos las relaciones ManyToMany (igual que hacéis en borrarMovie)
            for (Cadena c : campana.getCadenas()) {
                c.getCampanas().remove(campana);
            }
            this.cadenaRepository.saveAll(campana.getCadenas());

            // 3. Borramos la entidad
            this.campanaRepository.delete(campana);
        }
    }
}