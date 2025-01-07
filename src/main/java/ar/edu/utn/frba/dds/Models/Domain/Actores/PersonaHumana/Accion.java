package ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Accion extends Persistente {

    @Enumerated(EnumType.STRING)
    private TipoAccion tipoAccion;

    @Column
    private LocalDateTime inicio;

    @Column
    private LocalDateTime fin;

    @Column
    private Boolean validez;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @Column
    private Integer tiempoParaRealizar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona")
    private PersonaHumana persona;

    public Accion(TipoAccion tipoAccion, LocalDateTime inicio, LocalDateTime fin, Boolean validez, Heladera heladera) {
        super();
    }

    public void Accion(TipoAccion tipoAccion, Heladera heladera) {
        this.tipoAccion = tipoAccion;
        this.heladera = heladera;
        this.inicio = LocalDateTime.now();
        this.fin = inicio.plusHours(tiempoParaRealizar);
        this.validez = true;
    }

}
