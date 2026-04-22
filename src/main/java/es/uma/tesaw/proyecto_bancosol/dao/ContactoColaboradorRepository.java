package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.ContactoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContactoColaboradorRepository extends JpaRepository<ContactoColaborador, Integer>{
}
