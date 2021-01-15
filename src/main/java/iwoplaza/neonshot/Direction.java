package iwoplaza.neonshot;

import org.joml.Vector2i;

public enum Direction
{
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Vector2i getAsVector()
    {
        switch (this)
        {
            case NORTH:
                return new Vector2i(0, 1);
            case EAST:
                return new Vector2i(1, 0);
            case SOUTH:
                return new Vector2i(0, -1);
            case WEST:
                return new Vector2i(-1, 0);
            default:
                return new Vector2i(0, 0);
        }
    }
}
