package ivesiris.neonshot.engine.graphics;

import ivesiris.neonshot.engine.graphics.mesh.TextMesh;
import ivesiris.neonshot.engine.graphics.shader.TextShader;
import org.joml.Vector2f;

public class StaticText
{

    public final TextShader shader;
    public final Font font;
    private String renderedText;
    private TextMesh mesh;
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
            this.mesh.cleanUp();

        this.mesh = TextMesh.build(font, renderedText);
        this.textWidth = this.font.getTextWidth(renderedText, options);
    }

    public void render()
    {
        GlStack.push();

        GlStack.translate(this.position.x, this.position.y, 0);

        this.shader.bind();
        this.shader.setColor(color.getR(), color.getG(), color.getB(), color.getA());
        this.shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        this.shader.setModelViewMatrix(GlStack.MAIN.top());
        this.shader.setDiffuseTexture(0);

        this.font.texture.bind();
        this.mesh.render();

        this.shader.unbind();

        GlStack.pop();
    }

    public int getTextWidth()
    {
        return textWidth;
    }

    public void cleanUp()
    {
        if (this.mesh != null)
            this.mesh.cleanUp();
    }

}
