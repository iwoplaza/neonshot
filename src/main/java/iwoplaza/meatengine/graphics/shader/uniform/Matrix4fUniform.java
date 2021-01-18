package iwoplaza.meatengine.graphics.shader.uniform;

import iwoplaza.meatengine.graphics.shader.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Matrix4fUniform extends Uniform
{
    public Matrix4fUniform(Shader shader, String uniformKey)
    {
        super(shader, uniformKey);
    }

    public void set(Matrix4f value)
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            this.ownerShader.bind();
            glUniformMatrix4fv(uniformLocation, false, buffer);
        }
    }
}
