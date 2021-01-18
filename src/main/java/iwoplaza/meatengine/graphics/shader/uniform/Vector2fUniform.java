package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;
import org.joml.Vector2fc;

import static org.lwjgl.opengl.GL20.glUniform2f;

public class Vector2fUniform extends Uniform
{
    public Vector2fUniform(Shader shader, String uniformKey, int defaultX, int defaultY)
    {
        super(shader, uniformKey);
        this.set(defaultX, defaultY);
    }

    public void set(float x, float y)
    {
        this.ownerShader.bind();
        glUniform2f(uniformLocation, x, y);
    }

    public void set(Vector2fc vec)
    {
        this.set(vec.x(), vec.y());
    }
}
