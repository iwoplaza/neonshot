package ivesiris.neonshot.engine.graphics.mesh;

public class TexturedMesh extends Mesh
{
    public TexturedMesh(int[] indices, float[] positions, float[] texCoords)
    {
        super(indices);

        this.bindVertexArray();
        createFloatVBO(positions, 2);
        createFloatVBO(texCoords, 2);
        this.unbindVertexArray();
    }
}
