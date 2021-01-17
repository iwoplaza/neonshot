package iwoplaza.meatengine.pathfinding;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IWorld;
import iwoplaza.meatengine.Direction;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.List;

public class PathfindingActor implements IPathfindingActor, ICostScorer<Vector2ic>
{
    private final IWorld world;
    private final AStarSolver<Vector2ic> aStarSolver;
    private final PathfindingPool pathfindingPool;
    private final int recalculateInterval;

    private boolean requestPending = false;
    private final Vector2i position;
    private final Vector2i target;
    private List<Vector2ic> path;
    private int recalculateCountdown = 0;

    public PathfindingActor(IWorld world, Vector2ic position, int recalculateInterval)
    {
        this.position = new Vector2i(position);
        this.target = new Vector2i(position);
        this.recalculateInterval = recalculateInterval;
        this.world = world;
        this.aStarSolver = new AStarSolver<>(world);
        this.pathfindingPool = world.getPathfindingPool();
    }

    public PathfindingActor(IWorld world, Vector2ic position)
    {
        this(world, position, 20);
    }

    public void update(IEngineContext engine)
    {
        recalculateCountdown--;
        if (recalculateCountdown <= 0)
        {
            recalculateCountdown = recalculateInterval;
            requestPath(position, target);
        }
    }

    @Override
    public void setPosition(Vector2ic position)
    {
        this.position.set(position);
    }

    @Override
    public void setTarget(Vector2ic to)
    {
        this.target.set(to);
    }

    @Override
    public Vector2ic getPosition()
    {
        return this.position;
    }

    @Override
    public Vector2ic getTarget()
    {
        return this.target;
    }

    @Override
    public synchronized Direction getDirection()
    {
        if (path == null || path.size() == 0)
            return null;

        Vector2i next = new Vector2i(this.path.get(0));

        while (next.equals(position))
        {
            this.removeNode();
            next.set(this.path.get(0));
        }

        Direction direction = Direction.fromVector(next.sub(this.position));

        if (direction == null)
        {
            throw new IllegalStateException(String.format("Tried to move from %s to %s.", this.position, next));
        }

        return direction;
    }

    public synchronized void fetchDirection(IFetchCallback fetchCallback)
    {
        if (fetchCallback.perform(getDirection()))
        {
            if (this.path != null)
            {
                this.removeNode();
            }
        }
    }

    private void removeNode()
    {
        if (this.path == null || this.path.size() == 0)
        {
            return;
        }

        this.path.remove(0);
    }

    @Override
    public synchronized List<Vector2ic> getPath()
    {
        return this.path;
    }

    @Override
    public boolean isRequestPending()
    {
        return this.requestPending;
    }

    /**
     * This doesn't guarantee that a path will be requested.
     * The request will be ignored if the last request is still pending.
     */
    private synchronized void requestPath(Vector2ic from, Vector2ic to)
    {
        if (this.requestPending)
            return;

        this.pathfindingPool.requestPath(this, from, to);
        this.requestPending = true;
    }

    @Override
    public synchronized void computePath(Vector2ic from, Vector2ic to)
    {
        this.requestPending = false;

        try
        {
            this.path = this.aStarSolver.findRoute(from, to, this, this);
            // Removing the first node.
            this.removeNode();
        }
        catch(PathNotFoundException e)
        {
            this.path = null;
            e.printStackTrace();
        }
    }

    @Override
    public float computeCost(Vector2ic from, Vector2ic to)
    {
        if (!this.world.canTraverseTo(to, false))
        {
            return Float.POSITIVE_INFINITY;
        }

        return Vector2f.distance(from.x(), from.y(), to.x(), to.y());
    }

    @FunctionalInterface
    public interface IFetchCallback
    {
        boolean perform(Direction direction);
    }

}
