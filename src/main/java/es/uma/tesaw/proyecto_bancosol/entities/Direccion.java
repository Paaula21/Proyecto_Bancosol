package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direccion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Direccion {
  @Id
  @Column(name = "id_direccion")
  private Integer idDireccion;

  @Column(name = "tipo_via")
  private String tipoVia;

  @Column(name = "nombre_via", nullable = false)
  private String nombreVia;

  private String numero;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cp")
  private CodigoPostal cp;
}