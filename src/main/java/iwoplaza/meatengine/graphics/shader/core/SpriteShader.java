package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.ShaderHelper;
import iwoplaza.meatengine.graphics.shader.base.BasicShader;

import java.io.FileNotFoundException;

public class SpriteShader extends BasicShader
{
    private final String TEXTURE_DIFFUSE = "uTextureDiffuse";
    private final String COLOR = "uColor";
    private final String FRAME_OFFSET = "uFrameOffset";
    private final String FRAME_SIZE = "uFrameSize";

    public SpriteShader() throws FileNotFoundException
    {
        super("sprite");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.createUniform(TEXTURE_DIFFUSE);
        this.createUniform(COLOR);
        this.createUniform(FRAME_SIZE);
        this.createUniform(FRAME_OFFSET);

        ShaderHelper.operateOnShader(this, s -> {
            s.setUniform(TEXTURE_DIFFUSE, 0);
            s.setUniform(COLOR, 1, 1, 1, 1);
            s.setUniform(FRAME_OFFSET, 0, 0);
            s.setUniform(FRAME_SIZE, 1, 1);
        });
    }

    public void setDiffuseTexture(int textureIndex)
    {
        this.setUniform(TEXTURE_DIFFUSE, textureIndex);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }

    public void setFrameOffset(float frameX, float frameY)
    {
        this.setUniform(FRAME_OFFSET, frameX, frameY);
    }

    public void setFrameSize(float frameWidth, float frameHeight)
    {
        this.setUniform(FRAME_SIZE, frameWidth, frameHeight);
    }
}
