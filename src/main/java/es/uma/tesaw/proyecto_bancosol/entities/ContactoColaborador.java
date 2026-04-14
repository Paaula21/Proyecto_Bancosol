package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacto_colaborador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ContactoColaborador {
  @Id
  @Column(name = "id_contacto")
  private Integer idContacto;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "id_contacto")
  private Persona persona;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_colaborador", nullable = false)
  private Colaborador colaborador;

  @Column(name = "es_principal")
  private Boolean esPrincipal = false;
}