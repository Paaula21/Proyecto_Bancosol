package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;

public class VistaVoluntarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voluntario")
    private Integer idVoluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Column(name = "nombre_completo")
    private String nombre_completo;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "disponibilidad")
    private String disponibilidad;

}
