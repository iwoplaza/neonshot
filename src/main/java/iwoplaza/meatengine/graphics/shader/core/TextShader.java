package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;

import java.io.FileNotFoundException;

public class TextShader extends BasicShader
{
    private final String TEXTURE_DIFFUSE = "uTextureDiffuse";
    private final String COLOR = "uColor";

    public TextShader() throws FileNotFoundException
    {
        super("text");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.createUniform(TEXTURE_DIFFUSE);
        this.createUniform(COLOR);
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
