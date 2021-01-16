package iwoplaza.meatengine.pathfinding;

public interface ICostScorer<T>
{
    float computeCost(T from, T to);
}
