package ar.edu.utn.frba.dds.Models.Domain.Utils;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;

import lombok.*;

import java.util.*;

@Data
@Embeddable
@AllArgsConstructor
public class Puntaje{

    @Column
    private Float puntaje;

    @Transient
    private List<Canje> canjesRealizados;

    public Puntaje(){
        this.puntaje = 0f;
        this.canjesRealizados = new ArrayList<>();
    }
    
    public void sumarPuntaje(Float cantidadPuntos){
        this.puntaje += cantidadPuntos;
    }

    public void restarPuntaje(Float cantidadPuntos){
        this.puntaje -= cantidadPuntos;
    }

    public boolean puedeRealizarCanje(Canje canje){
        return this.puntaje >= canje.getPuntosCanjeados().floatValue();
    }

    public void realizarCanje(Canje canje){
        if(puedeRealizarCanje(canje)){
            this.canjesRealizados.add(canje);
            restarPuntaje(canje.getPuntosCanjeados().floatValue());
        }
    }
}