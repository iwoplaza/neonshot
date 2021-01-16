package iwoplaza.meatengine.world.tile;

import iwoplaza.meatengine.graphics.tile.FullTileRenderer;
import iwoplaza.meatengine.graphics.tile.ITileRenderer;

public class FullTile extends Tile
{

    private final int textureFrame;
    private final boolean traversable;

    public FullTile(int textureFrame, int mapColor, boolean traversable)
    {
        super(mapColor);
        this.textureFrame = textureFrame;
        this.traversable = traversable;
    }

    @Override
    public boolean isTraversable()
    {
        return traversable;
    }

    @Override
    public ITileRenderer getRenderer()
    {
        return new FullTileRenderer(this.textureFrame);
    }

}
