package ivesiris.neonshot.engine.graphics.shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader
{

    private final Map<String, Integer> uniforms;
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public Shader()
    {
        uniforms = new HashMap<>();
    }

    public void createProgram() throws Exception
    {
        programId = glCreateProgram();
        if (programId == 0)
            throw new Exception("Could not create Shader");
    }

    public void createVertexShader(String shaderCode) throws Exception
    {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception
    {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws Exception
    {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
        {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0)
        {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() throws Exception
    {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
        {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0)
        {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0)
        {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
        {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        this.createUniforms();
    }

    public void bind()
    {
        glUseProgram(programId);
    }

    public void unbind()
    {
        glUseProgram(0);
    }

    protected abstract void createUniforms() throws Exception;

    protected void createUniform(String uniformName) throws Exception
    {
        int location = glGetUniformLocation(programId, uniformName);

        if (location < 0)
        {
            throw new Exception("Could not find uniform: " + uniformName);
        }

        uniforms.put(uniformName, location);
    }

    protected void createUniform(String uniformName, int arrayElements) throws Exception
    {
        for (int i = 0; i < arrayElements; ++i)
        {
            createUniform(uniformName + "[" + i + "]");
        }
    }

    protected void setUniform(String uniformName, float value)
    {
        glUniform1f(uniforms.get(uniformName), value);
    }

    protected void setUniform(String uniformName, int value)
    {
        glUniform1i(uniforms.get(uniformName), value);
    }

    protected void setUniform(String uniformName, boolean value)
    {
        glUniform1i(uniforms.get(uniformName), value ? 1 : 0);
    }

    protected void setUniform(String uniformName, float x, float y, float z, float w)
    {
        glUniform4f(uniforms.get(uniformName), x, y, z, w);
    }

    protected void setUniform(String uniformName, Matrix4f value)
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(uniforms.get(uniformName), false, buffer);
        }
    }

    protected void setUniform(String uniformName, Matrix4f[] matrices)
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer fb = stack.mallocFloat(16 * matrices.length);
            for (int i = 0; i < matrices.length; i++)
            {
                matrices[i].get(16 * i, fb);
            }
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void cleanUp()
    {
        unbind();
        if (programId != 0)
        {
            glDeleteProgram(programId);
        }
    }

    public static <T extends Shader> void operateOnShader(T shader, IShaderOperationFunction<T> function)
    {
        shader.bind();
        function.operate(shader);
        shader.unbind();
    }

    @FunctionalInterface
    public interface IShaderOperationFunction<T extends Shader>
    {

        void operate(T shader);

    }

}
