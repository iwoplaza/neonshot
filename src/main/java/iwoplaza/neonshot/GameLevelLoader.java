package iwoplaza.neonshot;

import de.matthiasmann.twl.utils.PNGDecoder;
import iwoplaza.meatengine.loader.LevelLoader;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.meatengine.world.tile.TileRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class GameLevelLoader
{
    private static final int PLAYER_COLOR = 0x00ff00;

    private final LevelLoader loader;
    private final PlayerSpawner playerSpawner;

    public GameLevelLoader(PlayerSpawner playerSpawner)
    {
        this.playerSpawner = playerSpawner;
        this.loader = new LevelLoader(this::respondToColor);
    }

    public World loadFromStream(InputStream stream) throws IOException
    {
        return this.loader.loadFromStream(stream);
    }

    private void respondToColor(World world, int x, int y, int color)
    {
        Tile tile = TileRegistry.getForColor(color);
        if (tile != null)
        {
            world.getTileMap().setTile(x, y, tile);
            return;
        }

        if (color == PLAYER_COLOR)
        {
            this.playerSpawner.spawnPlayerAt(world, x, y);
            return;
        }

        throw new IllegalStateException(String.format("Unregistered map color: 0x%08X", color));
    }

    public interface PlayerSpawner
    {
        void spawnPlayerAt(World world, int x, int y);
    }
}
