package es.uma.tesaw.proyecto_bancosol.dao;

import es.uma.tesaw.proyecto_bancosol.entities.AsignacionTurnoColaborador;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface AsignacionTurnoColaboradorRepository extends JpaRepository<AsignacionTurnoColaborador, Integer> {

    List<AsignacionTurnoColaborador> findByCampanaAndTienda(Campana campana, Establecimiento tienda);

    @Modifying
    void deleteByCampanaAndTienda(Campana campana, Establecimiento tienda);
}