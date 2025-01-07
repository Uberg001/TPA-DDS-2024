package ar.edu.utn.frba.dds.Models.Domain.Incidentes;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.TipoEstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resolucion extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @ManyToOne(cascade = CascadeType.ALL)
    private Incidente incidente;

    @Column
    private String foto_path;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fecha;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @Enumerated(EnumType.STRING)
    private TipoEstadoHeladera estadoHeladera;


}
