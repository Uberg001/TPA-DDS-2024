package ar.edu.utn.frba.dds.Server.AppHandlers;
import io.javalin.Javalin;

public interface IHandler {
    void setHandle(Javalin app);
}