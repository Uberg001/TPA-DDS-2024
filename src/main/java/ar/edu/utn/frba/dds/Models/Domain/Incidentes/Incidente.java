package ar.edu.utn.frba.dds.Models.Domain.Incidentes;

import java.time.LocalDateTime;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "incidente")
public class Incidente extends Persistente {


    @Column
    private LocalDateTime fecha;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @Enumerated(EnumType.STRING)
    private TipoIncidente tipoIncidente;

    @OneToOne
    private FallaTecnica fallaTecnica;

    @Transient
    private NotificadorTecnico notificacion;

    @Column(name = "cargada_por_pagina")
    private Boolean cargadaPorPagina;

    @Column
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoIncidente estadoIncidente;

}
