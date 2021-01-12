package iwoplaza.meatengine.pathfinding;

public class PathNotFoundException extends RuntimeException
{
    public PathNotFoundException(String message)
    {
        super(message);
    }
}
