package iwoplaza.meatengine.world;

import org.joml.Vector2i;

import java.util.Collection;

public interface IWorld
{
    void spawnEntity(Entity entity);

    boolean canTraverseTo(Vector2i tileLocation);

    Collection<Entity> getEntities();
}
