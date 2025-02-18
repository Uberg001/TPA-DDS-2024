package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.DireccionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.TecnicoDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.TecnicoDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class TecnicoController{

    private TecnicoServiceImpl tecnicoService;

    private UsuarioController usuarioController;

    public TecnicoController(TecnicoServiceImpl tecnicoService, UsuarioController usuarioController){
        this.tecnicoService = tecnicoService;
        this.usuarioController = usuarioController;
    }
    public void signUp(Context ctx){
        TecnicoDTOSalida tecnicoDTOSalida = tecnicoService.createTecnico(getTecnicoDTOEntrada(ctx));
        ctx.status(HttpStatus.CREATED);
        ctx.sessionAttribute("tecnico", tecnicoDTOSalida.getIdUsuario());
        usuarioController.logInId(ctx, tecnicoDTOSalida.getIdUsuario(),"tecnico");
    }

    public List<TecnicoDTOSalida> getAllTecnicos(){
        return tecnicoService.getAllTecnicos();
    }

    private TecnicoDTOEntrada getTecnicoDTOEntrada(Context ctx){
        TecnicoDTOEntrada tecnicoDTOEntrada = new TecnicoDTOEntrada();
        tecnicoDTOEntrada.setNombre(ctx.formParam("nombre"));
        tecnicoDTOEntrada.setApellido(ctx.formParam("apellido"));
        tecnicoDTOEntrada.setTipoDocumento(ctx.formParam("tipoDoc"));
        tecnicoDTOEntrada.setNumeroDocumento(ctx.formParam("numeroDoc"));
        tecnicoDTOEntrada.setCuil(ctx.formParam("cuit"));
        tecnicoDTOEntrada.setUsuario(new UsuarioDTOEntrada(ctx.formParam("usuario"), ctx.formParam("password"),false));
        DireccionDTOEntrada direccionDTOEntrada = new DireccionDTOEntrada();
        direccionDTOEntrada.setCalle(ctx.formParam("calle"));
        direccionDTOEntrada.setAltura(Integer.valueOf(ctx.formParam("altura")));
        direccionDTOEntrada.setLocalidad(ctx.formParam("localidad"));
        direccionDTOEntrada.setProvincia(ctx.formParam("provincia"));
        tecnicoDTOEntrada.setDireccion(direccionDTOEntrada);
        return tecnicoDTOEntrada;
    }
}
