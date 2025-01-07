package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;

import java.util.HashMap;

import java.util.Map;

@Entity
@Table(name = "Colaboracion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public class Colaboracion extends Persistente {

    @Transient
    private Map<String,Float> coeficientes=new HashMap<>();
    //[Nombre de la clase,coeficiente]
    {
        coeficientes.put("DonarDinero",0.5f);
        coeficientes.put("DistribuirViandas",1.0f);
        coeficientes.put("DonarVianda",1.5f);
        coeficientes.put("RegistrarPersonasVulnerables",2.0f);
        coeficientes.put("HacerseCargoDeHeladeras",5.0f);
    }

    public float getCoeficiente(String nombreClase) {
        return coeficientes.getOrDefault(nombreClase, 0.0f);
    }
}
