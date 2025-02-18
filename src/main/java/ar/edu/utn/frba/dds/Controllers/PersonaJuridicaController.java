package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.ContactoDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaJuridicaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaJuridicaDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Documento.TipoDocumento;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class PersonaJuridicaController{
    public PersonaJuridicaServiceImpl personaJuridicaService;
    public PersonaJuridicaController (PersonaJuridicaServiceImpl personaJuridicaService){
        this.personaJuridicaService = personaJuridicaService;
    }
    public void index(Context ctx){
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        //PersonaJuridica pj = this.personaJuridicaRepository.findPersonaJuridicaById(5L);
        Map<String, Object> model = new HashMap<>();
        model.put("puntaje", 100);
        ctx.render("consultarPuntos/consultaPuntos.hbs",model);
    }

    public void signUp(Context ctx) {

        PersonaJuridicaDTOSalida personaJuridicaDTOSalida = personaJuridicaService.createUser(setSignUpVariables(ctx));
        ctx.status(HttpStatus.CREATED);
        ServiceLocator.instanceOf(UsuarioController.class).logInId(ctx, personaJuridicaDTOSalida.getIdUsuario(),"juridica");
    }

    public PersonaJuridicaDTOEntrada setSignUpVariables(Context ctx) {
        PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada = new PersonaJuridicaDTOEntrada();
        personaJuridicaDTOEntrada.setNombre(ctx.formParam("nombre"));
        personaJuridicaDTOEntrada.setTipoDocumento(TipoDocumento.CUIT.toString());
        personaJuridicaDTOEntrada.setNumeroDocumento(ctx.formParam("cuit"));
        personaJuridicaDTOEntrada.setPuntaje(new Puntaje());

        if (ctx.formParam("telefono") != null) {
            ContactoDTOEntrada contactoTelefono = new ContactoDTOEntrada(ctx.formParam("telefono"), TipoContacto.TELEFONO.toString());
            personaJuridicaDTOEntrada.addContacto(contactoTelefono);
        }
        if (ctx.formParam("email") != null) {
            ContactoDTOEntrada contactoMail = new ContactoDTOEntrada(ctx.formParam("email"), TipoContacto.MAIL.toString());
            personaJuridicaDTOEntrada.addContacto(contactoMail);
        }
        if (ctx.formParam("telegram") != null) {
            ContactoDTOEntrada contactoTelegram = new ContactoDTOEntrada(ctx.formParam("telegram"), TipoContacto.TELEGRAM.toString());
            personaJuridicaDTOEntrada.addContacto(contactoTelegram);
        }


        personaJuridicaDTOEntrada.setUsuario(new UsuarioDTOEntrada(ctx.formParam("usuario"), ctx.formParam("password"),false));
        personaJuridicaDTOEntrada.setPJtipo(ctx.formParam("tipoPJ"));
        personaJuridicaDTOEntrada.setRubro(ctx.formParam("rubro"));
        return personaJuridicaDTOEntrada;
    }

    /*
    * @Override
    public void index(Context context) {
        List<Suscripcion> suscripciones = this.suscripcionRepository.buscarTodos();
        Map<String, Object> model = new HashMap<>();
        model.put("suscripciones", suscripciones);
        model.put("titulo", "Listado de suscripciones");
        context.render("suscripciones/suscripciones.hbs",model);
    }
    * */
}