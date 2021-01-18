package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.helper.MeshHelper;
import iwoplaza.meatengine.util.Color;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.shader.ShaderHelper;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.util.IColorc;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class LineRenderer
{

    private static FlatShader shader;
    private static Drawable mesh;

    private static float lineWidth = 1.0F;

    public static void init() throws Exception
    {
        shader = new FlatShader();
        shader.load();
        mesh = new Drawable(MeshHelper.createFlatRectangle(1, 1), shader);
    }

    public static void setColor(IColorc color)
    {
        setColor(color.r(), color.g(), color.b(), color.a());
    }

    public static void setColor(float r, float g, float b, float a)
    {
        shader.getColor().set(r, g, b, a);
    }

    public static void setWidth(float width)
    {
        lineWidth = width;
    }

    public static void draw(float x0, float y0, float x1, float y1)
    {
        GlStack.push();

        float dx = x1-x0;
        float dy = y1-y0;
        float angle = (float) (-Math.atan2(dx, dy));
        float length = (float) Math.sqrt(dx*dx + dy*dy);

        GlStack.translate(x0, y0, 0);
        GlStack.rotate(angle, 0, 0, 1);
        GlStack.scale(lineWidth, length, 1);

        shader.bind();
        mesh.draw();
        shader.unbind();

        GlStack.pop();
    }

    public static void draw(Vector2f p1, Vector2f p2)
    {
        draw(p1.x, p1.y, p2.x, p2.y);
    }

    public static void drawPoint(float x, float y)
    {
        GlStack.push();

        GlStack.translate(x, y, 0);
        GlStack.scale(lineWidth);

        shader.bind();
        mesh.draw();
        shader.unbind();

        GlStack.pop();
    }

}
