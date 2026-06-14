/*
Paula Fernández Jiménez: 100%
*/

package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "vista_colaborador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VistaColaboradores {

    @Id
    @Column(name = "id_colaborador")
    private String idColaborador;

    @Column(name = "nombre_colaborador")
    private String nombreColaborador;

    private String observaciones;

    @Column(name = "tipo_via")
    private String tipoVia;

    @Column(name = "nombre_via")
    private String nombreVia;

    private String numero;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "nombre_division")
    private String nombreDivision;

    @Column(name = "nombre_zona")
    private String nombreZona;

    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "email_contacto")
    private String emailContacto;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;
}