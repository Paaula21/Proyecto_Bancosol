package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cadena")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cadena {
  @Id
  @Column(name = "id_cadena")
  private String idCadena;

  @Column(name = "nombre_cadena", nullable = false)
  private String nombreCadena;
}