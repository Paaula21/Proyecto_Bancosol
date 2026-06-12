package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

  @ManyToMany(mappedBy = "campanas", fetch = FetchType.LAZY)
  private List<Cadena> cadenas = new ArrayList<>();
}
