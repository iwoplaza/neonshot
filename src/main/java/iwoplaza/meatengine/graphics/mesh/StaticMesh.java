package iwoplaza.meatengine.graphics.mesh;

public class StaticMesh extends Mesh
{

    public StaticMesh(int[] indices, float[] positions, float[] normals, float[] texCoords)
    {
        super(indices);

        this.bindVertexArray();
        createFloatVBO(positions, 3);
        createFloatVBO(normals, 3);
        createFloatVBO(texCoords, 2);
        this.unbindVertexArray();
    }

}
