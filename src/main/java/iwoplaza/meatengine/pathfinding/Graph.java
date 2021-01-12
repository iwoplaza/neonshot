package iwoplaza.meatengine.pathfinding;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<T extends IGraphNode> implements IGraph<T>
{
    private final Set<T> nodes;
    private final Map<T, Set<T>> connections;

    public Graph(Set<T> nodes, Map<T, Set<T>> connections)
    {
        this.nodes = nodes;
        this.connections = connections;
    }

    public Set<T> getConnections(T node)
    {
        return new HashSet<>(connections.get(node));
    }
}
