package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.CodigoPostalRepository;
import es.uma.tesaw.proyecto_bancosol.entities.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Integer>{
    Optional<CodigoPostal> findByCodigo(String codigo);
}
