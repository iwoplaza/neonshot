package ivesiris.neonshot.engine.graphics.mesh;

public class FlatMesh extends Mesh
{

    public FlatMesh(int[] indices, float[] positions)
    {
        super(indices);

        this.bindVertexArray();
        createFloatVBO(positions, 2);
        this.unbindVertexArray();
    }


}
