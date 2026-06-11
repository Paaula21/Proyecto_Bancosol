package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.VistaVoluntarios;
import es.uma.tesaw.proyecto_bancosol.entities.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VistaVoluntariosRepository extends JpaRepository<VistaVoluntarios, Integer> {

    List<Voluntario> findByDisponibilidadContainingIgnoreCase(@Param("disponibilidadId") Integer disponibilidadId);

}