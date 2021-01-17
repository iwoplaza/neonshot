package iwoplaza.meatengine.helper;

import iwoplaza.meatengine.graphics.mesh.FlatMesh;

public class MeshHelper
{
    public static FlatMesh createBorder(int width, int height, int thickness)
    {
        int[] indices = new int[] {
                // Left
                0, 3, 4, 4, 3, 7,
                // Bottom
                0, 4, 1, 1, 4, 5,
                // Right
                5, 6, 2, 1, 5, 2,
                // Top
                7, 2, 6, 7, 3, 2
        };

        float[] positions = new float[] {
                0, 0,
                width, 0,
                width, height,
                0, height,

                thickness, thickness,
                width - thickness, thickness,
                width - thickness, height - thickness,
                thickness, height - thickness
        };

        return new FlatMesh(indices, positions);
    }

    public static FlatMesh createFlatRectangle(int width, int height)
    {
        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2,
        };

        float[] positions = new float[] {
                0, height,
                0, 0,
                width, 0,
                width, height,
        };

        return new FlatMesh(indices, positions);
    }
}
