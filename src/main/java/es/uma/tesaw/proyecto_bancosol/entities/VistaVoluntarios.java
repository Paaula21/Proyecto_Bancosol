/*
Ainhoa García Rebollo: 100%
*/

package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;

public class VistaVoluntarios {

    @Id
    @Column(name = "id_voluntario")
    private Integer idVoluntario;

    @JoinColumn(name = "id_persona")
    private Persona idPersona;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "disponibilidad")
    private String disponibilidad;

}
