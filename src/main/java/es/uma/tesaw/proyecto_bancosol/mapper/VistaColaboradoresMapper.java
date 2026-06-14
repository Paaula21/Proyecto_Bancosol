/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO la vista de colaboradores
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/

package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.ColaboradorDTO;
import es.uma.tesaw.proyecto_bancosol.entities.VistaColaboradores;
import org.springframework.stereotype.Component;

@Component
public class VistaColaboradoresMapper extends MapperDTO<ColaboradorDTO, VistaColaboradores> {

    @Override
    public ColaboradorDTO toDTO(VistaColaboradores entity) {
        if (entity == null) return null;

        ColaboradorDTO dto = new ColaboradorDTO();
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