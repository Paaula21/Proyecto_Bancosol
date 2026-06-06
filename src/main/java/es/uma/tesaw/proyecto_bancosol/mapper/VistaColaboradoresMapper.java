/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.VistaColaboradoresDTO;
import es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores;
import org.springframework.stereotype.Component;

@Component
public class VistaColaboradoresMapper extends MapperDTO<VistaColaboradoresDTO, VistaColaboradores> {

    @Override
    public VistaColaboradoresDTO toDTO(VistaColaboradores entity) {
        if (entity == null) return null;

        VistaColaboradoresDTO dto = new VistaColaboradoresDTO();
        dto.setIdColaborador(entity.getIdColaborador());
        dto.setNombreColaborador(entity.getNombreColaborador());
        dto.setObservaciones(entity.getObservaciones());
        dto.setNombreDivision(entity.getNombreDivision());
        dto.setNombreZona(entity.getNombreZona());
        dto.setNombreContacto(entity.getNombreContacto());
        dto.setEmailContacto(entity.getEmailContacto());
        dto.setTelefonoContacto(entity.getTelefonoContacto());

        return dto;
    }
}