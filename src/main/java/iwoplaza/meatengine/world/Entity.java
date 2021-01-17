package iwoplaza.meatengine.world;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import org.joml.Vector2ic;

public abstract class Entity implements IDisposable
{

    /**
     * Allows the entity to know what context it exists in.
     */
    protected IWorld world;

    protected boolean dead = false;

    public Entity()
    {
        this.world = null;
    }

    public void onSpawnedIn(IWorld world)
    {
        if (this.getWorld() != null)
            throw new IllegalStateException("This entity has already been spawned in a world.");

        this.world = world;
    }

    public void updatePerFrame(IEngineContext context)
    {
    }

    public void update(IEngineContext context)
    {
    }

    public IWorld getWorld()
    {
        return this.world;
    }

    public boolean isDead()
    {
        return this.dead;
    }

    @Override
    public abstract void dispose();

    public abstract boolean doesOccupyPosition(Vector2ic tileLocation);
}
