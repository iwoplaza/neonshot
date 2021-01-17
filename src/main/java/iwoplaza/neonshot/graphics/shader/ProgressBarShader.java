package iwoplaza.neonshot.graphics.shader;

import iwoplaza.meatengine.graphics.shader.base.BasicShader;
import iwoplaza.neonshot.Statics;

import java.io.FileNotFoundException;

public class ProgressBarShader extends BasicShader
{
    private final String COLOR = "uColor";
    private final String HIGHLIGHT_COLOR = "uHighlightColor";
    private final String HIGHLIGHT = "uHighlight";

    public ProgressBarShader() throws FileNotFoundException
    {
        super(Statics.RES_ORIGIN, "progressbar");
    }

    @Override
    protected void createUniforms()
    {
        super.createUniforms();
        this.createUniform(COLOR);
        this.createUniform(HIGHLIGHT_COLOR);
        this.createUniform(HIGHLIGHT);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }

    public void setHighlightColor(float r, float g, float b, float a)
    {
        this.setUniform(HIGHLIGHT_COLOR, r, g, b, a);
    }

    public void setHighlight(float value)
    {
        this.setUniform(HIGHLIGHT, value);
    }
}
