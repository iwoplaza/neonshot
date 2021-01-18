package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1i;

public class BooleanUniform extends Uniform
{
    public BooleanUniform(Shader shader, String uniformKey, boolean defaultValue)
    {
        super(shader, uniformKey);
        this.set(defaultValue);
    }

    public void set(boolean value)
    {
        this.ownerShader.bind();
        glUniform1i(uniformLocation, value ? 1 : 0);
    }
}
