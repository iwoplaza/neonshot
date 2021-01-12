package iwoplaza.meatengine.graphics.shader;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.loader.ResourceLoader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader implements IDisposable
{
    private final Map<String, Integer> uniforms;

    private AssetLocation vertexShaderLocation;
    private AssetLocation fragmentShaderLocation;
    private ShaderProgram program;

    public Shader(String origin, String shaderName) throws FileNotFoundException
    {
        this.vertexShaderLocation = AssetLocation.asResource(origin, String.format("shaders/%s.vert", shaderName));
        this.fragmentShaderLocation = AssetLocation.asResource(origin, String.format("shaders/%s.frag", shaderName));
        this.uniforms = new HashMap<>();
    }

    public Shader(String shaderName) throws FileNotFoundException
    {
        this.vertexShaderLocation = AssetLocation.asResource(String.format("shaders/%s.vert", shaderName));
        this.fragmentShaderLocation = AssetLocation.asResource(String.format("shaders/%s.frag", shaderName));
        this.uniforms = new HashMap<>();
    }

    @Override
    public String toString()
    {
        return this.getClass().getName();
    }

    public void load()
    {
        String vertexShaderCode = ResourceLoader.loadStringResource(this.vertexShaderLocation.getResourcePath());
        String fragmentShaderCode = ResourceLoader.loadStringResource(this.fragmentShaderLocation.getResourcePath());

        this.program = new ShaderProgram();
        this.program.link(
                ShaderPartial.createPartial(vertexShaderCode, PartialType.VERTEX),
                ShaderPartial.createPartial(fragmentShaderCode, PartialType.FRAGMENT)
        );

        this.createUniforms();
    }

    public void bind()
    {
        if (program == null)
            throw new IllegalStateException("Tried to bind a shader before loading it in.");

        this.program.use();
    }

    public void unbind()
    {
        glUseProgram(0);
    }

    @Override
    public void dispose()
    {
        unbind();
        this.program.dispose();
    }

    protected abstract void createUniforms();

    protected void createUniform(String uniformName)
    {
        int location = program.getUniformLocation(uniformName);

        if (location < 0)
        {
            throw new IllegalArgumentException(String.format("Uniform '%s' isn't defined in the '%s' shader", uniformName, this.toString()));
        }

        uniforms.put(uniformName, location);
    }

    protected void createUniform(String uniformName, int arrayElements)
    {
        for (int i = 0; i < arrayElements; ++i)
        {
            createUniform(uniformName + "[" + i + "]");
        }
    }

    // Uniform setters

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
}
