package iwoplaza.neonshot.world.entity;

import iwoplaza.neonshot.Direction;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public abstract class SlidingEntity extends DirectionalEntity
{

    protected int moveCooldown = 0;

    protected boolean moveStep(Direction direction)
    {
        return moveBy(direction.getAsVector());
    }

    protected boolean moveBy(Vector2ic offset)
    {
        this.prevPosition.set(this.nextPosition);
        Vector2i nextPosition = new Vector2i(this.prevPosition);
        nextPosition.add(offset);

        if (this.world.canTraverseTo(nextPosition, true))
        {
            this.nextPosition.set(nextPosition);
            return true;
        }

        return false;
    }

    public int getMoveCooldown()
    {
        return moveCooldown;
    }

    public abstract int getMoveDuration();
}
