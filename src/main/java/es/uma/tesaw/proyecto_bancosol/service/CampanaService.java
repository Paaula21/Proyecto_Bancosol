package es.uma.tesaw.proyecto_bancosol.service;

import es.uma.tesaw.proyecto_bancosol.dao.CampanaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
// import es.uma.tesaw.proyecto_bancosol.repository.CampanaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CampanaService {

    // Inyectamos el repositorio para comunicarnos con la base de datos
    private final CampanaRepository campanaRepository;

    /**
     * Obtiene la lista completa de campañas registradas en la base de datos.
     */
    public List<Campana> listarCampanas() {
        return campanaRepository.findAll();
    }

    // Aquí iremos añadiendo más métodos como guardarCampana, borrarCampana, etc.
}