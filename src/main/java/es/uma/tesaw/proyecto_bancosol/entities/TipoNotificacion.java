package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_notificacion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TipoNotificacion {
  @Id
  @Column(name = "id_tipo")
  private String idTipo;

  @Column(nullable = false)
  private String descripcion;
}