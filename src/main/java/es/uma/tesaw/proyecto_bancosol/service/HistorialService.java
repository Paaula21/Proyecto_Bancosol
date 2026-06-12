/*
Ainhoa García Rebollo: 100%
*/

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

    public List<LogCampana> listarHistorial() {
        return logCampanaRepository.findAllByOrderByTimestampDesc();
    }
}