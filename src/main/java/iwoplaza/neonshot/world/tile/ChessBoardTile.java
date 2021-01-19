package iwoplaza.neonshot.world.tile;

import iwoplaza.meatengine.graphics.tile.ITileRenderer;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.graphics.tile.ChessBoardTileRenderer;
import org.joml.Vector2ic;

public class ChessBoardTile extends Tile
{

    private final ChessBoardTileRenderer renderer;

    public ChessBoardTile(Vector2ic textureFrame, int mapColor)
    {
        super(mapColor);
        this.renderer = new ChessBoardTileRenderer(textureFrame);
    }

    @Override
    public boolean isTraversable()
    {
        return true;
    }

    @Override
    public ITileRenderer getRenderer()
    {
        return renderer;
    }

}
