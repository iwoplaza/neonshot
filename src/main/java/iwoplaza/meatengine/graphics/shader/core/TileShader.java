package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.Shader;
import iwoplaza.neonshot.Statics;
import org.joml.Matrix4f;

import java.io.FileNotFoundException;

public class TileShader extends Shader
{

    private final String PROJECTION_MATRIX = "uProjectionMatrix";
    private final String MODEL_VIEW_MATRIX = "uModelViewMatrix";
    private final String COLOR = "uColor";
    private final String USE_TEXTURE = "uUseTexture";
    private final String TEXTURE_DIFFUSE = "uTextureDiffuse";

    public TileShader() throws FileNotFoundException
    {
        super("tile");
    }

    @Override
    protected void createUniforms()
    {
        this.createUniform(PROJECTION_MATRIX);
        this.createUniform(MODEL_VIEW_MATRIX);
        this.createUniform(COLOR);
        this.createUniform(USE_TEXTURE);
        this.createUniform(TEXTURE_DIFFUSE);
    }

    public void setProjectionMatrix(Matrix4f matrix)
    {
        this.setUniform(PROJECTION_MATRIX, matrix);
    }

    public void setModelViewMatrix(Matrix4f matrix)
    {
        this.setUniform(MODEL_VIEW_MATRIX, matrix);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }

    public void setUseTexture(boolean useTexture)
    {
        this.setUniform(USE_TEXTURE, useTexture);
    }

    public void setDiffuseTexture(int textureIndex)
    {
        this.setUniform(TEXTURE_DIFFUSE, textureIndex);
    }

}
