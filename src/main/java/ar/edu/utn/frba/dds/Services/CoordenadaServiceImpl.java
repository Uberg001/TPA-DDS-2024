package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoordenadaServiceImpl {

    public Coordenada obtenerCoordenada(Direccion direccion) {
        try {
            String direccionStr = direccion.getCalle() + " " + direccion.getAltura() + ", " + direccion.getLocalidad().getNombre() + ", " + direccion.getLocalidad().getProvincia().getNombre();
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + direccionStr.replace(" ", "%20") + "&format=json";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return new Coordenada(BigDecimal.valueOf(-34.6596012), BigDecimal.valueOf(-58.4705505));
                //throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JSONArray jsonArray = new JSONArray(inline.toString());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    return new Coordenada(BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
                } else {
                    throw new RuntimeException("No results found for the given address.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching coordinates.");
        }
    }
}
