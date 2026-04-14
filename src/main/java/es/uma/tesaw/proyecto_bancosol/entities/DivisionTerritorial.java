package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "division_territorial")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DivisionTerritorial {
    @Id
    @Column(name = "id_division")
    private Integer idDivision;

    @Column(name = "nombre_division", nullable = false)
    private String nombreDivision;

    @Column(nullable = false)
    private Boolean tipo; // true/false o un String dependiendo de cómo lo manejes en la BBDD, en SQL es Boolean con CHECK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona")
    private ZonaGeografica zona;
}