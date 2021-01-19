package iwoplaza.neonshot;

import iwoplaza.meatengine.world.tile.FullTile;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.world.tile.ChessBoardTile;
import org.joml.Vector2i;

public class Tiles
{

    public static Tile VOID;
    public static Tile CHESSBOARD_FLOOR;
    public static Tile SOLID_WALL_DOOR;
    public static Tile SOLID_WALL_PLAIN;
    public static Tile SOLID_WALL_CAMERAS;
    public static Tile SOLID_WALL_PAPERS;

    public static void registerTiles()
    {
        VOID = new FullTile(new Vector2i(0, 0), 0x000000, false);
        CHESSBOARD_FLOOR = new ChessBoardTile(new Vector2i(1, 0), 0xFFFFFF);
        SOLID_WALL_DOOR = new FullTile(new Vector2i(0, 1), 0xFFFF00, false);
        SOLID_WALL_PLAIN = new FullTile(new Vector2i(1, 1), 0xAAAAA0, false);
        SOLID_WALL_CAMERAS = new FullTile(new Vector2i(2, 1), 0xAAAAA1, false);
        SOLID_WALL_PAPERS = new FullTile(new Vector2i(3, 1), 0xAAAAA2, false);
    }

}
