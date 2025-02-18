package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SuscripcionDTOSalida {
    private Long id;
    private List<HeladeraDTOSalida> heladeras = new ArrayList<>();

    public void addHeladera(HeladeraDTOSalida heladeraDTOSalida){
        this.heladeras.add(heladeraDTOSalida);
    }
}
