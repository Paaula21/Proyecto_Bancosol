package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.entities.LogCampana;
import es.uma.tesaw.proyecto_bancosol.dao.LogCampanaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistorialService {

    private final LogCampanaRepository logCampanaRepository;

    /**
     * Devuelve todos los logs del historial ordenados por fecha descendente (los más nuevos primero)
     */
    public List<LogCampana> listarHistorial() {
        // Si no tienes este método creado en el repositorio, puedes definirlo en la interfaz
        // o usar simplemente logCampanaRepository.findAll()
        return logCampanaRepository.findAllByOrderByTimestampDesc();
    }
}