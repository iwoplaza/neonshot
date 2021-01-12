package iwoplaza.neonshot;

import iwoplaza.meatengine.graphics.shader.Shader;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.graphics.shader.core.TextShader;
import iwoplaza.meatengine.graphics.shader.core.TileShader;
import iwoplaza.meatengine.graphics.shader.core.UIShader;

public class CommonShaders
{
    public static FlatShader flatShader;
    public static UIShader uiShader;
    public static TextShader textShader;
    public static TileShader tileShader;

    public static void loadShaders() throws Exception
    {
        flatShader = loadShader(new FlatShader());
        uiShader = loadShader(new UIShader());
        textShader = loadShader(new TextShader());
        tileShader = loadShader(new TileShader());
    }

    public static <T extends Shader> T loadShader(T shader)
    {
        shader.load();
        return shader;
    }
}
