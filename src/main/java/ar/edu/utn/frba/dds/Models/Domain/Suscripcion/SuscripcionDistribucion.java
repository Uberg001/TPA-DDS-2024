package ar.edu.utn.frba.dds.Models.Domain.Suscripcion;

import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Enviador;
import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Mensaje;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("Distribucion")
@AllArgsConstructor @NoArgsConstructor(force = true)
@Data
public class SuscripcionDistribucion extends Suscripcion {

    @Column(name = "cantidadDeViandasMaximas")
    private int cantidadDeViandasMaximas;

    @Override
    public void analizarEstadoHeladera(Heladera heladera) {
//        if(heladera.cantidadViandas() > this.cantidadDeViandasMaximas){
//            this.getPersonas().forEach(persona -> {
//                Mensaje mensaje = new Mensaje(
//                        "Distribución de viandas",
//                        persona.getNombre() + ", la heladera tiene muchas viandas, por favor distribuirlas.",
//                        persona
//                );
//                this.getEnviador().enviar(mensaje);
//                this.getMensajes().add(mensaje);
//            });
//        }
    }
}
