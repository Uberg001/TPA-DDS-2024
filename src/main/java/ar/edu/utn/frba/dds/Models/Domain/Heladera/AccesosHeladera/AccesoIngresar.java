package ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera;

import ar.edu.utn.frba.dds.Models.Domain.Config;
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
public class AccesoIngresar extends Acceso{

    private LocalDateTime fechaSolicitud;


    private List<Vianda> viandas = new ArrayList<>();

    private Heladera heladeraUtilizada = null;

    private LocalDateTime fechaRealizado = null;

    private Integer estado = 0;



    private LocalDateTime limiteDeTiempo; //= fechaSolicitud.plusHours(tiempoMaximo); Esto rompe los tests

    private Tarjeta tarjeta;

    @Override
    public boolean puedeUsarse(){
        Boolean puedeUsarse = false;
        switch (estado) {
            case 1:
                // Si estado es 1 (activo), se ejecuta el método this.puedeUsarse()
                if(LocalDateTime.now().isAfter(this.limiteDeTiempo)){ //se venció
                    System.out.println("el acceso está vencido");
                    puedeUsarse = false;
                } else if (!heladeraUtilizada.tieneEsteAccesoActivo(this)) {
                    System.out.println("el acceso no está registrado");
                } else {
                    puedeUsarse = true;
                }
                break;
            case 2:
                // Si estado es 2 (realizado), se imprime "el acceso ya se realizó"
                System.out.println("el acceso ya se realizó");
                puedeUsarse = false;
                break;
            case 3:
                // Si estado es 3 (vencido), se imprime "el acceso está vencido"
                System.out.println("el acceso está vencido");
                puedeUsarse = false;
                break;
        }
        return puedeUsarse;
    }

    @Override
    public void usarse(){
        if (this.puedeUsarse()) {
            if (viandas != null && !viandas.isEmpty()) { //agrego las viandas a la heladera destino
                for (Vianda vianda : viandas) {
                    heladeraUtilizada.agregarVianda(vianda);
                }
            }
            heladeraUtilizada.efectuarAcceso(this);
            this.estado = 2;
            this.fechaRealizado = LocalDateTime.now();
        }
    }

    @Override
    public boolean puedeUsarseEn(Heladera heladera) {
        return heladera==heladeraUtilizada && this.puedeUsarse();
    }
    @Override
    public LocalDateTime fechaDeVencimiento(){
        return limiteDeTiempo;
    }

    @Override
    public void setEstado(Integer estadoNuevo){
        estado=estadoNuevo;
    }
}
