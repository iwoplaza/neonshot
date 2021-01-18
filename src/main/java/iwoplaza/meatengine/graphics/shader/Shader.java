package iwoplaza.meatengine.graphics.shader;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.loader.ResourceLoader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader implements IDisposable
{
    private AssetLocation vertexShaderLocation;
    private AssetLocation fragmentShaderLocation;
    private ShaderProgram program;

    public Shader(String origin, String shaderName) throws FileNotFoundException
    {
        this.vertexShaderLocation = AssetLocation.asResource(origin, String.format("shaders/%s.vert", shaderName));
        this.fragmentShaderLocation = AssetLocation.asResource(origin, String.format("shaders/%s.frag", shaderName));
    }

    public Shader(String shaderName) throws FileNotFoundException
    {
        this.vertexShaderLocation = AssetLocation.asResource(String.format("shaders/%s.vert", shaderName));
        this.fragmentShaderLocation = AssetLocation.asResource(String.format("shaders/%s.frag", shaderName));
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

    public ShaderProgram getProgram()
    {
        return program;
    }
}
