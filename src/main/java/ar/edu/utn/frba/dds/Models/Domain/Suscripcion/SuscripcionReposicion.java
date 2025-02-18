package ar.edu.utn.frba.dds.Models.Domain.Suscripcion;

import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Enviador;
import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Mensaje;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("Reposicion")
@AllArgsConstructor @NoArgsConstructor(force = true)
@Builder
@Data
public class SuscripcionReposicion extends Suscripcion {

    @Column(name = "cantidadDeViandasMinimas")
    private int cantidadDeViandasMinimas;

    @Override
    public void analizarEstadoHeladera(Heladera heladera) {
//        if(heladera.cantidadViandas() < this.cantidadDeViandasMinimas){
//            this.getPersonas().forEach(persona -> {
//                Mensaje mensaje = new Mensaje(
//                        "Reposición de viandas",
//                        persona.getNombre() + ", la heladera tiene pocas viandas, por favor reponerlas.",
//                        persona
//                );
//                this.getEnviador().enviar(mensaje);
//                this.getMensajes().add(mensaje);
//            });
//        }
    }
}
