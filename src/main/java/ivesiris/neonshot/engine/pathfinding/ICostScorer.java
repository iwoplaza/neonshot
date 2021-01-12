package ivesiris.neonshot.engine.pathfinding;

public interface ICostScorer<T extends IGraphNode>
{
    float computeCost(T from, T to);
}
