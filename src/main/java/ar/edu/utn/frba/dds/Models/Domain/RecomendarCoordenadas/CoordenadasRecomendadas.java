package ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas;

import java.time.LocalDate;
import java.util.*;
import lombok.*;

@Data
public class CoordenadasRecomendadas {
    private LocalDate fecha;
    private List<Coordenada> coordenadas=new ArrayList<>();
}