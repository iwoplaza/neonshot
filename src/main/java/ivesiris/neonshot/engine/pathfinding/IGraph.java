package ivesiris.neonshot.engine.pathfinding;

import java.util.Set;

public interface IGraph<T extends IGraphNode>
{
    /**
     * Returns nodes directly connected to the node parameter.
     */
    Set<T> getConnections(T node);
}
