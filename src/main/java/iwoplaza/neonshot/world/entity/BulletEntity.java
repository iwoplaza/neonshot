package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.Entity;
import org.joml.Vector2ic;

public abstract class BulletEntity extends TileboundEntity implements IDamageSource
{
    private final Entity owner;
    private final Direction direction;
    private final int moveDuration;

    private int moveCooldown;

    public BulletEntity(Entity owner, Vector2ic position, Direction direction, int moveDuration)
    {
        this.owner = owner;
        this.direction = direction;
        this.moveDuration = moveDuration;
        this.setPosition(position);

        this.moveCooldown = 0;
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

        if (moveCooldown > 0)
        {
            moveCooldown--;
        }

        if (moveCooldown == 0)
        {
            this.prevPosition.set(this.nextPosition);
            this.nextPosition.add(this.direction.getAsVector());
            this.moveCooldown = this.moveDuration;
        }

        if (!this.world.canTraverseTo(this.nextPosition, false))
        {
            this.dead = true;
        }

        for (Entity entity : this.world.getEntities())
        {
            if (entity instanceof IDamageable && entity != this.owner)
            {
                IDamageable damageable = (IDamageable) entity;
                if ((damageable.isHittableFrom(this.nextPosition) || damageable.isHittableFrom(this.prevPosition)))
                {
                    if (canDamage(damageable))
                    {
                        damageable.inflictDamage(this, getDamage());
                    }
                    this.dead = true;
                    return;
                }
            }
        }
    }

    public boolean canDamage(IDamageable damageable)
    {
        return true;
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return false;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public int getMoveDuration()
    {
        return moveDuration;
    }

    public int getMoveCooldown()
    {
        return moveCooldown;
    }

    public abstract int getSpriteFrame();

    @Override
    public void dispose()
    {

    }
}
