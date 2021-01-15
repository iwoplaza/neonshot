package iwoplaza.neonshot.world.tile;

import iwoplaza.meatengine.graphics.tile.ITileRenderer;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.graphics.tile.ChessBoardTileRenderer;

public class ChessBoardTile extends Tile
{

    private final int textureFrame;

    public ChessBoardTile(int textureFrame, int mapColor)
    {
        super(mapColor);
        this.textureFrame = textureFrame;
    }

    @Override
    public ITileRenderer getRenderer()
    {
        return new ChessBoardTileRenderer(this.textureFrame);
    }

}
