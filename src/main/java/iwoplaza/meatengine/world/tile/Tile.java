package iwoplaza.meatengine.world.tile;

import iwoplaza.meatengine.graphics.tile.ITileRenderer;

public abstract class Tile
{

    protected final int id;

    public Tile()
    {
        this.id = TileRegistry.registerTile(this);
    }

    public int getId()
    {
        return id;
    }

    public static char UP = 0x01;
    public static char RIGHT = 0x02;
    public static char DOWN = 0x04;
    public static char LEFT = 0x08;

    public abstract ITileRenderer getRenderer();

}
