package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import org.joml.Vector2ic;

public abstract class ItemEntity extends TileboundEntity implements IConsumable
{
    private int lifetime = 0;
    private final int maxLifetime;
    private final boolean hasLimitedLifetime;

    public abstract Vector2ic getTextureFrame();

    public ItemEntity(Vector2ic position, int maxLifetime)
    {
        this.setPosition(position);
        this.maxLifetime = maxLifetime;
        this.hasLimitedLifetime = true;
    }

    public ItemEntity(Vector2ic position)
    {
        this.setPosition(position);
        this.maxLifetime = 0;
        this.hasLimitedLifetime = false;
    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        this.lifetime++;
        if (this.lifetime > this.maxLifetime)
        {
            this.dead = true;
        }
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return false;
    }

    @Override
    public boolean isConsumableFrom(Vector2ic position)
    {
        return this.nextPosition.equals(position);
    }

    public int getLifetime()
    {
        return lifetime;
    }

    public int getMaxLifetime()
    {
        return maxLifetime;
    }

    public boolean isHasLimitedLifetime()
    {
        return hasLimitedLifetime;
    }
}
