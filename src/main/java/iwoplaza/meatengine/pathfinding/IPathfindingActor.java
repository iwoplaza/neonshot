package iwoplaza.meatengine.pathfinding;

import iwoplaza.meatengine.Direction;
import org.joml.Vector2ic;

import java.util.List;

public interface IPathfindingActor
{
    void setPosition(Vector2ic position);

    void setTarget(Vector2ic to);

    boolean isRequestPending();

    Vector2ic getPosition();

    Vector2ic getTarget();

    List<Vector2ic> getPath();

    Direction getDirection();

    /**
     * Do not call this method if you worry about slowing down the thread you're running it from.
     * Call requestPath instead.
     */
    void computePath(Vector2ic from, Vector2ic to);
}
