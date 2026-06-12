package es.uma.tesaw.proyecto_bancosol.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_campana") // Ajusta el nombre de la tabla si es necesario
public class LogCampana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "action") // Guardará 'create', 'edit' o 'delete'
    private String action;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}