package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.dao.ContactoColaboradorRepository;
import es.uma.tesaw.proyecto_bancosol.entities.Colaborador;
import es.uma.tesaw.proyecto_bancosol.entities.ContactoColaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContactoColaboradorRepository extends JpaRepository<ContactoColaborador, Integer>{
    @Query("SELECT cc FROM ContactoColaborador cc WHERE cc.colaborador = :colaborador")
    Optional<ContactoColaborador> findByColaborador(@Param("colaborador") Colaborador colaboradorSeleccionado);
}
