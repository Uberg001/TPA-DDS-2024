package ar.edu.utn.frba.dds.Models.Domain.Heladera;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AperturaHeladera extends Persistente {

    @Column
    private LocalDateTime fecha;

    @Column
    private Boolean efectiva;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona")
    private PersonaHumana persona;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;
}
