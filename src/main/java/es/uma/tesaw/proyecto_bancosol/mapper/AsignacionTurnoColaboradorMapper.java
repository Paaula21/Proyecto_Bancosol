/**
 * Archivo mapper que se encarga de la asignación de los turnos
 /**
 * Archivo Mapper que se encarga de transformar de entidad a DTO la asignación de los turnos
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.AsignacionTurnoColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionTurnoColaborador;
import org.springframework.stereotype.Component;

@Component
public class AsignacionTurnoColaboradorMapper extends MapperDTO<AsignacionTurnoColaboradorDTO, AsignacionTurnoColaborador> {

    @Override
    public AsignacionTurnoColaboradorDTO toDTO(AsignacionTurnoColaborador entity) {
        if (entity == null) return null;

        AsignacionTurnoColaboradorDTO dto = new AsignacionTurnoColaboradorDTO();
        dto.setIdAsignacionTurno(entity.getIdAsignacionTurno());
        dto.setIdCampana(String.valueOf(entity.getCampana() != null ? entity.getCampana().getIdCampana() : null));
        dto.setIdTienda(entity.getTienda() != null ? entity.getTienda().getIdEstablecimiento() : null);
        dto.setIdColaborador(entity.getColaborador() != null ? entity.getColaborador().getIdColaborador() : null);
        dto.setFecha(entity.getFecha());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setIdVoluntario(entity.getVoluntario() != null ? entity.getVoluntario().getIdVoluntario() : null);

        return dto;
    }
}
