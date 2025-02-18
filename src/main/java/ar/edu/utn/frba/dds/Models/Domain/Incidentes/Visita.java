package ar.edu.utn.frba.dds.Models.Domain.Incidentes;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVisita;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Visita extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;


    @Column
    private String descripcion;


    @Column(name = "foto_url")
    private String foto;

    @OneToOne
    @JoinColumn(name = "id_estado")
    private EstadoVisita estado;

    @Column
    private LocalDate fecha;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_incidente")
    private Incidente incidente;
}
