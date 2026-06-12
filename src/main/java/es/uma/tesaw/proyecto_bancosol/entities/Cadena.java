package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cadena")
@Getter @Setter
@NoArgsConstructor
public class Cadena {

  @Id
  @Column(name = "id_cadena")
  private String idCadena;

  @Column(name = "nombre_cadena", nullable = false)
  private String nombreCadena;

  @OneToMany(mappedBy = "cadena")
  private List<Establecimiento> establecimientos = new ArrayList<>();

  @ManyToMany
  @JoinTable(
          name = "campana_cadena",
          joinColumns = @JoinColumn(name = "id_cadena"),
          inverseJoinColumns = @JoinColumn(name = "id_campana")
  )
  private List<Campana> campanas = new ArrayList<>();

  public void deleteCampanas() {
    for (Campana campana : this.campanas) {
      campana.getCadenas().remove(this);
    }
    this.campanas.clear();
  }
}