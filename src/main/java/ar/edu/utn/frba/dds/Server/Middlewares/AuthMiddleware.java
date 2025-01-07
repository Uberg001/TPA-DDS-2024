package ar.edu.utn.frba.dds.Server.Middlewares;

import ar.edu.utn.frba.dds.Exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.Utils.Entities.TipoUsuario;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class AuthMiddleware {

    public static void apply(JavalinConfig config) {
        config.accessManager(((handler, context, routeRoles) -> {
            TipoUsuario userRole = getUserRoleType(context);

            if(routeRoles.isEmpty() || routeRoles.contains(userRole)) {
                handler.handle(context);
            }
            else {
                throw new AccessDeniedException();
            }
        }));
    }

    private static TipoUsuario getUserRoleType(Context context) {
        return context.sessionAttribute("tipoPersona") != null?
                TipoUsuario.valueOf(context.sessionAttribute("tipoPersona").toString().toUpperCase()) : null;
    }
}