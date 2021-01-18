package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;
import iwoplaza.meatengine.util.IColorc;

import static org.lwjgl.opengl.GL20.glUniform4f;

public class ColorUniform extends Uniform
{

    public ColorUniform(Shader shader, String uniformKey, IColorc defaultValue)
    {
        super(shader, uniformKey);
        this.set(defaultValue);
    }

    public void set(float r, float g, float b, float a)
    {
        this.ownerShader.bind();
        glUniform4f(this.uniformLocation, r, g, b, a);
    }

    public void set(IColorc color)
    {
        this.set(color.r(), color.g(), color.b(), color.a());
    }

}
