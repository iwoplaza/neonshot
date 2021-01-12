package ivesiris.neonshot.engine.pathfinding;

public class RouteNode<T extends IGraphNode> implements Comparable<RouteNode>
{
    private final T current;
    private T previous;
    private float routeScore;
    private float estimatedScore;

    public RouteNode(T current)
    {
        this(current, null, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    public RouteNode(T current, T previous, float routeScore, float estimatedScore)
    {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public T getCurrent()
    {
        return current;
    }

    public T getPrevious()
    {
        return previous;
    }

    public float getRouteScore()
    {
        return routeScore;
    }

    public float getEstimatedScore()
    {
        return estimatedScore;
    }

    public void setPrevious(T previous)
    {
        this.previous = previous;
    }

    public void setRouteScore(float routeScore)
    {
        this.routeScore = routeScore;
    }

    public void setEstimatedScore(float estimatedScore)
    {
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(RouteNode o)
    {
        if (this.estimatedScore > o.estimatedScore)
        {
            return 1;
        }
        else if (this.estimatedScore < o.estimatedScore)
        {
            return -1;
        }

        return 0;
    }
}
