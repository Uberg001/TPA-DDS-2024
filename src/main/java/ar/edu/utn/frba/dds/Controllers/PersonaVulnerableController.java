package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class PersonaVulnerableController{

    //private PersonaVulnerableRepository personaVulnerableRepository;
    private PersonaVulnerableServiceImpl personaVulnerableService;

    public PersonaVulnerableController (PersonaVulnerableServiceImpl personaVulnerableService){
        this.personaVulnerableService = personaVulnerableService;
    }

    public void signUp(Context ctx) {
        PersonaVulnerableDTOSalida personaVulnerableDTOSalida = personaVulnerableService.createUser(this.setSignUpVariables(ctx));
        ctx.status(HttpStatus.CREATED);
        ServiceLocator.instanceOf(UsuarioController.class).logInId(ctx, personaVulnerableDTOSalida.getIdUsuario(),"vulnerable");
    }

    public PersonaVulnerableDTOEntrada setSignUpVariables(Context ctx) {
        PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada = new PersonaVulnerableDTOEntrada();
        personaVulnerableDTOEntrada.setNombre(ctx.formParam("nombre"));
        if (ctx.formParam("email") != null) {
            personaVulnerableDTOEntrada.setEmail(ctx.formParam("mail"));
        }
        personaVulnerableDTOEntrada.setUsuario(
                new UsuarioDTOEntrada(ctx.formParam("usuario"), ctx.formParam("password"),false));
        return personaVulnerableDTOEntrada;
    }

    /*
    * public void signUp(Context ctx) {
        PersonaJuridica personaJuridica = new PersonaJuridica();
        this.setSignUpVariables(personaJuridica, ctx);
        personaJuridicaRepository.guardar(personaJuridica);
        ctx.status(HttpStatus.CREATED);
        ServiceLocator.instanceOf(UsuarioController.class).logIn(ctx, personaJuridica.getUsuario(),"juridica");
    }

    public void setSignUpVariables(PersonaJuridica personaJuridica, Context ctx){
        Usuario usuario = new Usuario(ctx.formParam("nombre"),ctx.formParam("contrasenia"));
        personaJuridica.setUsuario(usuario);
        personaJuridica.setDocumento(new Documento(TipoDocumento.CUIT,ctx.formParam("cuit")));
        Contacto contactoMail       = new Contacto(ctx.formParam("email"), TipoContacto.MAIL);
        Contacto contactoTelefono   = new Contacto(ctx.formParam("telefono"),TipoContacto.TELEFONO);
        Contacto contactoTelegram   = new Contacto(ctx.formParam("telegram"),TipoContacto.TELEGRAM);
        personaJuridica.addContacto(contactoMail);
        personaJuridica.addContacto(contactoTelefono);
        personaJuridica.addContacto(contactoTelegram);
    }*/
}
