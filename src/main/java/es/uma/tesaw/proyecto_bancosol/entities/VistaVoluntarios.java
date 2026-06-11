/*
Ainhoa García Rebollo: 100%
*/

package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vista_voluntario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VistaVoluntarios {

    @Id
    @Column(name = "id_voluntario")
    private Integer idVoluntario;

    @Column(name = "id_persona")
    private String idPersona;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "disponibilidad")
    private String disponibilidad;

}
