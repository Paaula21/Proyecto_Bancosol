/**
 * Archivo Mapper que se encarga de transformar de entidad a DTO las personas
 *Autores:
 *- Paula Fernández Jiménez: 100%
 **/
package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.PersonaDTO;
import es.uma.tesaw.proyecto_bancosol.entities.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper extends MapperDTO<PersonaDTO, Persona> {

    @Override
    public PersonaDTO toDTO(Persona entity) {
        if (entity == null) return null;

        PersonaDTO dto = new PersonaDTO();
        dto.setIdPersona(entity.getIdPersona());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setObservacion(entity.getObservacion());

        return dto;
    }
}
