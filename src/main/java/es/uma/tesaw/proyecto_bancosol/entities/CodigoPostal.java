package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "codigo_postal")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CodigoPostal {
    @Id
    @Column(name = "id_cp")
    private Integer idCp;

    @Column(nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_division")
    private DivisionTerritorial division;
}