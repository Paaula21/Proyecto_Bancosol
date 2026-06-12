package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import es.uma.tesaw.proyecto_bancosol.dao.CadenaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CadenaService {

    private final CadenaRepository tiendaRepository;

    public List<Cadena> buscarCadenaPorCampana(String idCampana) {
        return tiendaRepository.findCadenasByCampanaId(idCampana);
    }
}