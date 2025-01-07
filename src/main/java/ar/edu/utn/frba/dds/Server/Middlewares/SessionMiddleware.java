package ar.edu.utn.frba.dds.Server.Middlewares;

import io.javalin.config.JavalinConfig;

import java.util.Arrays;
import java.util.List;

public class SessionMiddleware {

    public static void apply(JavalinConfig config) {

        List<String> allowedPaths = Arrays.asList("/", "/signup");

        config.accessManager(((handler, context, routeRoles) -> {

            if(context.sessionAttribute("id") == null && !allowedPaths.contains(context.path()))
            {
                context.redirect("/");
            }
            else
                handler.handle(context);
        }));
    }
}
