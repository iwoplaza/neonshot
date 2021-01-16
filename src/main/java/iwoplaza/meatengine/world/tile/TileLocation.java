package iwoplaza.meatengine.world.tile;

import iwoplaza.meatengine.pathfinding.IGraphNode;

public class TileLocation implements IGraphNode
{

    public final int x;
    public final int y;

    public TileLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
