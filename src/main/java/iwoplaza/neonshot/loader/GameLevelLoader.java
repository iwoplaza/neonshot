package iwoplaza.neonshot.loader;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.loader.LevelLoader;
import iwoplaza.meatengine.loader.ResourceLoader;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.world.tile.Tile;
import iwoplaza.meatengine.world.tile.TileRegistry;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.world.ChallengeRoom;
import iwoplaza.neonshot.world.IChallengeEntityFactory;
import iwoplaza.neonshot.world.entity.FinishZoneEntity;
import org.joml.Vector2i;

import java.io.IOException;

public class GameLevelLoader
{
    private static final int PLAYER_COLOR = 0x00ff00;
    private static final int FINISH_ZONE_COLOR = 0x0000ff;

    private final LevelLoader loader;
    private final IPlayerSpawner playerSpawner;
    private final FinishZoneEntity.IFinishCallback finishCallback;

    public GameLevelLoader(IPlayerSpawner playerSpawner, FinishZoneEntity.IFinishCallback finishCallback)
    {
        this.playerSpawner = playerSpawner;
        this.finishCallback = finishCallback;
        this.loader = new LevelLoader(this::respondToColor);
    }

    public World loadFromPath(String levelPath, String contentPath, IEntityAssigner entityAssigner) throws IOException
    {
        World world = this.loader.loadFromStream(AssetLocation.asResource(Statics.RES_ORIGIN, levelPath).getInputStream());

        LevelContentData contentData = ResourceLoader.loadJSONResource(AssetLocation.asResource(Statics.RES_ORIGIN, contentPath).getResourcePath(), LevelContentData.class);

        if (contentData.challengeRooms != null)
        {
            for (LevelContentData.ChallengeRoomData d : contentData.challengeRooms)
            {
                ChallengeRoom room = new ChallengeRoom(
                        mapCoordinates(world, d.entranceX, d.entranceY),
                        d.entranceDirection.getFlippedVertically(),
                        mapCoordinates(world, d.exitX, d.exitY)
                );
                d.entries.forEach(e -> {
                    room.addEntry(mapCoordinates(world, e.x, e.y), entityAssigner.getFactoryForKey(e.entityKey));
                });
                world.addChallengeRoom(room);
            }
        }

        return world;
    }

    private static Vector2i mapCoordinates(World world, int x, int y)
    {
        int height = world.getTileMap().getHeight();
        return new Vector2i(x, height - 1 - y);
    }

    public World loadFromName(String name, IEntityAssigner entityAssigner) throws IOException
    {
        return loadFromPath(String.format("levels/%s.png", name), String.format("levels/%s.json", name), entityAssigner);
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

        if (color == FINISH_ZONE_COLOR)
        {
            world.spawnEntity(new FinishZoneEntity(new Vector2i(x, y), this.finishCallback));
            world.getTileMap().setTile(x, y, Tiles.CHESSBOARD_FLOOR);
            return;
        }

        throw new IllegalStateException(String.format("Unregistered map color: 0x%08X", color));
    }

    public interface IPlayerSpawner
    {
        void spawnPlayerAt(World world, int x, int y);
    }

    public interface IEntityAssigner
    {
        IChallengeEntityFactory getFactoryForKey(String key);
    }
}
