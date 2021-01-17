package iwoplaza.meatengine.world.tile;

import org.joml.Vector2ic;

public class TileMap
{
    protected final int width;
    protected final int height;
    protected final TileData[] tiles;

    /**
     * Should be true if the tilemap needs to be re-rendered.
     */
    protected boolean dirty = true;

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

        this.markDirty();
    }

    public void setTile(int x, int y, Tile tile)
    {
        this.setTile(x, y, tile.getId());
    }

    public void markDirty()
    {
        this.dirty = true;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isDirty()
    {
        return dirty;
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
