package iwoplaza.meatengine.pathfinding;

import java.util.Set;

public interface IGraph<T>
{
    /**
     * Returns nodes directly connected to the node parameter.
     */
    Set<T> getConnections(T node);
}
