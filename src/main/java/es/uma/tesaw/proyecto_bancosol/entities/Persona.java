package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_persona")
  private Integer idPersona;

  @Column(name = "nombre_completo", nullable = false)
  private String nombreCompleto;

  private String telefono;

  @Column(unique = true)
  private String email;

  private String observacion;
}