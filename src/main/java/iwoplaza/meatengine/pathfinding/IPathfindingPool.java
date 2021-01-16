package iwoplaza.meatengine.pathfinding;

import org.joml.Vector2ic;

public interface IPathfindingPool
{
    void requestPath(PathfindingActor actor, Vector2ic from, Vector2ic to);
}
