package ar.edu.utn.frba.dds.Models.Domain.Utils;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class EstadoVisita extends Persistente {

    @Column
    private LocalDate fechaHora;

    @Column
    private String motivo;

    @Enumerated(EnumType.STRING)
    private TipoEstadoVisita tipoEstado;
}
