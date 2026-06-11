package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.AsignacionCoordinadorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionCoordinador;
import org.springframework.stereotype.Component;

@Component
public class AsignacionCoordinadorMapper extends MapperDTO<AsignacionCoordinadorDTO, AsignacionCoordinador> {

    @Override
    public AsignacionCoordinadorDTO toDTO(AsignacionCoordinador entity) {
        if (entity == null) return null;

        AsignacionCoordinadorDTO dto = new AsignacionCoordinadorDTO();
        dto.setIdAsignacionCoord(entity.getIdAsignacionCoord());
        dto.setIdCampana(String.valueOf(entity.getCampana() != null ? entity.getCampana().getIdCampana() : null));
        dto.setIdTienda(entity.getTienda() != null ? entity.getTienda().getIdEstablecimiento() : null);
        dto.setIdUsuarioCoordinador(entity.getUsuarioCoordinador() != null ? entity.getUsuarioCoordinador().getIdUsuario() : null);

        return dto;
    }
}
