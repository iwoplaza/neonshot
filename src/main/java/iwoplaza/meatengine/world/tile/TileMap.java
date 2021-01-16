package iwoplaza.meatengine.world.tile;

import org.joml.Vector2ic;

public class TileMap
{
    protected final int width;
    protected final int height;
    protected final TileData[] tiles;

    public TileMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.tiles = new TileData[width * height];
    }

    public void setTile(int x, int y, int tileId)
    {
        if (this.tiles[y * width + x] == null)
            this.tiles[y * width + x] = new TileData(tileId);
        else
            this.tiles[y * width + x].setTileId(tileId);
    }

    public void setTile(int x, int y, Tile tile)
    {
        this.setTile(x, y, tile.getId());
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public TileData getTileAt(int x, int y)
    {
        return this.tiles[y * width + x];
    }

    public TileData getTileAt(Vector2ic position)
    {
        return this.getTileAt(position.x(), position.y());
    }

    public TileData getTileAt(TileLocation tileLocation)
    {
        return this.getTileAt(tileLocation.x, tileLocation.y);
    }
}
