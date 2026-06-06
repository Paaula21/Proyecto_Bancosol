
package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.ZonaGeograficaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ZonaGeograficaService {

    private final ZonaGeograficaRepository zonaGeograficaRepository;

    @Transactional(readOnly = true)
    public List<ZonaGeografica> obtenerTodasLasZonas() {
        return this.zonaGeograficaRepository.findAll();
    }
}