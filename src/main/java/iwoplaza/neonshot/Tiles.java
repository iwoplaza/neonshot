package iwoplaza.neonshot;

import iwoplaza.meatengine.world.tile.FullTile;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.neonshot.world.tile.ChessBoardTile;

public class Tiles
{

    public static final Tile VOID = new FullTile(0);
    public static final Tile CHESSBOARD_FLOOR = new ChessBoardTile(1);
    public static final Tile SOLID_WALL_PLAIN = new FullTile(3);

}
