package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.uniform.ColorUniform;
import iwoplaza.meatengine.graphics.shader.uniform.IntUniform;
import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.meatengine.util.Color;

import java.io.FileNotFoundException;

public class TextShader extends BasicShader
{
    private IntUniform diffuseTexture;
    private ColorUniform color;

    public TextShader() throws FileNotFoundException
    {
        super("text");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();

        this.diffuseTexture = new IntUniform(this, "uTextureDiffuse", 0);
        this.color = new ColorUniform(this, "uColor", new Color(1, 1, 1, 1));
    }

    public IntUniform getDiffuseTexture()
    {
        return diffuseTexture;
    }

    public ColorUniform getColor()
    {
        return color;
    }
}
