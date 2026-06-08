package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.ZonaGeograficaRepository;
import es.uma.tesaw.proyecto_bancosol.entities.ZonaGeografica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZonaGeograficaRepository extends JpaRepository<ZonaGeografica, Integer> {
    Optional<ZonaGeografica> findByNombreZona(String nombreZona);
}
