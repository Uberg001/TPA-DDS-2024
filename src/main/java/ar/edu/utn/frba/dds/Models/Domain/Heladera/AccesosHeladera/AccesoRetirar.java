package ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.ErrorMessages;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import javax.persistence.DiscriminatorValue;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Data
public class AccesoRetirar extends Acceso{
    private Vianda viandaRetirada;
    private Heladera heladeraUtilizada = null;
    private LocalDateTime fechaRealizado = null;
    private Integer estado = Config.ESTADO_BASE_DISTRIBUIR;
    private Tarjeta tarjeta;


    @Override
    public boolean puedeUsarse(){
        PersonaVulnerable duenio = (PersonaVulnerable) tarjeta.getDuenio(); //Está bien traer el dueño de esta manera?
        int totalAccesosPorDia = Config.MINIMO_ACCESOS_DIARIOS;
        if (duenio.poseeMenoresACargo()) {
            totalAccesosPorDia += duenio.cantidadMenoresACargo() * Config.MULTIPLICADOR_ACCESO_MENORES;
        }
        if (tarjeta.getAccesosDiarios().size()>=totalAccesosPorDia){
            System.out.println(ErrorMessages.MAXIMO_ACCESOS_DIARIOS);
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void usarse(){
        if (this.puedeUsarse()) {
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
        return null;
    }

    @Override
    public void setEstado(Integer estadoNuevo){
        estado=estadoNuevo;
    }
}
