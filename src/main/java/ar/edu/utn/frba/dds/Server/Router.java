package ar.edu.utn.frba.dds.Server;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Controllers.*;
import ar.edu.utn.frba.dds.Controllers.Colaboraciones.ImportarCSVController;
import ar.edu.utn.frba.dds.Controllers.Suscripciones.SuscripcionController;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Utils.AJAX_Dashboard.Consultas;

import io.javalin.Javalin;
import java.util.List;
import java.util.Map;


public class Router {

    public static void init(Javalin app) {
        // ##### Integracion con servicio grupo 5 #####
        app.get("/recomendadorColaboradores", ctx -> ServiceLocator.instanceOf(RecomendadorColaboradoresController.class).index(ctx));
        app.get("/recomendadorColaboradores/colaboradores/", ctx -> ServiceLocator.instanceOf(RecomendadorColaboradoresController.class).
                getColaboradoresRecomendados(ctx,
                        Double.valueOf(ctx.queryParam("minPuntos")),
                        Integer.valueOf(ctx.queryParam("minDonaciones")),
                        Integer.valueOf(ctx.queryParam("page")),
                        Integer.valueOf(ctx.queryParam("limit")),
                        ctx.queryParam("sortString")));
        // ##### Manejo de usuarios #####
        app.get("/login", ctx -> ServiceLocator.instanceOf(UsuarioController.class).index(ctx));
        app.post("/signUp", ctx -> ServiceLocator.instanceOf(UsuarioController.class).signUp(ctx));
        app.post("/logIn", ctx -> ServiceLocator.instanceOf(UsuarioController.class).logIn(ctx, new UsuarioDTOSalida(ctx.formParam("usuario"), ctx.formParam("password"))));
        // ########## HOME ##############
        app.get("/", ctx -> ServiceLocator.instanceOf(HomeController.class).index(ctx));
        // ############ COLABORACIONES #############
        app.get("/colaboracion", ctx -> ServiceLocator.instanceOf(ColaboracionController.class).index(ctx, ctx.sessionAttribute("tipoPersona")));
        app.get("/formCargarColaboracion", ctx -> ServiceLocator.instanceOf(ColaboracionController.class).formColaboracion(ctx));
        app.post("/colaboracion", ctx -> ServiceLocator.instanceOf(ColaboracionController.class).cargarColaboracion(ctx));
        // ############ SUSCRIPCIONES #############
        app.get("/suscripcion", ctx -> ServiceLocator.instanceOf(SuscripcionController.class).index(ctx));
        app.post("/suscripcion", ctx -> ServiceLocator.instanceOf(SuscripcionController.class).suscribirse(ctx));
        app.post("/guardarSuscripcion", ctx -> ServiceLocator.instanceOf(SuscripcionController.class).guardarSuscripcion(ctx));
        // ################ OFERTAS Y PUNTOS ##################
        app.get("/altaOferta", ctx -> ServiceLocator.instanceOf(OfertaController.class).index(ctx, ctx.sessionAttribute("tipoPersona")));
        app.post("/oferta", ctx -> ServiceLocator.instanceOf(OfertaController.class).cargarOferta(ctx));
        app.get("/consultaPuntos", ctx -> ServiceLocator.instanceOf(PersonaJuridicaController.class).index(ctx));
        app.get("/canjePuntos", ctx -> ServiceLocator.instanceOf(OfertaController.class).canjearPuntos(ctx, ctx.sessionAttribute("tipoPersona")));
        app.post("/canje", ctx -> ServiceLocator.instanceOf(OfertaController.class).completarCanje(ctx));
        app.get("/misProductosYServicios", ctx -> ServiceLocator.instanceOf(OfertaController.class).misProductosYServicios(ctx));


        app.get("/alertas", ctx -> {
            Long id = ctx.sessionAttribute("id");
            String username = ctx.sessionAttribute("username");
            SessionDTO sessionDTO = ctx.sessionAttribute("session");
            //String tipoPersona = ctx.sessionAttribute("tipoUsuario");
            ServiceLocator.instanceOf(AlertasController.class).index(ctx, id, username,sessionDTO);
        });

        app.get("/alerta/{idIncidente}", ctx -> {
            Long idIncidente = Long.valueOf(ctx.pathParam("idIncidente"));
            ServiceLocator.instanceOf(AlertasController.class).getAlertasIncidente(idIncidente,ctx);
        });


        // ########## IMPORTAR COLABORACIONES (Admins) ##############
        app.get("/colaboracionesCSV", ctx ->{
            if(ctx.sessionAttribute("tipoPersona") != null) {
                if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                    ctx.status(403).render("errors/403.hbs");
                }
            } else {
                ctx.redirect("/login");
            }
            ctx.render("cargarColaboracion/colaboracionesCSV.hbs");
        });
        app.post("/colaboracionesCSV", ctx -> ServiceLocator.instanceOf(ImportarCSVController.class).recibirCSV(ctx));
        
        app.get("/falla/{idHeladera}", ctx -> {
            Long id = Long.valueOf(ctx.pathParam("idHeladera"));
            String username = ctx.sessionAttribute("username");
            if(ctx.sessionAttribute("tipoPersona") != null) {
                if(!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                    ctx.status(403).render("errors/403.hbs");
                }
            } else {
                ctx.redirect("/login");
            }
            ServiceLocator.instanceOf(AlertasController.class).reportarAlerta(ctx, id, username);
        });

        app.get("/resolverFalla/{idIncidente}", ctx -> {
            Long idIncidente = Long.valueOf(ctx.pathParam("idIncidente"));
            SessionDTO sessionDTO = ctx.sessionAttribute("session");
            ServiceLocator.instanceOf(ResolucionController.class).index(ctx,sessionDTO,idIncidente);
        });

        app.post("/resolverFalla/{idIncidente}", ctx -> {
            Long idIncidente = Long.valueOf(ctx.pathParam("idIncidente"));
            SessionDTO sessionDTO = ctx.sessionAttribute("session");
            ServiceLocator.instanceOf(ResolucionController.class).resolver(ctx,sessionDTO,idIncidente);
        });

        app.post("/falla", ctx -> ServiceLocator.instanceOf(AlertasController.class).postAlerta(ctx));

        // ########## MAPA ##############
        app.get("/mapa", ctx -> {
            ServiceLocator.instanceOf(MapaController.class).index(ctx);
        });
        app.get("/mapa/hbs-content", ctx -> {
            ctx.render("contenidoDinamico.hbs");
        });

        // ########## REPORTES (Solo para admins) ##############
        app.get("/reportes", ctx -> ServiceLocator.instanceOf(ReporteController.class).index(ctx));
        
        // ########## LOGOUT ##############
        app.get("/logout", ctx -> ServiceLocator.instanceOf(UsuarioController.class).logout(ctx));
    
        // ########## DASHBOARD PARA ADMINS ##############
        app.get("/dashboard", ctx -> ServiceLocator.instanceOf(DashboardController.class).index(ctx));
        app.get("/dashboard/baseDeDatos",ctx->ServiceLocator.instanceOf(DashboardController.class).mostrarDB(ctx));
        // TODO: NO DEBERIA HABER LOGICA EN EL ROUTER!!
        app.get("/api/{id}", ctx -> {
            if(ctx.sessionAttribute("tipoPersona") != null) {
                if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                    ctx.status(403).render("errors/403.hbs");
                }
            } else {
                ctx.redirect("/login");
            }
            String id = ctx.pathParam("id");

            // Obtener los datos según el identificador (id)
            List<Map<String, String>> data = Consultas.obtenerDatos(id);

            if (data != null) {
                ctx.json(data); // Enviar los datos como respuesta JSON
            } else {
                ctx.status(404).result("No se encontraron datos para la ruta: " + id);
            }
        });
        app.get("/api/estadisticas/{id}", ctx -> {
            if(ctx.sessionAttribute("tipoPersona") != null) {
                if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                    ctx.status(403).render("errors/403.hbs");
                }
            } else {
                ctx.redirect("/login");
            }
            String id = ctx.pathParam("id");

            // Obtener los datos según el identificador (id)
            List<Map<String, String>> data = Consultas.obtenerEstadisticas(id);

            if (data != null) {
                ctx.json(data); // Enviar los datos como respuesta JSON
            } else {
                ctx.status(404).result("No se encontraron datos para la ruta: " + id);
            }
        });
    }
}