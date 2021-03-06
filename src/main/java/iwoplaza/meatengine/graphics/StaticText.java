package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.util.Color;
import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.graphics.font.Font;
import iwoplaza.meatengine.graphics.mesh.TextMesh;
import iwoplaza.meatengine.graphics.shader.core.TextShader;
import iwoplaza.neonshot.CommonShaders;
import org.joml.Vector2f;

public class StaticText implements IDisposable
{

    public final TextShader shader;
    public final Font font;
    private String renderedText;
    private Drawable<TextShader> mesh;
    public final TextRenderOptions options;
    private int textWidth;

    private Color color;
    private Vector2f position;

    public StaticText(TextShader shader, Font font, String text)
    {
        this.shader = shader;
        this.font = font;
        this.renderedText = text;
        this.options = new TextRenderOptions();
        this.updateMesh();

        this.color = new Color(1, 1, 1, 1);
        this.position = new Vector2f();
    }

    public void setText(String text)
    {
        this.renderedText = text;
        this.updateMesh();
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.color.set(r, g, b, a);
    }

    public void setPosition(float x, float y)
    {
        this.position.set(x, y);
    }

    public void updateMesh()
    {
        if (this.mesh != null)
            this.mesh.dispose();

        this.mesh = new Drawable<>(TextMesh.build(font, renderedText), this.shader);
        this.textWidth = this.font.getTextWidth(renderedText, options);
    }

    public void render()
    {
        GlStack.push();

        GlStack.translate(this.position.x, this.position.y, 0);

        this.shader.bind();
        this.shader.getColor().set(color);

        this.font.texture.bind();
        this.mesh.draw();

        this.shader.unbind();

        GlStack.pop();
    }

    public int getTextWidth()
    {
        return textWidth;
    }

    @Override
    public void dispose()
    {
        if (this.mesh != null)
            this.mesh.dispose();
    }

}
