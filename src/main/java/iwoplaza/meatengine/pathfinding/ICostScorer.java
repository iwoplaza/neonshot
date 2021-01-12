package iwoplaza.meatengine.pathfinding;

public interface ICostScorer<T extends IGraphNode>
{
    float computeCost(T from, T to);
}
