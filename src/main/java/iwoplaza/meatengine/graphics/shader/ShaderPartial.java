package iwoplaza.meatengine.graphics.shader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderPartial
{
    private int id;
    private PartialType type;

    public ShaderPartial(int id, PartialType type)
    {
        this.id = id;
        this.type = type;
    }

    public PartialType getType()
    {
        return type;
    }

    public int getId()
    {
        return id;
    }

    public static ShaderPartial createPartial(String shaderCode, PartialType type)
    {
        int shaderId = glCreateShader(type.getGlCode());
        if (shaderId == 0)
        {
            throw new IllegalStateException("Couldn't create shader partial of type: " + type);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
        {
            throw new IllegalArgumentException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        return new ShaderPartial(shaderId, type);
    }
}
