package ar.edu.utn.frba.dds.Models.Domain.Utils;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Oferta  extends Persistente {

    @Column
    private String nombre;

    @Column(name = "puntos_necesarios")
    private Float puntosNecesarios;

    @Column
    private String imagen;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_rubro")
    private Rubro rubro;
}