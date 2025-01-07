package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.ContactoDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaHumanaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaHumanaDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.LocalDate;

public class PersonaHumanaController{
    public PersonaHumanaRepository personaHumanaRepository;

    public PersonaHumanaServiceImpl personaHumanaServiceImpl;
    public PersonaHumanaController (PersonaHumanaRepository personaHumanaRepository, PersonaHumanaServiceImpl personaHumanaServiceImpl){
        this.personaHumanaRepository = personaHumanaRepository;
        this.personaHumanaServiceImpl = personaHumanaServiceImpl;
    }

    public void signUp(Context ctx){
        PersonaHumanaDTOSalida personaHumanaDTOSalida = personaHumanaServiceImpl.createPH(getPersonaHumanaDTOEntrada(ctx));
        ctx.status(HttpStatus.CREATED);
        ServiceLocator.instanceOf(UsuarioController.class).logInId(ctx, personaHumanaDTOSalida.getIdUsuario(),"humana");
    }
    public void setSignUpVariables(PersonaHumana personaHumana, Context ctx) {
        // USUARIO
        Usuario usuario = new Usuario(ctx.formParam("usuario"), ctx.formParam("password"), false, TipoActor.HUMANA);
        personaHumana.setUsuario(usuario);
        personaHumana.setFechaNacimiento(LocalDate.now());
        personaHumana.setNombre(ctx.formParam("nombre"));
        personaHumana.setApellido(ctx.formParam("apellido"));

        if(ctx.formParam("telefono") != null){
            Contacto contactoTelefono = new Contacto(ctx.formParam("telefono"), TipoContacto.TELEFONO);
            personaHumana.addContacto(contactoTelefono);
        }
        if(ctx.formParam("email") != null){
            Contacto contactoMail = new Contacto(ctx.formParam("email"), TipoContacto.MAIL);
            personaHumana.addContacto(contactoMail);
        }
        if(ctx.formParam("telegram") != null){
            Contacto contactoTelegram = new Contacto(ctx.formParam("telegram"), TipoContacto.TELEGRAM);
            personaHumana.addContacto(contactoTelegram);
        }
    }

    private PersonaHumanaDTOEntrada getPersonaHumanaDTOEntrada(Context ctx) {
        PersonaHumanaDTOEntrada personaHumanaDTOEntrada = new PersonaHumanaDTOEntrada();
        personaHumanaDTOEntrada.setNombre(ctx.formParam("nombre"));
        personaHumanaDTOEntrada.setApellido(ctx.formParam("apellido"));
        personaHumanaDTOEntrada.setFechaNacimiento(null); //TODO: Agregar fecha de nacimiento al formulario
        personaHumanaDTOEntrada.setUsuario(new UsuarioDTOEntrada(ctx.formParam("usuario"), ctx.formParam("password"),false));
        personaHumanaDTOEntrada.setPuntaje(new Puntaje()); //Incializa el puntaje en 0
        personaHumanaDTOEntrada.setTarjeta(new Tarjeta()); //Crea la tarjeta con el codigo autogenerado

        if (ctx.formParam("telefono") != null) {
            personaHumanaDTOEntrada.addContacto(new ContactoDTOEntrada(ctx.formParam("telefono"), "TELEFONO"));
        }
        if (ctx.formParam("email") != null) {
            personaHumanaDTOEntrada.addContacto(new ContactoDTOEntrada(ctx.formParam("email"), "MAIL"));
        }
        if (ctx.formParam("telegram") != null) {
            personaHumanaDTOEntrada.addContacto(new ContactoDTOEntrada(ctx.formParam("telegram"), "TELEGRAM"));
        }

        return personaHumanaDTOEntrada;
    }

}
