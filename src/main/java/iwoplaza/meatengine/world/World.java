package iwoplaza.meatengine.world;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.tile.TileMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class World implements IDisposable, IWorld
{
    private final List<Entity> entities;
    private final TileMap tileMap;

    public World(int width, int height)
    {
        this.entities = new ArrayList<>();
        this.tileMap = new TileMap(width, height);
    }

    public void update(IEngineContext context)
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            Entity entity = entities.get(i);

            entity.update(context);
        }
    }

    public void updatePerFrame(IEngineContext context)
    {
        for (int i = 0; i < entities.size(); ++i)
        {
            Entity entity = entities.get(i);

            entity.updatePerFrame(context);
        }
    }

    /**
     * Spawns a newly created entity into this world.
     * This only works on newly created entities. If you try to add an entity that already belongs
     * to another world, it cannot be added to this one as well.
     * @param entity The entity to spawn
     */
    public void spawnEntity(Entity entity)
    {
        entity.onSpawnedIn(this);
        this.entities.add(entity);
    }

    @Override
    public Collection<Entity> getEntities()
    {
        return this.entities;
    }

    public TileMap getTileMap()
    {
        return this.tileMap;
    }

    @Override
    public void dispose()
    {
        this.entities.forEach(Entity::dispose);
    }
}
