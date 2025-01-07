package ar.edu.utn.frba.dds.Models.Domain.Heladera;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class EstadoHeladera extends Persistente {

    @Column
    private LocalDateTime fechaInicio;

    @Column
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    private TipoEstadoHeladera tipoEstadoHeladera;
}
