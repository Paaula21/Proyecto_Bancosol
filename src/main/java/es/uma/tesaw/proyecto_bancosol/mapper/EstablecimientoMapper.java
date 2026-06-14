/**
 * Mapper que transforma de entidad a DTO para establecimiento.
 *
 * Autores:
 * - María Muñoz Martín: 100%
 */


package es.uma.tesaw.proyecto_bancosol.mapper;

import es.uma.tesaw.proyecto_bancosol.dto.EstablecimientoDTO;
import es.uma.tesaw.proyecto_bancosol.entities.AsignacionCoordinador;
import es.uma.tesaw.proyecto_bancosol.entities.Campana;
import es.uma.tesaw.proyecto_bancosol.entities.Establecimiento;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstablecimientoMapper extends MapperDTO<EstablecimientoDTO, Establecimiento> {

    private String obtenerCoordinador(List<AsignacionCoordinador> asignaciones) {
        if (asignaciones == null || asignaciones.isEmpty()) return null;
        return asignaciones.get(0).getUsuarioCoordinador().getPersona().getNombreCompleto();
    }

    @Override
    public EstablecimientoDTO toDTO(Establecimiento entity) {
        if (entity == null) return null;

        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setIdEstablecimiento(entity.getIdEstablecimiento());
        dto.setNombreResena(entity.getNombreResena());
        dto.setLineales(entity.getLineales());

        if (entity.getCadena() != null) {
            dto.setIdCadena(entity.getCadena().getIdCadena());
            dto.setNombreCadena(entity.getCadena().getNombreCadena());

            List<String> campanasIds = new ArrayList<>();
            for (Campana campana : entity.getCadena().getCampanas()) {
                campanasIds.add(campana.getIdCampana());
            }
            dto.setCampanasIds(campanasIds);
        }

        if (entity.getDireccion() != null) {
            dto.setIdDireccion(entity.getDireccion().getIdDireccion());
            dto.setTipoVia(entity.getDireccion().getTipoVia());
            dto.setNombreVia(entity.getDireccion().getNombreVia());
            dto.setNumero(entity.getDireccion().getNumero());

            if (entity.getDireccion().getCp() != null) {
                dto.setCodigo(entity.getDireccion().getCp().getCodigo());

                if (entity.getDireccion().getCp().getDivision() != null) {
                    dto.setLocalidad(entity.getDireccion().getCp().getDivision().getNombreDivision());

                    if (entity.getDireccion().getCp().getDivision().getZona() != null) {
                        dto.setIdZona(entity.getDireccion().getCp().getDivision().getZona().getIdZona());
                        dto.setNombreZona(entity.getDireccion().getCp().getDivision().getZona().getNombreZona());
                    }
                }
            }
        }

        return dto;
    }

    public EstablecimientoDTO toDTO(Establecimiento entity, List<AsignacionCoordinador> asignaciones) {
        EstablecimientoDTO dto = toDTO(entity);
        if (dto != null) {
            dto.setCoordinadorNombre(obtenerCoordinador(asignaciones));
        }
        return dto;
    }
}
