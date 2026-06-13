package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.DivisionTerritorialRepository;
import es.uma.tesaw.proyecto_bancosol.entities.DivisionTerritorial;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DivisionTerritorialService {

    private final DivisionTerritorialRepository divisionTerritorialRepository;

    @Transactional
    public DivisionTerritorial buscarOCrear(String nombreDivision, ZonaGeografica zona) {
        List<DivisionTerritorial> existentes = divisionTerritorialRepository.buscarPorNombre(nombreDivision);
        if (!existentes.isEmpty()) return existentes.get(0);
        DivisionTerritorial div = new DivisionTerritorial();
        Integer maxId = divisionTerritorialRepository.findAll().stream()
                .mapToInt(DivisionTerritorial::getIdDivision)
                .max().orElse(0);
        div.setIdDivision(maxId + 1);
        div.setNombreDivision(nombreDivision);
        div.setTipo(false);
        div.setZona(zona);
        return divisionTerritorialRepository.save(div);
    }
}
