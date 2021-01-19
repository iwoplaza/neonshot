package iwoplaza.neonshot.graphics.tile;

import iwoplaza.meatengine.graphics.tile.TileRenderer;
import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class ChessBoardTileRenderer extends TileRenderer
{
    private final Vector2ic textureFrame;
    private final Vector2ic offsetTextureFrame;

    public ChessBoardTileRenderer(Vector2ic textureFrame)
    {
        this.textureFrame = textureFrame;
        this.offsetTextureFrame = new Vector2i(this.textureFrame).add(1, 0);
    }

    @Override
    public Vector2ic getTextureFrame(TileData data, TileLocation location)
    {
        if ((location.x + location.y) % 2 == 0)
        {
            return offsetTextureFrame;
        }
        return textureFrame;
    }
}
