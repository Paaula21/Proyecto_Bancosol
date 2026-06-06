package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import org.springframework.stereotype.Component;

@Component
public class CadenaMapper extends MapperDTO<CadenaDTO, Cadena> {

    @Override
    public CadenaDTO toDTO(Cadena entity) {
        if (entity == null) return null;

        CadenaDTO dto = new CadenaDTO();
        dto.setIdCadena(entity.getIdCadena());
        dto.setNombreCadena(entity.getNombreCadena());

        return dto;
    }
}
