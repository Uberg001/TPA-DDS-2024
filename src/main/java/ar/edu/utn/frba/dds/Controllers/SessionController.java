package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Services.SessionServiceImpl;
import lombok.Data;
import lombok.Getter;


public class SessionController {

    @Getter
    private SessionDTO session;

    private SessionServiceImpl sessionService;

    public SessionController(SessionServiceImpl sessionService) {
        this.sessionService = sessionService;
    }

    public void setSession(Long idUsuario) {
        this.session = sessionService.setSession(idUsuario);
    }

}
