package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.UsuarioRepository;
import ar.edu.utn.frba.dds.Services.UsuarioServiceImpl;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class UsuarioController {

    private UsuarioServiceImpl usuarioService;


    private SessionController sessionController;

    public UsuarioController(UsuarioServiceImpl usuarioService, SessionController sessionController) {
        this.usuarioService = usuarioService;
        this.sessionController = sessionController;
    }

    public void index(Context ctx) {
        ctx.render("logIn/signIn.hbs");
    }

    public void signUp(Context ctx) {
        String persona = ctx.formParam("persona");
        if (persona == null) {
            ctx.redirect("/login");
            return;
        }
        switch (persona) {
            case "humana" -> ServiceLocator.instanceOf(PersonaHumanaController.class).signUp(ctx);
            case "juridica" -> ServiceLocator.instanceOf(PersonaJuridicaController.class).signUp(ctx);
            case "tecnico" -> ServiceLocator.instanceOf(TecnicoController.class).signUp(ctx);
            case "administrador" -> crearAdmin(ctx);
        }
    }

    public void crearAdmin(Context ctx) {
        Usuario usuario = new Usuario();
        usuario.setUsername(ctx.formParam("usuario"));
        usuario.setPassword(ctx.formParam("password"));
        usuario.setAdmin(true);
        usuario.setTipoActor(TipoActor.ADMINISTRADOR);

        ServiceLocator.instanceOf(UsuarioRepository.class).guardar(usuario);

        UsuarioDTOSalida usuarioDTOSalida = new UsuarioDTOSalida(usuario.getUsername(), usuario.getPassword());
        logIn(ctx, usuarioDTOSalida);
    }

    public void logIn(Context ctx, UsuarioDTOSalida usuarioDTOSalida) {

        Usuario usuarioVerificado = usuarioService.buscarPorCredenciales(usuarioDTOSalida.getNombre(),
                usuarioDTOSalida.getContrasenia());
        //String juridica = usuarioVerificado.getTipoActor().toString().toLowerCase();
        if (usuarioVerificado == null) {
            ctx.redirect("/login");
        } else {
            if (usuarioVerificado.getTipoActor().equals(TipoActor.JURIDICA))

                ctx.sessionAttribute("juridica", usuarioVerificado.getTipoActor().toString().toLowerCase());
            if (usuarioVerificado.getTipoActor().equals(TipoActor.HUMANA))
                ctx.sessionAttribute("humana", usuarioVerificado.getTipoActor().toString().toLowerCase());
            if (usuarioVerificado.getTipoActor().equals(TipoActor.ADMINISTRADOR))
                ctx.sessionAttribute("administrador", usuarioVerificado.getTipoActor().toString().toLowerCase());
            if (usuarioVerificado.getTipoActor().equals(TipoActor.TECNICO))
                ctx.sessionAttribute("tecnico", usuarioVerificado.getTipoActor().toString().toLowerCase());

            ctx.sessionAttribute("tipoPersona", usuarioVerificado.getTipoActor().toString().toLowerCase());
            ctx.sessionAttribute(usuarioVerificado.getTipoActor().toString().toLowerCase(), usuarioVerificado.getTipoActor().toString().toLowerCase());
            ctx.sessionAttribute("authenticaded", true);
            ctx.sessionAttribute("id", usuarioVerificado.getId());
            ctx.sessionAttribute("username", usuarioVerificado.getUsername());
            sessionController.setSession(usuarioVerificado.getId());
            ctx.sessionAttribute("session", sessionController.getSession());
            ctx.redirect("/");
        }
    }


    public void logInId(Context ctx, Long id, String tipoUsuario) {
        Usuario usuarioVerificado = usuarioService.buscarPorId(id);
        if (usuarioVerificado == null) {
            ctx.redirect("/login");
        } else {
            ctx.sessionAttribute("tipoPersona", tipoUsuario);
            ctx.sessionAttribute(tipoUsuario, tipoUsuario);
            ctx.sessionAttribute("authenticaded", true);
            ctx.sessionAttribute("id", usuarioVerificado.getId());
            ctx.sessionAttribute("username", usuarioVerificado.getUsername());
            ctx.redirect("/");
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("authenticaded", null);
        ctx.sessionAttribute("id", null);
        ctx.sessionAttribute("username", null);
        ctx.sessionAttribute("tipoPersona", null);
        ctx.redirect("/");
    }


}
