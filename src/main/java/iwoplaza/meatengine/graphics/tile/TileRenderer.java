package iwoplaza.meatengine.graphics.tile;

import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public abstract class TileRenderer implements ITileRenderer
{
    public abstract Vector2ic getTextureFrame(TileData data, TileLocation location);

    @Override
    public void render(TileData tileData, TileLocation location, TileMapSpec tileMapSpec, List<Integer> indices, List<Float> positions, List<Float> texCoords)
    {
        final int x = location.x * tileMapSpec.tileSize;
        final int y = location.y * tileMapSpec.tileSize;

        Vector2i uv = new Vector2i(this.getTextureFrame(tileData, location));
        uv.mul(tileMapSpec.tileSize);

        TileRendererHelper.drawRectangle(
                new Vector2i(x, y),
                new Vector2i(tileMapSpec.tileSize, tileMapSpec.tileSize),
                uv,
                tileMapSpec,
                indices,
                positions,
                texCoords
        );
    }
}
