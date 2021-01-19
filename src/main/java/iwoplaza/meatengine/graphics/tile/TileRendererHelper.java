package iwoplaza.meatengine.graphics.tile;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.Arrays;
import java.util.List;

public class TileRendererHelper
{
    public static void drawRectangle(Vector2ic min, Vector2ic max, Vector2fc uvMin, Vector2fc uvMax, Vector2fc uvMargin, List<Integer> indices, List<Float> positions, List<Float> texCoords)
    {
        final int idxOff = positions.size() / 2;
        indices.addAll(Arrays.asList(idxOff, idxOff + 3, idxOff + 1, idxOff + 1, idxOff + 3, idxOff + 2));

        positions.addAll(Arrays.asList(
                (float) min.x(), (float) min.y(),
                (float) max.x(), (float) min.y(),
                (float) max.x(), (float) max.y(),
                (float) min.x(), (float) max.y()
        ));

        texCoords.addAll(Arrays.asList(
                uvMin.x() + uvMargin.x(), uvMax.y() - uvMargin.y(),
                uvMax.x() - uvMargin.x(), uvMax.y() - uvMargin.y(),
                uvMax.x() - uvMargin.x(), uvMin.y() + uvMargin.y(),
                uvMin.x() + uvMargin.x(), uvMin.y() + uvMargin.y()));
    }

    public static void drawRectangle(Vector2ic pos, Vector2ic size, Vector2ic uv, ITileRenderer.TileMapSpec tileMapSpec, List<Integer> indices, List<Float> positions, List<Float> texCoords)
    {
        Vector2f uvMin = new Vector2f((float) uv.x() / tileMapSpec.width, (float) uv.y() / tileMapSpec.height);
        Vector2f uvMax = new Vector2f(uvMin).add((float) tileMapSpec.tileSize / tileMapSpec.width, (float) tileMapSpec.tileSize / tileMapSpec.height);
        Vector2f uvMargin = new Vector2f(
            0.01f * tileMapSpec.tileSize / tileMapSpec.width,
            0.01f * tileMapSpec.tileSize  / tileMapSpec.height
        );

        drawRectangle(pos, new Vector2i(pos).add(size), uvMin, uvMax, uvMargin, indices, positions, texCoords);
    }
}
