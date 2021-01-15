package iwoplaza.meatengine.world;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import org.joml.Vector2i;

public abstract class Entity implements IDisposable
{

    /**
     * The entity's tile-position before a movement.
     * Used by the renderer to interpolate with nextPosition.
     */
    protected Vector2i prevPosition = new Vector2i();

    /**
     * The entity's tile-position after a movement.
     * Used by the renderer to interpolate with prevPosition.
     */
    protected Vector2i nextPosition = new Vector2i();

    /**
     * Allows the entity to know what context it exists in.
     */
    protected IWorld world;

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

    public void setPosition(int x, int y)
    {
        this.prevPosition.set(x, y);
        this.nextPosition.set(x, y);
    }

    public void setPosition(Vector2i position)
    {
        this.setPosition(position.x, position.y);
    }

    public Vector2i getPrevPosition()
    {
        return prevPosition;
    }

    public Vector2i getNextPosition()
    {
        return nextPosition;
    }

    public IWorld getWorld()
    {
        return this.world;
    }

    @Override
    public abstract void dispose();

}
