package iwoplaza.meatengine.world.tile;

public class TileData
{

    private int tileId;

    public TileData(int tileId)
    {
        this.tileId = tileId;
    }

    public int getTileId()
    {
        return this.tileId;
    }

    public void setTileId(int tileId)
    {
        this.tileId = tileId;
    }

    public Tile getTile()
    {
        return TileRegistry.get(this.tileId);
    }

}
