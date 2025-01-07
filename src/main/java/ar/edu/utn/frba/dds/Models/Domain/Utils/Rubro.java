package ar.edu.utn.frba.dds.Models.Domain.Utils;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Rubro extends Persistente {

    @Column
    private String nombre;

}