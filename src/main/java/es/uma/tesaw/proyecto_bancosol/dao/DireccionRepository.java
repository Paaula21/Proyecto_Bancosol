package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.DireccionRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DireccionRepository extends JpaRepository<Direccion, Integer>{
}
