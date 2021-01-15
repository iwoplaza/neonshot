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
            default:
                return new Vector2i(0, 0);
        }
    }
}
