package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.world.Entity;
import org.joml.Vector2ic;

public abstract class BulletEntity extends DirectionalEntity implements IDamageSource
{
    public final Entity owner;

    public BulletEntity(Entity owner, Vector2ic position, Direction direction)
    {
        this.owner = owner;
        this.direction = direction;
        this.setPosition(position);
    }

    protected abstract int getDamage();

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.dead)
        {
            return;
        }

        this.prevPosition.set(this.nextPosition);
        this.nextPosition.add(this.direction.getAsVector());

        if (!this.world.canTraverseTo(this.nextPosition, false))
        {
            this.dead = true;
        }

        for (Entity entity : this.world.getEntities())
        {
            if (entity instanceof IDamageable && entity != this.owner)
            {
                IDamageable damageable = (IDamageable) entity;
                if (damageable.isHittableFrom(this.nextPosition) || damageable.isHittableFrom(this.prevPosition))
                {
                    damageable.inflictDamage(this, getDamage());
                    this.dead = true;
                }
            }
        }
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return false;
    }

    @Override
    public void dispose()
    {

    }
}
