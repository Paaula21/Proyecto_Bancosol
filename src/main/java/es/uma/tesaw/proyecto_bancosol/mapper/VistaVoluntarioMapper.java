/**
 * Mapper que transforma de entidad a DTO un voluntario.
 *
 * Autores:
 * - Ainhoa García Rebollo: 100%
 */

package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.VistaVoluntarioDTO;
import es.uma.tesaw.proyecto_bancosol.entities.VistaVoluntarios;
import org.springframework.stereotype.Component;

@Component
public class VistaVoluntarioMapper extends MapperDTO<VistaVoluntarioDTO, VistaVoluntarios> {

    @Override
    public VistaVoluntarioDTO toDTO(VistaVoluntarios entity) {
        if (entity == null) return null;

        VistaVoluntarioDTO dto = new VistaVoluntarioDTO();

        dto.setIdVoluntario(String.valueOf(entity.getIdVoluntario()));
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDisponibilidad(entity.getDisponibilidad());

        return dto;
    }

}
