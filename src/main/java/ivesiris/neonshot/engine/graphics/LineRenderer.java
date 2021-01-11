package ivesiris.neonshot.engine.graphics;

import ivesiris.neonshot.engine.graphics.mesh.FlatMesh;
import ivesiris.neonshot.engine.graphics.shader.FlatShader;
import ivesiris.neonshot.engine.graphics.shader.Shader;
import ivesiris.neonshot.engine.loader.ResourceLoader;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class LineRenderer
{

    private static FlatShader shader = new FlatShader();
    private static FlatMesh mesh;

    private static Matrix4f transformMatrix = new Matrix4f();
    private static float lineWidth = 1.0F;

    public static void init() throws Exception
    {
        int[] indices = new int[] {
                0, 2, 1,
                0, 3, 2
        };

        float[] positions = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        mesh = new FlatMesh(indices, positions);

        shader.createProgram();
        shader.createVertexShader(ResourceLoader.loadStringResource("/shaders/flat.vert"));
        shader.createFragmentShader(ResourceLoader.loadStringResource("/shaders/solid_color.frag"));
        shader.link();

        Shader.operateOnShader(shader, s -> {
            s.setProjectionMatrix(new Matrix4f());
            s.setModelViewMatrix(transformMatrix);
            s.setColor(1, 1, 1, 1);
        });
    }

    public static void setColor(Color color)
    {
        setColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    public static void setColor(float r, float g, float b, float a)
    {
        Shader.operateOnShader(shader, s -> {
            s.setColor(r, g, b, a);
        });
    }

    public static void setProjection(Matrix4f matrix)
    {
        Shader.operateOnShader(shader, s -> {
            s.setProjectionMatrix(matrix);
        });
    }

    public static void setWidth(float width)
    {
        lineWidth = width;
    }

    public static void draw(float x0, float y0, float x1, float y1)
    {
        float dx = x1-x0;
        float dy = y1-y0;
        float angle = (float) (-Math.atan2(dx, dy));
        float length = (float) Math.sqrt(dx*dx + dy*dy);

        transformMatrix.identity().translate(x0, y0, 0).rotate(angle, 0, 0, 1).scale(lineWidth, length, 1);

        shader.bind();
        shader.setModelViewMatrix(transformMatrix);
        mesh.render();
        shader.unbind();
    }

    public static void draw(Vector2f p1, Vector2f p2)
    {
        draw(p1.x, p1.y, p2.x, p2.y);
    }

    public static void drawPoint(float x, float y)
    {
        transformMatrix.identity().translate(x, y, 0).scale(lineWidth, lineWidth, 1);

        shader.bind();
        shader.setModelViewMatrix(transformMatrix);
        mesh.render();
        shader.unbind();
    }

}
