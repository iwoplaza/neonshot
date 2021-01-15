package iwoplaza.meatengine.world.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileRegistry
{

    private static final List<Tile> registeredTiles = new ArrayList<>();
    private static final Map<Integer, Tile> colorToTileMap = new HashMap<>();

    static int registerTile(Tile tile)
    {
        registeredTiles.add(tile);

        colorToTileMap.put(tile.getMapColor(), tile);

        return registeredTiles.size() - 1;
    }

    public static Tile get(int tileId)
    {
        return registeredTiles.get(tileId);
    }

    public static Tile getForColor(int color)
    {
        return colorToTileMap.get(color);
    }

}
