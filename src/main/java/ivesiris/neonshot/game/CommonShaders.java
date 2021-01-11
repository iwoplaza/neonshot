package ivesiris.neonshot.game;

import ivesiris.neonshot.engine.graphics.shader.FlatShader;
import ivesiris.neonshot.engine.graphics.shader.Shader;
import ivesiris.neonshot.engine.graphics.shader.TextShader;
import ivesiris.neonshot.engine.graphics.shader.UIShader;
import ivesiris.neonshot.engine.loader.ResourceLoader;

public class CommonShaders
{
    public static FlatShader flatShader;
    public static UIShader uiShader;
    public static TextShader textShader;

    public static void loadShaders() throws Exception
    {
        flatShader = createShader(new FlatShader(), "flat", "solid_color");
        uiShader = createShader(new UIShader(), "ui", "textured");
        textShader = createShader(new TextShader(), "ui", "text");
    }

    public static <T extends Shader> T createShader(T shader, String vertex, String fragment) throws Exception
    {
        shader.createProgram();
        shader.createVertexShader(ResourceLoader.loadStringResource("/shaders/" + vertex + ".vert"));
        shader.createFragmentShader(ResourceLoader.loadStringResource("/shaders/" + fragment + ".frag"));
        shader.link();
        return shader;
    }
}
