/**
 * Mapper que transforma de entidad a DTO para cadena.
 *
 * Autores:
 * - María Muñoz Martín: 100%
 */


package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.CadenaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Cadena;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CadenaMapper extends MapperDTO<CadenaDTO, Cadena> {

    @Override
    public CadenaDTO toDTO(Cadena entity) {
        if (entity == null) return null;

        CadenaDTO dto = new CadenaDTO();
        dto.setIdCadena(entity.getIdCadena());
        dto.setNombreCadena(entity.getNombreCadena());
        dto.setNumEstablecimientos(entity.getEstablecimientos().size());

        List<String> campanasIds = new ArrayList<>();
        for (Campana campana : entity.getCampanas()) {
            campanasIds.add(campana.getIdCampana());
        }
        dto.setCampanasIds(campanasIds);

        return dto;
    }
}
