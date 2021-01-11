package ivesiris.neonshot.engine.graphics;

import org.joml.Matrix4f;

public class GlState
{

    public final Matrix4f matrix;

    public GlState(GlState other)
    {
        this(other.matrix);
    }

    public GlState(Matrix4f matrix)
    {
        this.matrix = new Matrix4f(matrix);
    }

    public GlState()
    {
        this.matrix = new Matrix4f();
    }

}
