package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1i;

public class IntUniform extends Uniform
{
    public IntUniform(Shader shader, String uniformKey, int defaultValue)
    {
        super(shader, uniformKey);
        this.set(defaultValue);
    }

    public void set(int value)
    {
        this.ownerShader.bind();
        glUniform1i(uniformLocation, value);
    }
}
