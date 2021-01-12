package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;

import java.io.FileNotFoundException;

public class UIShader extends BasicShader
{
    private final String USE_TEXTURE = "uUseTexture";
    private final String TEXTURE_DIFFUSE = "uTextureDiffuse";
    private final String COLOR = "uColor";

    public UIShader() throws FileNotFoundException
    {
        super("ui");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.createUniform(USE_TEXTURE);
        this.createUniform(TEXTURE_DIFFUSE);
        this.createUniform(COLOR);
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
