package ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas;

import com.google.gson.Gson;
import lombok.Data;
import java.util.List;


@Data
public class CoordenadasResponse {
    private List<Coordenada> coordenadas;

    public static CoordenadasResponse convertirDesdeJson(String json) {
        return new Gson().fromJson(json, CoordenadasResponse.class);
    }
}