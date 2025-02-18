package ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.ErrorMessages;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccesoDistribuir extends Acceso {


    private Heladera heladeraDestino;

    private LocalDateTime fechaSolicitud;


    private List<Vianda> viandas = new ArrayList<>();

    private Integer estado = Config.ESTADO_BASE_DISTRIBUIR;

    private LocalDateTime limiteDeTiempo = fechaSolicitud.plusHours(tiempoMaximo);

    @Override
    public boolean puedeUsarse() {
        if (estado == 1) {
            if (LocalDateTime.now().isAfter(this.limiteDeTiempo)) {
                System.out.println(ErrorMessages.ACCESO_VENCIDO);
                return false;
            } else if (!heladeraUtilizada.tieneEsteAccesoActivo(this)) {
                System.out.println(ErrorMessages.ACCESO_NO_REGISTRADO);
                return false;
            } else {
                return true;
            }
        } else if (estado == 2) {
            System.out.println(ErrorMessages.ACCESO_YA_REALIZADO);
            return false;
        } else if (estado == 3) {
            System.out.println(ErrorMessages.ACCESO_CANCELADO);
            return false;
        }
        return false;
    }

    @Override
    public boolean puedeUsarseEn(Heladera heladera) {
        return heladera == heladeraUtilizada && this.puedeUsarse();
    }

    @Override
    public void usarse() {
        if (this.puedeUsarse()) {
            if (viandas != null && !viandas.isEmpty()) { //agrego las viandas a la heladera destino
                for (Vianda vianda : viandas) {
                    heladeraUtilizada.retirarVianda(vianda);
                    heladeraDestino.agregarVianda(vianda);
                }
            }
            heladeraUtilizada.efectuarAcceso(this);
            this.estado = 2;
            this.fechaRealizado = LocalDateTime.now();
        }
    }

    @Override
    public LocalDateTime fechaDeVencimiento() {
        return limiteDeTiempo;
    }

    @Override
    public void setEstado(Integer estadoNuevo) {
        estado = estadoNuevo;
    }
}