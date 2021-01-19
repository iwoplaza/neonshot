package iwoplaza.meatengine.graphics.tile;

import iwoplaza.meatengine.world.tile.TileData;
import iwoplaza.meatengine.world.tile.TileLocation;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.Arrays;
import java.util.List;

public class FullTileRenderer extends TileRenderer
{
    private final Vector2ic textureFrame;

    public FullTileRenderer(Vector2ic textureFrame)
    {
        this.textureFrame = textureFrame;
    }

    @Override
    public Vector2ic getTextureFrame(TileData data, TileLocation location)
    {
        return textureFrame;
    }

}
