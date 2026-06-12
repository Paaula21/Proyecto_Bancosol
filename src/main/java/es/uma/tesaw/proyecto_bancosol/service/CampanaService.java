package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;

    public List<Campana> listarCampanas() {
        return campanaRepository.findAll();
    }

    public List<Campana> buscarCadenasPorCampana(String idCampana) {
        return campanaRepository.findByEstado(idCampana);
    }
}