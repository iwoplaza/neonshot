package iwoplaza.meatengine.graphics.shader.base;

import iwoplaza.meatengine.graphics.shader.uniform.Matrix4fUniform;
import iwoplaza.meatengine.graphics.shader.Shader;

import java.io.FileNotFoundException;

public abstract class BasicShader extends Shader
{
    protected Matrix4fUniform modelViewMatrix;
    protected Matrix4fUniform projectionMatrix;

    public BasicShader(String origin, String shaderName) throws FileNotFoundException
    {
        super(origin, shaderName);
    }

    public BasicShader(String shaderName) throws FileNotFoundException
    {
        super(shaderName);
    }

    @Override
    protected void createUniforms()
    {
        this.modelViewMatrix = new Matrix4fUniform(this, "uModelViewMatrix");
        this.projectionMatrix = new Matrix4fUniform(this, "uProjectionMatrix");
    }

    public Matrix4fUniform getModelViewMatrix()
    {
        return modelViewMatrix;
    }

    public Matrix4fUniform getProjectionMatrix()
    {
        return projectionMatrix;
    }
}
