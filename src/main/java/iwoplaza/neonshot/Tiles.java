package iwoplaza.neonshot;

import iwoplaza.meatengine.world.tile.FullTile;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.world.tile.ChessBoardTile;
import org.joml.Vector2i;

public class Tiles
{

    public static Tile VOID;
    public static Tile CHESSBOARD_FLOOR;
    public static Tile SOLID_WALL_PLAIN;
    public static Tile SOLID_WALL_DOOR;

    public static void registerTiles()
    {
        VOID = new FullTile(new Vector2i(0, 0), 0x000000, false);
        CHESSBOARD_FLOOR = new ChessBoardTile(new Vector2i(1, 0), 0xFFFFFF);
        SOLID_WALL_PLAIN = new FullTile(new Vector2i(3, 0), 0xAAAAAA, false);
        SOLID_WALL_DOOR = new FullTile(new Vector2i(0, 1), 0xFFFF00, false);
    }

}
