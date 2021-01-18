package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class FloatUniform extends Uniform
{
    public FloatUniform(Shader shader, String uniformKey, float defaultValue)
    {
        super(shader, uniformKey);
        this.set(defaultValue);
    }

    public void set(float value)
    {
        this.ownerShader.bind();
        glUniform1f(uniformLocation, value);
    }
}
