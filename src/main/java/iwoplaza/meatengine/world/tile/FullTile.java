package iwoplaza.meatengine.world.tile;

import iwoplaza.meatengine.graphics.tile.FullTileRenderer;
import iwoplaza.meatengine.graphics.tile.ITileRenderer;
import org.joml.Vector2ic;

public class FullTile extends Tile
{

    private final boolean traversable;
    private final FullTileRenderer renderer;

    public FullTile(Vector2ic textureFrame, int mapColor, boolean traversable)
    {
        super(mapColor);
        this.traversable = traversable;
        this.renderer = new FullTileRenderer(textureFrame);
    }

    @Override
    public boolean isTraversable()
    {
        return traversable;
    }

    @Override
    public ITileRenderer getRenderer()
    {
        return this.renderer;
    }

}
