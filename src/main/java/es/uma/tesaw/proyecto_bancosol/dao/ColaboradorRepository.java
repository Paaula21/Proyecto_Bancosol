package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.ColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ColaboradorRepository extends JpaRepository<Colaborador, String>{
}
