package ivesiris.neonshot.engine.graphics.shader;

import org.joml.Matrix4f;

public class UIShader extends Shader
{
    private final String PROJECTION_MATRIX = "uProjectionMatrix";
    private final String MODEL_VIEW_MATRIX = "uModelViewMatrix";
    private final String USE_TEXTURE = "uUseTexture";
    private final String TEXTURE_DIFFUSE = "uTextureDiffuse";
    private final String COLOR = "uColor";

    public UIShader()
    {
        super();
    }

    @Override
    protected void createUniforms() throws Exception
    {
        this.createUniform(PROJECTION_MATRIX);
        this.createUniform(MODEL_VIEW_MATRIX);
        this.createUniform(USE_TEXTURE);
        this.createUniform(TEXTURE_DIFFUSE);
        this.createUniform(COLOR);
    }

    public void setProjectionMatrix(Matrix4f matrix)
    {
        this.setUniform(PROJECTION_MATRIX, matrix);
    }

    public void setModelViewMatrix(Matrix4f matrix)
    {
        this.setUniform(MODEL_VIEW_MATRIX, matrix);
    }

    public void setUseTexture(boolean useTexture)
    {
        this.setUniform(USE_TEXTURE, useTexture);
    }

    public void setDiffuseTexture(int textureIndex)
    {
        this.setUniform(TEXTURE_DIFFUSE, textureIndex);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }
}
