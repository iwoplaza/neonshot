package iwoplaza.meatengine.world;

import iwoplaza.meatengine.pathfinding.IGraph;
import iwoplaza.meatengine.pathfinding.PathfindingPool;
import org.joml.Vector2ic;

import java.util.Collection;
import java.util.List;

public interface IWorld extends IGraph<Vector2ic>
{
    void spawnEntity(Entity entity);

    boolean canTraverseTo(Vector2ic tileLocation, boolean includeEntities);

    Collection<Entity> getEntities();

    List<IPlayerEntity> getPlayers();

    PathfindingPool getPathfindingPool();
}
