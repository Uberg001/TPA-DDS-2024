package ar.edu.utn.frba.dds.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Provider.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Persona;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.OfrecerProductos;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Canje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Oferta;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Rubro;
import ar.edu.utn.frba.dds.Models.Repositories.*;
import ar.edu.utn.frba.dds.Services.OfertaServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaJuridicaServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class OfertaController {

    private OfertaServiceImpl ofertaService;
    private PersonaHumanaServiceImpl personaHumanaService;
    private PersonaJuridicaServiceImpl personaJuridicaService;

    public OfertaController(OfertaServiceImpl oferta, PersonaHumanaServiceImpl personaHumana, PersonaJuridicaServiceImpl personaJuridica) {
        this.ofertaService = oferta;
        this.personaHumanaService = personaHumana;
        this.personaJuridicaService = personaJuridica;
    }

    public void index(Context ctx, String tipoUsuario) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        ctx.render("consultarPuntos/crearOfertas.hbs");
    }

    public void cargarOferta(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        OfertaDTOEntrada ofertaDTO = createOfertaDTO(ctx);
        ofertaService.cargarOferta(ofertaDTO);
        OfrecerProductos colaboracion = new OfrecerProductos();
        colaboracion.setFechaOferta(LocalDate.now());
        ServiceLocator.instanceOf(OfrecerProductosRepository.class).guardar(colaboracion);
        ctx.redirect("/");
    }


    public void canjearPuntos(Context ctx, String tipoUsuario) {

        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }

        List<OfertaDTOSalida> ofertas = ofertaService.obtenerOfertas();
        float puntaje = 0;

        if (tipoUsuario.equals("humana")) {
            puntaje = personaHumanaService.getPoints(ctx.sessionAttribute("id"));
        } else if (tipoUsuario.equals("juridica")) {
            puntaje = personaJuridicaService.getPoints(ctx.sessionAttribute("id"));
        }

        HashMap<String, Object> model = new HashMap<>();
        ofertas.forEach(oferta -> {
            if (oferta.getImagen() != null) {
                oferta.setImagen(oferta.getImagen().replace("src\\main\\resources\\public\\", ""));
            }
        });
        model.put("ofertas", ofertas);
        model.put("puntaje", puntaje);
        model.put("cantidadProductosTotales", ofertas.size());
        ctx.render("consultarPuntos/canjearOfertas.hbs", model);
    }

    public void completarCanje(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }

        this.ofertaService.completarCanje(ctx);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ctx.redirect("/");
        ctx.result("Canje realizado con exito");
    }

    private OfertaDTOEntrada createOfertaDTO(Context ctx) {

        OfertaDTOEntrada ofertaDTO = new OfertaDTOEntrada();
        ofertaDTO.setNombre(ctx.formParam("nombre"));
        ofertaDTO.setPuntosNecesarios(Float.valueOf(ctx.formParam("puntosNecesarios") + "f"));
        ofertaDTO.setRubro(ctx.formParam("rubro"));
        ofertaDTO.setImagen(ctx.formParam("imagen"));
//        UploadedFile imagen = ctx.uploadedFile("imagen");
//        //Guardo la imagen
//        if (imagen != null) {
//            String uploadDir = "src\\main\\resources\\public\\images\\productos";
//            String fileName = imagen.filename();
//            Path filePath = Paths.get(uploadDir, fileName);
//            try {
//                Files.copy(imagen.content(), filePath);
//                ofertaDTO.setImagen(filePath.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//                ctx.status(500).result("Error al guardar la imagen");
//            }
//        }
        return ofertaDTO;
    }

    public void misProductosYServicios(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("juridica") &&
                    !ctx.sessionAttribute("tipoPersona").equals("administrador") &&
                    !ctx.sessionAttribute("tipoPersona").equals("humana")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }

        List<Canje> todosCanjes = ServiceLocator.instanceOf(CanjeRepository.class).buscarTodos();
        List<Canje> canjePorPersona = new ArrayList<>();
        if (ctx.sessionAttribute("tipoPersona").equals("juridica")) {
            canjePorPersona = todosCanjes.stream()
                    .filter(c -> c.getPersonaJuridica() != null && c.getPersonaJuridica().getUsuario().getId().equals(ctx.sessionAttribute("id")))
                    .toList();
        } else {
            canjePorPersona = todosCanjes.stream()
                    .filter(c -> c.getPersonaHumana() != null && c.getPersonaHumana().getUsuario().getId().equals(ctx.sessionAttribute("id")))
                    .toList();
        }
        canjePorPersona.forEach(canje -> {
            if (canje.getOfertaARealizar().getImagen() != null) {
                canje.getOfertaARealizar().setImagen(canje.getOfertaARealizar().getImagen().replace("src\\main\\resources\\public\\", ""));
            }
        });
        HashMap<String, Object> model = new HashMap<>();
        model.put("canjes", canjePorPersona);
        model.put("persona", ctx.sessionAttribute("username"));
        ctx.render("consultarPuntos/misProductosYServicios.hbs", model);
    }

}
