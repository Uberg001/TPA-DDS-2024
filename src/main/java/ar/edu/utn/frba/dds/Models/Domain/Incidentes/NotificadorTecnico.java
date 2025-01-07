package ar.edu.utn.frba.dds.Models.Domain.Incidentes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.RecomendadorHeladeras.RecomendadorHeladeras;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NotificadorTecnico { //Se podria hacer un package para las notificaciones
    private List<Tecnico> tecnicosCercanos = new ArrayList<>();
    private Heladera heladera;

    public Tecnico tecnicoMasCercano(List<Tecnico> tecnicos){
        RecomendadorHeladeras recomendadorHeladeras = new RecomendadorHeladeras();
        // Define un Comparator<Tecnico> que compara por distancia a la heladera
        Comparator<Tecnico> comparadorPorDistancia  = (tecnico1, tecnico2) -> {
            double distancia1 = recomendadorHeladeras.distanciaEnKilometrosEntre(
                    this.heladera.getDireccion().getCoordenada(),
                    tecnico1.getAreaCobertura().getPuntoCentral());
            double distancia2 = recomendadorHeladeras.distanciaEnKilometrosEntre(
                    this.heladera.getDireccion().getCoordenada(),
                    tecnico2.getAreaCobertura().getPuntoCentral());
            return Double.compare(distancia1, distancia2);
        };

        List<Tecnico> tecnicosOrdenadosPorCercania = tecnicos.stream()
                .sorted(comparadorPorDistancia)
                .toList();

        if(!tecnicosOrdenadosPorCercania.isEmpty() &&
                tecnicosOrdenadosPorCercania.get(0).getAreaCobertura().puntoEstaDentroDeArea(this.getHeladera().getDireccion().getCoordenada())){
            return tecnicosOrdenadosPorCercania.get(0);
        } else {
            throw new RuntimeException("No hay tecnicos cercanos para la heladera con coordenada: " + this.heladera.getDireccion().getCoordenada().toString());
        }
    }
}
