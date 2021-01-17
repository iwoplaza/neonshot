package iwoplaza.meatengine;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public enum Direction
{
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static final Vector2ic UP = new Vector2i(0, 1);
    public static final Vector2ic RIGHT = new Vector2i(1, 0);
    public static final Vector2ic DOWN = new Vector2i(0, -1);
    public static final Vector2ic LEFT = new Vector2i(-1, 0);

    public Vector2ic getAsVector()
    {
        switch (this)
        {
            case NORTH:
                return UP;
            case EAST:
                return RIGHT;
            case SOUTH:
                return DOWN;
            case WEST:
                return LEFT;
        }

        throw new IllegalStateException("Why are we still here.");
    }

    public float getAngle()
    {
        return (float) (this.ordinal() * -Math.PI/2);
    }

    public static Direction fromVector(Vector2ic vec)
    {
        if (UP.equals(vec))
        {
            return NORTH;
        }
        else if (RIGHT.equals(vec))
        {
            return EAST;
        }
        else if (DOWN.equals(vec))
        {
            return SOUTH;
        }
        else if (LEFT.equals(vec))
        {
            return WEST;
        }

        return null;
    }
}
