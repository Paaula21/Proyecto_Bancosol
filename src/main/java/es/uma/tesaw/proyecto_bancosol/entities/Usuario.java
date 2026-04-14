package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario {
  @Id
  @Column(name = "id_usuario")
  private Integer idUsuario;

  // Relación 1:1 con Persona, mapeando el ID
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "id_usuario")
  private Persona persona;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_rol", nullable = false)
  private Rol rol;

  @Column(nullable = false)
  private String contrasenia;

  @Column(nullable = false, unique = true)
  private String usuario;
}