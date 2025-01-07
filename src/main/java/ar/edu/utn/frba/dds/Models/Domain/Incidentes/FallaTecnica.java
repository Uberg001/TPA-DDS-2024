package ar.edu.utn.frba.dds.Models.Domain.Incidentes;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class FallaTecnica extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador")
    private PersonaHumana colaborador;

    @Column
    private String descripcion;

    @Column
    private String foto;
}
