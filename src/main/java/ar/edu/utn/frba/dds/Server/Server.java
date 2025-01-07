package ar.edu.utn.frba.dds.Server;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Controllers.ReporteController;
import ar.edu.utn.frba.dds.Server.AppHandlers.AppHandlers;
import ar.edu.utn.frba.dds.Server.Middlewares.AuthMiddleware;
import ar.edu.utn.frba.dds.Utils.JavalinRenderer;
import ar.edu.utn.frba.dds.Utils.PrettyProperties;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("La App no inicio correctamente");
        return app;
    }

    public static void init() {
//        ReporteController reporteController = ServiceLocator.instanceOf(ReporteController.class);
        if (app == null) {
            int port = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("server_port"));
            app = Javalin.create(config()).events(event -> {
                event.serverStarting(() -> {
                    //Eventos cuando inicia el servidor
                });
//                event.serverStarted(() -> {
//                    //Eventos cuando inicio el servidor
//                    reporteController.iniciarGeneracionReportes();
//                    reporteController.iniciarActualizacionReportes();
//                });
                event.serverStopping(() -> {
                    //Eventos cuando se detiene el servidor
                });
                event.serverStopped(() -> {
                    //Eventos cuando se detuvo el servidor
                });
            }).start(port);

            AppHandlers.applyHandlers(app);
            Router.init(app);

            // Error handlers
            app.error(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), ctx -> {
                ctx.render("errors/500.html");
            });

            app.error(HttpStatus.BAD_REQUEST.getCode(), ctx -> {
                ctx.render("errors/400.html");
            });

            app.error(HttpStatus.NOT_FOUND.getCode(), ctx -> {
                ctx.render("errors/404.hbs");
            });
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
            AuthMiddleware.apply(config);
            //SessionMiddleware.apply(config);
            config.fileRenderer((filePath, model, context) -> {
                Handlebars handlebars = new Handlebars();

                handlebars.registerHelper("eq", (val1, val2) -> {
                    // Extraer el valor de val2 si es un objeto Options
                    String value1 = val1 != null ? val1.toString().trim() : "";
                    String value2 = val2 instanceof Options
                            ? val2.toString().trim() : val2 != null ? val2.toString().trim() : "";

                    // Si val2 es una instancia de Options, accedemos al primer parámetro
                    if (val2 instanceof Options) {
                        Options options = (Options) val2;
                        value2 = options.params[0].toString().trim();  // Extraemos el valor de la cadena
                    }

                    return value1.equals(value2);  // Comparamos los valores
                });



                Template template = null;

                try {
                    // Compilación de la plantilla
                    template = handlebars.compile("templates/" + filePath.replace(".hbs", ""));

                    return template.apply(model);
                } catch (IOException e) {
                    // Manejo de error si la plantilla no se encuentra
                    e.printStackTrace();
                    context.status(HttpStatus.NOT_FOUND);
                    context.render("errors/404.hbs");
                    return "";
                } catch (Exception e) {
                    // Manejo de error si ocurre un error inesperado
                    e.printStackTrace();
                    context.status(HttpStatus.INTERNAL_SERVER_ERROR);
                    context.render("errors/500.html");
                    return "";
                }
            });
        };
    }

    public static void configureEntityManagerProperties() {
        // Set environment variables if not already set
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();

        String[] keys = new String[]{
                "hibernate.archive.autodetection",
                "hibernate.connection.driver_class",
                "hibernate.connection.url",
                "hibernate.connection.username",
                "hibernate.connection.password",
                "hibernate.dialect",
                "hibernate.show_sql",
                "hibernate.format_sql",
                "use_sql_comments",
                "hibernate.hbm2ddl.auto",
        };

        for (String key : keys) {
            String value = env.get(key);
            if (value != null) {
                System.out.println(key + ": " + value);
                configOverrides.put(key, value);
            } else {
                System.out.println("Environment variable " + key + " is not set.");
            }
        }

        Consumer<PerThreadEntityManagerProperties> propertiesConsumer = perThreadEntityManagerProperties -> {
            perThreadEntityManagerProperties.putAll(configOverrides);
        };

        WithSimplePersistenceUnit.configure(propertiesConsumer);
    }

}
