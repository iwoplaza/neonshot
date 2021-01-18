package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.shader.base.BasicShader;

public class Drawable<S extends BasicShader> implements IDisposable
{
    private final Mesh mesh;
    private final S shader;

    public Drawable(Mesh mesh, S shader)
    {
        this.mesh = mesh;
        this.shader = shader;
    }

    public void draw(IShaderSetup<S> shaderSetup)
    {
        shader.bind();

        shaderSetup.setup(shader);
        shader.getProjectionMatrix().set(GlStack.MAIN.projectionMatrix);
        shader.getModelViewMatrix().set(GlStack.MAIN.top());

        this.mesh.render();
    }

    public void draw()
    {
        shader.bind();

        shader.getProjectionMatrix().set(GlStack.MAIN.projectionMatrix);
        shader.getModelViewMatrix().set(GlStack.MAIN.top());

        this.mesh.render();
    }

    public S getShader()
    {
        return shader;
    }

    @Override
    public void dispose()
    {
        this.mesh.dispose();
    }

    public interface IShaderSetup<S extends BasicShader>
    {
        void setup(S shader);
    }
}
