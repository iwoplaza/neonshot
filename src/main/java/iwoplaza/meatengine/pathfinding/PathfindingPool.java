package iwoplaza.meatengine.pathfinding;

import org.joml.Vector2ic;

import java.util.LinkedList;
import java.util.Queue;

public class PathfindingPool extends Thread implements IPathfindingPool
{

    private Queue<PathfindingRequest> requests = new LinkedList<>();
    private boolean running = true;

    public PathfindingPool()
    {
    }

    @Override
    public void requestPath(PathfindingActor actor, Vector2ic from, Vector2ic to)
    {
        synchronized (this)
        {
            // Inserting a request into the queue.
            this.requests.add(new PathfindingRequest(actor, from, to));

            // Notifying the solver that a request has been added.
            notify();
        }
    }

    /**
     * Function to be called by solver thread
     */
    private void solveRequests() throws InterruptedException
    {
        while (true)
        {
            synchronized (this)
            {
                while (requests.isEmpty() && running)
                    wait();

                if (!running)
                {
                    // Stopping the thread.
                    return;
                }

                // Retrieving the next pathfinding request.
                PathfindingRequest request = requests.remove();

                System.out.println(String.format("Calculating a pathfinding request: %s", request));
                request.actor.computePath(request.from, request.to);
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            this.solveRequests();
        }
        catch (InterruptedException e)
        {
            // This pool has been discarded.
        }
    }

    public void stopRunning()
    {
        this.running = false;
        this.interrupt();
    }

    private static class PathfindingRequest
    {
        private final PathfindingActor actor;
        private final Vector2ic from;
        private final Vector2ic to;

        public PathfindingRequest(PathfindingActor actor, Vector2ic from, Vector2ic to)
        {
            this.actor = actor;
            this.from = from;
            this.to = to;
        }
    }
}
