package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import org.springframework.stereotype.Component;

@Component
public class EstablecimientoMapper extends MapperDTO<EstablecimientoDTO, Establecimiento> {

    @Override
    public EstablecimientoDTO toDTO(Establecimiento entity) {
        if (entity == null) return null;

        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setIdEstablecimiento(entity.getIdEstablecimiento());
        dto.setIdCadena(entity.getCadena() != null ? entity.getCadena().getIdCadena() : null);
        dto.setNombreResena(entity.getNombreResena());
        dto.setLineales(entity.getLineales());
        dto.setIdDireccion(entity.getDireccion() != null ? entity.getDireccion().getIdDireccion() : null);

        return dto;
    }
}
