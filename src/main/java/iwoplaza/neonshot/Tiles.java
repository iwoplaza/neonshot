package iwoplaza.neonshot;

import iwoplaza.meatengine.world.tile.FullTile;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.world.tile.ChessBoardTile;

public class Tiles
{

    public static Tile VOID;
    public static Tile CHESSBOARD_FLOOR;
    public static Tile SOLID_WALL_PLAIN;

    public static void registerTiles()
    {
        VOID = new FullTile(0, 0x000000, false);
        CHESSBOARD_FLOOR = new ChessBoardTile(1, 0xFFFFFF);
        SOLID_WALL_PLAIN = new FullTile(3, 0xAAAAAA, false);
    }

}
