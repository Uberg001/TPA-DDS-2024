package ar.edu.utn.frba.dds.Utils;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JavalinRenderer {
    private final Map<String, FileRenderer> renderers = new HashMap<>();

    public JavalinRenderer register(String extension, FileRenderer renderer) {
        renderers.put(extension, renderer);
        return this;
    }

    public String render(String filePath, Map<String, Object> model, Context context) {
        String extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        FileRenderer renderer = renderers.get(extension);
        if (renderer == null) {
            throw new RuntimeException("No renderer registered for extension: " + extension);
        }
        return renderer.render(filePath, model, context);
    }
}