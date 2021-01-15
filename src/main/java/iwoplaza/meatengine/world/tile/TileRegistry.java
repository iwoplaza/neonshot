package iwoplaza.meatengine.world.tile;

import java.util.ArrayList;
import java.util.List;

public class TileRegistry
{

    private static final List<Tile> registeredTiles = new ArrayList<>();

    static int registerTile(Tile tile)
    {
        registeredTiles.add(tile);
        return registeredTiles.size() - 1;
    }

    public static Tile get(int tileId)
    {
        return registeredTiles.get(tileId);
    }

}
