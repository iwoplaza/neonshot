package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.world.Entity;
import iwoplaza.neonshot.Direction;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public abstract class DirectionalEntity extends Entity
{
    protected Direction direction = Direction.EAST;

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

    public void setPosition(int x, int y)
    {
        this.prevPosition.set(x, y);
        this.nextPosition.set(x, y);
    }

    public void setPosition(Vector2ic position)
    {
        this.setPosition(position.x(), position.y());
    }

    public Vector2ic getPrevPosition()
    {
        return prevPosition;
    }

    public Vector2ic getNextPosition()
    {
        return nextPosition;
    }

    public Direction getDirection()
    {
        return direction;
    }
}
