package iwoplaza.neonshot.graphics.shader;

import iwoplaza.meatengine.graphics.shader.uniform.ColorUniform;
import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.meatengine.graphics.shader.uniform.FloatUniform;
import iwoplaza.meatengine.util.Color;
import iwoplaza.neonshot.Statics;

import java.io.FileNotFoundException;

public class ProgressBarShader extends BasicShader
{
    private ColorUniform color;
    private ColorUniform highlightColor;
    private FloatUniform highlight;

    public ProgressBarShader() throws FileNotFoundException
    {
        super(Statics.RES_ORIGIN, "progressbar");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.color = new ColorUniform(this, "uColor", new Color(1, 1, 1, 1));
        this.highlightColor = new ColorUniform(this, "uHighlightColor", new Color(1, 1, 1, 1));
        this.highlight = new FloatUniform(this, "uHighlight", 0);
    }

    public ColorUniform getColor()
    {
        return color;
    }

    public ColorUniform getHighlightColor()
    {
        return highlightColor;
    }

    public FloatUniform getHighlight()
    {
        return highlight;
    }
}
