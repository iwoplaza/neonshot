package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.uniform.ColorUniform;
import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.meatengine.util.Color;

import java.io.FileNotFoundException;

public class FlatShader extends BasicShader
{
    private ColorUniform color;

    public FlatShader() throws FileNotFoundException
    {
        super("flat");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.color = new ColorUniform(this, "uColor", new Color(1, 1, 1, 1));
    }

    public ColorUniform getColor()
    {
        return color;
    }
}
