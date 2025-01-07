package ar.edu.utn.frba.dds.Controllers;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.javalin.http.Context;

public class RecomendadorColaboradoresController{
    private final String apiUrl;
    private final HttpClient httpClient;

    public RecomendadorColaboradoresController() {
        this.httpClient = HttpClient.newHttpClient();
        this.apiUrl = "http://localhost:8082/colaboradores"+ 
        "?minPuntos=%s&minDonaciones=%s&page=%s&limit=%s&sort=%s";
    }
        
    public void index(Context ctx) {
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }

        ctx.render("recomendarColaboradores/recomendadorColaboradores.hbs");
    }
    
    public void getColaboradoresRecomendados(Context ctx,Double minPuntos, Integer minDonaciones, Integer page, Integer limit, String sortString) throws Exception {
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        
        String url = String.format(apiUrl, minPuntos,minDonaciones,page,limit,sortString);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<ArrayList<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> colaboradores = new Gson().fromJson(response.body(), listType);
        
        // Crear un mapa para el modelo
        Map<String, Object> model = new HashMap<>();
        model.put("colaboradores", colaboradores);

        // Renderizar el template
        ctx.render("recomendarColaboradores/mostrarColaboradores.hbs", model);
    }
}
