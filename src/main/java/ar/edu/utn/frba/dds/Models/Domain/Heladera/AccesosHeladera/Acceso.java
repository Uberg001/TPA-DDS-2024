package ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public abstract class Acceso extends Persistente {

    @ManyToOne
    @JoinColumn(name = "id_heladera")
    protected Heladera heladeraUtilizada = null;

    @Column
    protected LocalDateTime fechaRealizado = null;

    @ManyToOne
    @JoinColumn(name = "codigoTarjeta")
    protected Tarjeta codigoTarjeta;

    @Column
    protected LocalDateTime fechaSolicitud;

    public int tiempoMaximo = Config.TIEMPO_MAXIMO_ACCESO;

    public abstract boolean puedeUsarse();

    abstract boolean puedeUsarseEn(Heladera heladera);

    public abstract void usarse();

    public abstract LocalDateTime fechaDeVencimiento();

    public abstract void setEstado(Integer estadoNuevo);
}