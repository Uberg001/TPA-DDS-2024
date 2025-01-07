package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaHumanaDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.RegistrarPersonasVulnerables;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Localidad;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Provincia;
import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Documento.TipoDocumento;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.Models.Repositories.RegistrarPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.Services.CoordenadaServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import io.javalin.http.Context;

public class RegistrarPersonasVulnerablesController {

    
    public void cargarPersonaVulnerable(Context ctx) {
        RegistrarPersonasVulnerables colaboracion = new RegistrarPersonasVulnerables();
        PersonaVulnerable personaVulnerable = new PersonaVulnerable();
        personaVulnerable.setNombre(ctx.formParam("nombre"));
        personaVulnerable.setFechaNacimiento(LocalDate.parse(ctx.formParam("fechaNacimiento")));
        personaVulnerable.setFechaAlta(LocalDate.parse(ctx.formParam("fechaAlta")));
        personaVulnerable.setEnCondicionCalle("on".equals(ctx.formParam("enCondicionCalle")));
        
        Direccion direccion = new Direccion();
        direccion.setCalle(ctx.formParam("calle"));
        direccion.setAltura(Integer.parseInt(ctx.formParam("altura")));


        Localidad localidad = new Localidad();
        localidad.setNombre(ctx.formParam("localidad"));
        Provincia provincia = new Provincia();
        provincia.setNombre(ctx.formParam("provincia"));
        localidad.setProvincia(provincia);

        direccion.setLocalidad(localidad);
        direccion.setCoordenada(new CoordenadaServiceImpl().obtenerCoordenada(direccion));
        personaVulnerable.setDomicilio(direccion);

        Documento documento = new Documento();
        documento.setTipo(TipoDocumento.valueOf(ctx.formParam("tipoDocumento")));
        documento.setNumero(ctx.formParam("numeroDocumento"));

        personaVulnerable.setDocumento(documento);
        
        Tarjeta tarjeta=new Tarjeta();
        tarjeta.setCodigo(tarjeta.generarCodigo());

        personaVulnerable.setTarjeta(tarjeta);
        
        colaboracion.getPersonasVulnerables().add(personaVulnerable);

        List<PersonaVulnerable> personasAcargo=new ArrayList<PersonaVulnerable>();
        if("on".equals(ctx.formParam("personasACargo"))){
            int i=1;
            while(ctx.formParam("nombrePersona-"+i)!=null){
                PersonaVulnerable personaAcargo = new PersonaVulnerable();
                personaAcargo.setNombre(ctx.formParam("nombrePersona-"+i));
                personaAcargo.setFechaNacimiento(LocalDate.parse(ctx.formParam("fechaNacimientoPersona-"+i)));
                personaAcargo.setFechaAlta(LocalDate.parse(ctx.formParam("fechaAltaPersona-"+i)));
                personaAcargo.setEnCondicionCalle("on".equals(ctx.formParam("enCondicionCallePersona-"+i)));
                
                Direccion direccionAcargo = new Direccion();
                direccionAcargo.setCalle(ctx.formParam("callePersona-"+i));
                direccionAcargo.setAltura(Integer.parseInt(ctx.formParam("alturaPersona-"+i)));


                Localidad localidadAcargo = new Localidad();
                localidadAcargo.setNombre(ctx.formParam("localidadPersona-"+i));
                Provincia provinciaAcargo = new Provincia();
                provinciaAcargo.setNombre(ctx.formParam("provinciaPersona-"+i));
                localidadAcargo.setProvincia(provinciaAcargo);

                direccionAcargo.setLocalidad(localidadAcargo);
                direccionAcargo.setCoordenada(new CoordenadaServiceImpl().obtenerCoordenada(direccionAcargo));
                personaAcargo.setDomicilio(direccionAcargo);

                Documento documentoAcargo = new Documento();
                documentoAcargo.setTipo(TipoDocumento.valueOf(ctx.formParam("tipoDocumentoPersona-"+i)));
                documentoAcargo.setNumero(ctx.formParam("numeroDocumentoPersona-"+i));

                personaAcargo.setDocumento(documentoAcargo);
                personasAcargo.add(personaAcargo);
                colaboracion.getPersonasVulnerables().add(personaVulnerable);
                i++;
            }

            personaVulnerable.setPersonasACargo(personasAcargo);
        } 
        
        //Guardo primero a la persona vulnerable principal
        ServiceLocator.instanceOf(PersonaVulnerableRepository.class).guardar(personaVulnerable);
        
        //Registro colaboracion
        colaboracion.setIdPersonaVulnerable(personaVulnerable.getId());
        ServiceLocator.instanceOf(RegistrarPersonaVulnerableRepository.class).guardar(colaboracion);

        //Despues recorro la lista de las persoans dependientes y las guardo con id_tutor referenciando al id de la persona vulnerable principal
        for(PersonaVulnerable persona : personasAcargo){
            persona.setIdTutor(personaVulnerable.getId());
            ServiceLocator.instanceOf(PersonaVulnerableRepository.class).guardar(persona);
            
            //Registro colaboracion
            RegistrarPersonasVulnerables colaboracionBeta = new RegistrarPersonasVulnerables();
            colaboracion.setIdPersonaVulnerable(personaVulnerable.getId());
            ServiceLocator.instanceOf(RegistrarPersonaVulnerableRepository.class).guardar(colaboracionBeta);
        }
        
        actualizarPuntaje(ctx, colaboracion);

        ctx.result(personasAcargo.toString());
        ctx.redirect("/");
    }

    public void actualizarPuntaje(Context ctx, RegistrarPersonasVulnerables colaboracion){
        long idColaborador = ctx.sessionAttribute("id");
        PersonaHumana colaborador = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(idColaborador);
        
        Puntaje puntaje=new Puntaje();
        puntaje.setPuntaje(colaboracion.calcularPuntaje()+colaborador.getPuntaje().getPuntaje());

        colaborador.setPuntaje(puntaje);
        ServiceLocator.instanceOf(PersonaHumanaRepository.class).actualizar(colaborador);
    }

    public void cargarColaboracionCSV(String[] columnas) {

    }
}