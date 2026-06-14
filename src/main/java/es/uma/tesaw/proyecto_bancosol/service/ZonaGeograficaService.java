/**
 * Archivo Service, que se encarga de manejar las zonas geográficas
 *Autores:
 *- Paula Fernández Jiménez: 50%
 *-Andrea Pérez Rodríguez: 50%
 **/

package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.ZonaGeograficaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ZonaGeograficaService {

    private final ZonaGeograficaRepository zonaGeograficaRepository;

    public List<ZonaGeografica> obtenerTodasLasZonas() {
        return this.zonaGeograficaRepository.findAll();
    }

    public long contarZonas() {
        return this.zonaGeograficaRepository.count();
    }
}