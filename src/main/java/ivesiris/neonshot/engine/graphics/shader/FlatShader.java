package ivesiris.neonshot.engine.graphics.shader;

import org.joml.Matrix4f;

public class FlatShader extends Shader
{

    private final String PROJECTION_MATRIX = "uProjectionMatrix";
    private final String MODEL_VIEW_MATRIX = "uModelViewMatrix";
    private final String COLOR = "uColor";

    public FlatShader()
    {
        super();
    }

    @Override
    protected void createUniforms() throws Exception
    {
        this.createUniform(PROJECTION_MATRIX);
        this.createUniform(MODEL_VIEW_MATRIX);
        this.createUniform(COLOR);
    }

    public void setProjectionMatrix(Matrix4f matrix)
    {
        this.setUniform(PROJECTION_MATRIX, matrix);
    }

    public void setModelViewMatrix(Matrix4f matrix)
    {
        this.setUniform(MODEL_VIEW_MATRIX, matrix);
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.setUniform(COLOR, r, g, b, a);
    }


}
