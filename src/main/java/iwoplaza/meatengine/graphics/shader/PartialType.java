package iwoplaza.meatengine.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public enum PartialType
{
    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER);

    private int glCode;

    PartialType(int glCode)
    {
        this.glCode = glCode;
    }

    public int getGlCode()
    {
        return glCode;
    }
}
