package iwoplaza.meatengine.pathfinding;

import java.util.*;

public class AStarSolver<T>
{
    private final IGraph<T> graph;

    public AStarSolver(IGraph<T> graph)
    {
        this.graph = graph;
    }

    public List<T> findRoute(T from, T to, ICostScorer<T> nextNodeScorer, ICostScorer<T> targetScorer)
    {
        Queue<RouteNode<T>> openSet = new PriorityQueue<>();
        Map<T, RouteNode<T>> metaMap = new HashMap<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0f, targetScorer.computeCost(from, to));
        openSet.add(start);
        metaMap.put(from, start);

        while (!openSet.isEmpty())
        {
            RouteNode<T> currentMeta = openSet.poll();
            T currentNode = currentMeta.getCurrent();

            if (currentNode.equals(to))
            {
                // We've reached the end.
                return backtrackFrom(currentMeta, metaMap);
            }

            Set<T> neighbours = graph.getConnections(currentNode);
            for (T connected : neighbours)
            {
                RouteNode<T> nextNode = metaMap.getOrDefault(connected, new RouteNode<>(connected));
                metaMap.put(connected, nextNode);

                float newScore = currentMeta.getRouteScore() + nextNodeScorer.computeCost(currentNode, connected);
                if (newScore < nextNode.getRouteScore())
                {
                    nextNode.setPrevious(currentNode);
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connected, to));
                    openSet.add(nextNode);
                }
            }
        }

        throw new PathNotFoundException(String.format("No path found from %s to %s.", from, to));
    }

    /**
     * Returns a path from (whatever the destination's "previous" chain leads to) to the destination node.
     * @param destination The final node of the returned path.
     * @param metaMap A look up for meta route information for each node.
     */
    private static <T> List<T> backtrackFrom(RouteNode<T> destination, Map<T, RouteNode<T>> metaMap)
    {
        List<T> route = new LinkedList<>();

        do
        {
            route.add(0, destination.getCurrent());
            destination = metaMap.get(destination.getPrevious());
        }
        while (destination != null);

        return route;
    }
}
