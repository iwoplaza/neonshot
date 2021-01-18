package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.meatengine.graphics.shader.uniform.BooleanUniform;
import iwoplaza.meatengine.graphics.shader.uniform.ColorUniform;
import iwoplaza.meatengine.graphics.shader.uniform.IntUniform;
import iwoplaza.meatengine.graphics.shader.uniform.Uniform;
import iwoplaza.meatengine.util.Color;

import java.io.FileNotFoundException;

public class TileShader extends BasicShader
{
    private ColorUniform color;
    private BooleanUniform useTexture;
    private IntUniform diffuseTexture;

    public TileShader() throws FileNotFoundException
    {
        super("tile");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();

        this.color = new ColorUniform(this, "uColor", new Color(1, 1, 1, 1));
        this.useTexture = new BooleanUniform(this, "uUseTexture", true);
        this.diffuseTexture = new IntUniform(this, "uTextureDiffuse", 0);
    }

    public ColorUniform getColor()
    {
        return color;
    }

    public BooleanUniform getUseTexture()
    {
        return useTexture;
    }

    public IntUniform getDiffuseTexture()
    {
        return diffuseTexture;
    }
}
