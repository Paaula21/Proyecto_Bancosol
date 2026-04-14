package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asignacion_coordinador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AsignacionCoordinador {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_asignacion_coord")
  private Integer idAsignacionCoord;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_campana", nullable = false)
  private Campana campana;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tienda", nullable = false)
  private Establecimiento tienda;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario_coordinador", nullable = false)
  private Usuario usuarioCoordinador;
}