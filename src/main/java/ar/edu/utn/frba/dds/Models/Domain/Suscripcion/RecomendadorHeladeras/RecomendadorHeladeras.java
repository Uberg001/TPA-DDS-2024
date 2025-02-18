package ar.edu.utn.frba.dds.Models.Domain.Suscripcion.RecomendadorHeladeras;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;

import java.util.List;

public class RecomendadorHeladeras {
    public List<Heladera> recomendarHeladeras(PersonaHumana persona, List<Heladera> heladeras){
        return heladeras.stream().filter(heladera ->
                this.estaCerca(heladera.getDireccion().getCoordenada(),
                persona.getDireccion().getCoordenada(),
                3)
        ).toList();
    }

    // Método para calcular la distancia entre dos coordenadas en kilómetros
    public double distanciaEnKilometrosEntre(Coordenada unaCoordenada, Coordenada otraCoordenada) {
        // Radio de la Tierra en kilómetros
        final double RADIO_TIERRA = 6371.0;

        // Convertir las latitudes y longitudes a radianes
        double lat1 = Math.toRadians(unaCoordenada.getLatitud().doubleValue());
        double lon1 = Math.toRadians(unaCoordenada.getLongitud().doubleValue());
        double lat2 = Math.toRadians(otraCoordenada.getLatitud().doubleValue());
        double lon2 = Math.toRadians(otraCoordenada.getLongitud().doubleValue());

        // Fórmula de Haversine
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a    = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c    = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia en kilómetros
        double distancia = RADIO_TIERRA * c;
        return distancia;
    }

    // Método para verificar si otra coordenada está cerca (menos de 3 km)
    public boolean estaCerca(Coordenada unaCoordenada, Coordenada otraCoordenada, double cantidadEnKilometrosDeCercania) {
        double distancia = this.distanciaEnKilometrosEntre(unaCoordenada, otraCoordenada);
        return distancia < cantidadEnKilometrosDeCercania;
    }

}
