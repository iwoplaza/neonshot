package iwoplaza.meatengine.graphics.tile;

import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;

import java.util.Arrays;
import java.util.List;

public class FullTileRenderer implements ITileRenderer
{

    private final int textureFrame;

    public FullTileRenderer(int textureFrame)
    {
        this.textureFrame = textureFrame;
    }

    @Override
    public void render(TileData data, TileLocation location, int tileSize, int tileMapWidth, int tileMapHeight, List<Integer> indices, List<Float> positions, List<Float> texCoords)
    {
        final int x = location.x * tileSize;
        final int y = location.y * tileSize;

        final int idxOff = positions.size() / 2;
        indices.addAll(Arrays.asList(idxOff, idxOff + 3, idxOff + 1, idxOff + 1, idxOff + 3, idxOff + 2));

        positions.addAll(Arrays.asList(
                (float) x, (float) y,
                (float) (x + tileSize), (float) y,
                (float) (x + tileSize), (float) (y + tileSize),
                (float) x, (float) (y + tileSize)
        ));
        float u0 = (float) ((textureFrame * tileSize) % tileMapWidth) / tileMapWidth;
        float v0 = (float) ((textureFrame * tileSize) / tileMapWidth) / tileMapHeight;
        float u1 = u0 + (float) tileSize / tileMapWidth;
        float v1 = v0 + (float) tileSize / tileMapHeight;
        texCoords.addAll(Arrays.asList(
                u0, v1,
                u1, v1,
                u1, v0,
                u0, v0));
    }

}
