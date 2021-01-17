package iwoplaza.meatengine.world;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.pathfinding.PathfindingPool;
import iwoplaza.meatengine.world.tile.TileMap;
import iwoplaza.meatengine.Direction;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.*;

public class World implements IDisposable, IWorld
{
    private final List<Entity> entities;
    private final List<IPlayerEntity> players;
    private final TileMap tileMap;
    private final PathfindingPool pathfindingPool;

    public World(int width, int height)
    {
        this.entities = new ArrayList<>();
        this.players = new ArrayList<>();
        this.tileMap = new TileMap(width, height);
        this.pathfindingPool = new PathfindingPool();

        this.pathfindingPool.start();
    }

    public void update(IEngineContext context)
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            Entity entity = entities.get(i);

            entity.update(context);

            if (entity.isDead())
            {
                i--;
                entities.remove(entity);
                if (entity instanceof IPlayerEntity)
                {
                    players.remove(entity);
                }
            }
        }
    }

    public void updatePerFrame(IEngineContext context)
    {
        for (Entity entity : entities)
        {
            entity.updatePerFrame(context);
        }
    }

    /**
     * Spawns a newly created entity into this world.
     * This only works on newly created entities. If you try to add an entity that already belongs
     * to another world, it cannot be added to this one as well.
     * @param entity The entity to spawn
     */
    @Override
    public void spawnEntity(Entity entity)
    {
        entity.onSpawnedIn(this);
        this.entities.add(entity);

        if (entity instanceof IPlayerEntity)
        {
            this.players.add((IPlayerEntity) entity);
        }
    }

    @Override
    public Collection<Entity> getEntities()
    {
        return this.entities;
    }

    @Override
    public List<IPlayerEntity> getPlayers()
    {
        return this.players;
    }

    public TileMap getTileMap()
    {
        return this.tileMap;
    }

    @Override
    public PathfindingPool getPathfindingPool()
    {
        return pathfindingPool;
    }

    @Override
    public boolean canTraverseTo(Vector2ic tileLocation, boolean includeEntities)
    {
        if (tileLocation.x() < 0 || tileLocation.y() < 0 ||
            tileLocation.x() >= this.tileMap.getWidth() || tileLocation.y() >= this.tileMap.getHeight())
        {
            return false;
        }

        if (includeEntities)
        {
            for (Entity entity : this.entities)
            {
                if (entity.doesOccupyPosition(tileLocation))
                {
                    return false;
                }
            }
        }

        return this.tileMap.getTileAt(tileLocation).getTile().isTraversable();
    }

    @Override
    public Set<Vector2ic> getConnections(Vector2ic node)
    {
        Set<Vector2ic> connections = new HashSet<>();

        Vector2ic up =       new Vector2i(node).add(Direction.UP);
        Vector2ic right =    new Vector2i(node).add(Direction.RIGHT);
        Vector2ic down =     new Vector2i(node).add(Direction.DOWN);
        Vector2ic left =     new Vector2i(node).add(Direction.LEFT);

        if (canTraverseTo(up, false))
            connections.add(up);

        if (canTraverseTo(right, false))
            connections.add(right);

        if (canTraverseTo(down, false))
            connections.add(down);

        if (canTraverseTo(left, false))
            connections.add(left);

        return connections;
    }

    @Override
    public void dispose()
    {
        this.entities.forEach(Entity::dispose);
        this.pathfindingPool.stopRunning();
    }
}
