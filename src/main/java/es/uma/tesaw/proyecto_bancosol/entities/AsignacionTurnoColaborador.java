package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asignacion_turno_colaborador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AsignacionTurnoColaborador {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_asignacion_turno")
  private Integer idAsignacionTurno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_campana", nullable = false)
  private Campana campana;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tienda", nullable = false)
  private Establecimiento tienda;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_colaborador", nullable = false)
  private Colaborador colaborador;

  @Column(nullable = false)
  private LocalDate fecha;

  @Column(name = "hora_inicio", nullable = false)
  private LocalTime horaInicio;

  @Column(name = "hora_fin", nullable = false)
  private LocalTime horaFin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_voluntario")
  private Voluntario voluntario;
}