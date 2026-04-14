package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zona_geografica")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ZonaGeografica {
  @Id
  @Column(name = "id_zona")
  private Integer idZona;

  @Column(name = "nombre_zona", nullable = false)
  private String nombreZona;
}