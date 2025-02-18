package ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.*;

import java.math.*;

import static java.lang.Math.*;
import static java.lang.Math.sqrt;

@Data
@Entity
public class Area extends Persistente {


    @Embedded
    private Coordenada puntoCentral;

    @Column
    private BigDecimal radioM; // en metros


    //metodo para calcular si un punto se encuentra dentro de un area dependiendo de la curvatura de la tierra (usando la formula de Haversine)
    // VER si esto deberia comparar cada punto sugerido por la api y como implementarlo luego en el controlador


    // por el momento el metodo deberia ser algo como este estilo:

    public boolean puntoEstaDentroDeArea(Coordenada punto) {

        double radioTierra = 6371e3; // radio de la Tierra en metros
        double lat1 = Math.toRadians(puntoCentral.getLatitud().doubleValue());
        double lat2 = Math.toRadians(punto.getLatitud().doubleValue());
        double deltaLat = Math.toRadians(punto.getLatitud().doubleValue() - puntoCentral.getLatitud().doubleValue());
        double deltaLon = Math.toRadians(punto.getLongitud().doubleValue() - puntoCentral.getLongitud().doubleValue());

        double a = sin(deltaLat / 2) * sin(deltaLat / 2) +
                cos(lat1) * cos(lat2) *
                        sin(deltaLon / 2) * sin(deltaLon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        double distancia = radioTierra * c; // distancia en metros

        return new BigDecimal(distancia).compareTo(radioM) <= 0;

    }


}