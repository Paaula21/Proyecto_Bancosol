/*
* Andrea Pérez Rodríguez: 45% (Te parece bien? que cada una ha hecho un metodo pero tu lo has creado y te he puesto un poco mas)
* Paula Fernández Jiménez: 55%
 */

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