package ar.edu.utn.frba.dds.Server.AppHandlers;
import ar.edu.utn.frba.dds.Exceptions.AccessDeniedException;
import io.javalin.Javalin;

public class AccessDeniedHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(AccessDeniedException.class, (e, context) -> {
            context.status(401);
            context.render("");
        });
    }
}
