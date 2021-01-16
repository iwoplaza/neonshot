package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.neonshot.Direction;
import org.joml.Vector2i;

public abstract class BulletEntity extends DirectionalEntity
{
    public BulletEntity(Vector2i position, Direction direction)
    {
        this.setPosition(position);
        this.direction = direction;
    }

    @Override
    public void update(IEngineContext context)
    {
        this.prevPosition.set(this.nextPosition);

        this.nextPosition.add(this.direction.getAsVector());

        if (!this.world.canTraverseTo(this.nextPosition))
        {
            this.dead = true;
        }

        super.update(context);
    }

    @Override
    public void dispose()
    {

    }
}
