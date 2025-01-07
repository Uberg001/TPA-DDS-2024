package ar.edu.utn.frba.dds.Models.Domain.Suscripcion;

import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Enviador;
import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Mensaje;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Desperfecto")
@AllArgsConstructor
public class SuscripcionDesperfecto extends Suscripcion {

    @Override
    public void analizarEstadoHeladera(Heladera heladera) {
//        this.getPersonas().forEach(persona -> {
//            Mensaje mensaje = new Mensaje(
//                    "Desperfecto en la heladera",
//                    persona.getNombre() + ", la heladera presenta un desperfecto, por favor revisarla.",
//                    persona
//            );
//            this.getEnviador().enviar(mensaje);
//            this.getMensajes().add(mensaje);
//        });
    }
}
