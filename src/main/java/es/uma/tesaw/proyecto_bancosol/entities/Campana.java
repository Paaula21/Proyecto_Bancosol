package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "campana")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Campana {
  @Id
  @Column(name = "id_campana")
  private String idCampana;

  @Column(name = "nombre_campana", nullable = false)
  private String nombreCampana;

  @Column(name = "fecha_inicio", nullable = false)
  private LocalDate fechaInicio;

  @Column(name = "fecha_fin", nullable = false)
  private LocalDate fechaFin;

  @Column(nullable = false)
  private String estado;

  // Relación N:M que representa la tabla campana_cadena
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "campana_cadena",
          joinColumns = @JoinColumn(name = "id_campana"),
          inverseJoinColumns = @JoinColumn(name = "id_cadena")
  )
  private Set<Cadena> cadenas;
}