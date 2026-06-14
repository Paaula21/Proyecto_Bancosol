package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "colaborador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Colaborador {

  @Id
  @Column(name = "id_colaborador")
  private String idColaborador;

  @Column(name = "nombre_colaborador", nullable = false)
  private String nombreColaborador;

  private String observaciones;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_direccion", nullable = false)
  private Direccion direccion;

  @OneToOne(mappedBy = "colaborador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private ContactoColaborador contacto;
}