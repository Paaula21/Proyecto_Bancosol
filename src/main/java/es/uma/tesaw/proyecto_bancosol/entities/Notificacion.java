package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_notificacion")
  private Integer idNotificacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_persona_destino", nullable = false)
  private Persona personaDestino;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo", nullable = false)
  private TipoNotificacion tipo;

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String mensaje;

  private Boolean leida = false;

  @Column(name = "fecha_creacion", insertable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_envio_programado")
  private LocalDateTime fechaEnvioProgramado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_asignacion_ref")
  private AsignacionTurnoColaborador asignacionRef;
}