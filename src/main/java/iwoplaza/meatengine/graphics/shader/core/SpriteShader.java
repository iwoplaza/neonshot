package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.meatengine.graphics.shader.uniform.ColorUniform;
import iwoplaza.meatengine.graphics.shader.uniform.IntUniform;
import iwoplaza.meatengine.graphics.shader.uniform.Uniform;
import iwoplaza.meatengine.graphics.shader.uniform.Vector2fUniform;
import iwoplaza.meatengine.util.Color;

import java.io.FileNotFoundException;

public class SpriteShader extends BasicShader
{
    private IntUniform diffuseTexture;
    private ColorUniform color;
    private Vector2fUniform frameOffset;
    private Vector2fUniform frameSize;
    private ColorUniform overlayColor;

    public SpriteShader() throws FileNotFoundException
    {
        super("sprite");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.diffuseTexture = new IntUniform(this, "uTextureDiffuse", 0);
        this.color = new ColorUniform(this, "uColor", new Color(1, 1, 1, 1));
        this.frameOffset = new Vector2fUniform(this, "uFrameOffset", 0, 0);
        this.frameSize = new Vector2fUniform(this, "uFrameSize", 1, 1);
        this.overlayColor = new ColorUniform(this, "uOverlayColor", new Color(1, 1, 1, 0));
    }

    public IntUniform getDiffuseTexture()
    {
        return diffuseTexture;
    }

    public ColorUniform getColor()
    {
        return color;
    }

    public Vector2fUniform getFrameOffset()
    {
        return frameOffset;
    }

    public Vector2fUniform getFrameSize()
    {
        return frameSize;
    }

    public ColorUniform getOverlayColor()
    {
        return overlayColor;
    }
}
