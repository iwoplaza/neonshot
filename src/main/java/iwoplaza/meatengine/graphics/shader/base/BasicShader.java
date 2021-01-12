package iwoplaza.meatengine.graphics.shader.base;

import iwoplaza.meatengine.graphics.shader.Shader;
import org.joml.Matrix4f;

import java.io.FileNotFoundException;

public abstract class BasicShader extends Shader
{
    protected final String PROJECTION_MATRIX = "uProjectionMatrix";
    protected final String MODEL_VIEW_MATRIX = "uModelViewMatrix";

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
        this.createUniform(PROJECTION_MATRIX);
        this.createUniform(MODEL_VIEW_MATRIX);
    }

    public void setProjectionMatrix(Matrix4f matrix)
    {
        this.setUniform(PROJECTION_MATRIX, matrix);
    }

    public void setModelViewMatrix(Matrix4f matrix)
    {
        this.setUniform(MODEL_VIEW_MATRIX, matrix);
    }
}
