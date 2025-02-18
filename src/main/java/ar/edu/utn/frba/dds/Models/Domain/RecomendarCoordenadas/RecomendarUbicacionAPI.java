package ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;

public class RecomendarUbicacionAPI implements AdapterUbicacionRecomendada {
    private final String apiUrl;
    private final HttpClient httpClient;

    public RecomendarUbicacionAPI() {
        this.httpClient = HttpClient.newHttpClient();
        this.apiUrl = getApiUrlFromProperties() + "latitud=%s&longitud=%s&radio=%s";
    }


    private String getApiUrlFromProperties() {
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            return prop.getProperty("api.url");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo de propiedades", e);
        }
    }


    @Override
    public CoordenadasRecomendadas recomendarUbicacion(Area area) {
        try {
            List<Coordenada> coordenadas = getApi(area.getPuntoCentral().getLatitud(), area.getPuntoCentral().getLongitud(), area.getRadioM());
            CoordenadasRecomendadas recomendadas = new CoordenadasRecomendadas();
            recomendadas.setCoordenadas(coordenadas);
            return recomendadas;
        } catch (Exception e) {
            throw new RuntimeException("Error al recomendar ubicación", e);
        }
    }


    public List<Coordenada> getApi(BigDecimal latitud, BigDecimal longitud, BigDecimal radio) throws Exception {
        String url = String.format(apiUrl, latitud, longitud, radio);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<ArrayList<Coordenada>>() {
        }.getType();
        List<Coordenada> coordenadas = new Gson().fromJson(response.body(), listType);

        return coordenadas;
    }

}

