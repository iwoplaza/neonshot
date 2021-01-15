package iwoplaza.meatengine.world.tile;

import iwoplaza.meatengine.graphics.tile.FullTileRenderer;
import iwoplaza.meatengine.graphics.tile.ITileRenderer;

public class FullTile extends Tile
{

    private final int textureFrame;

    public FullTile(int textureFrame)
    {
        this.textureFrame = textureFrame;
    }

    @Override
    public ITileRenderer getRenderer()
    {
        return new FullTileRenderer(this.textureFrame);
    }

}
