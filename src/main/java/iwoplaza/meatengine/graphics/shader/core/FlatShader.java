package iwoplaza.meatengine.graphics.shader.core;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;

import java.io.FileNotFoundException;

public class FlatShader extends BasicShader
{
    private final String COLOR = "uColor";

    public FlatShader() throws FileNotFoundException
    {
        super("flat");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.createUniform(COLOR);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }
}
