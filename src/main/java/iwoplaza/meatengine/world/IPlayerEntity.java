package iwoplaza.meatengine.world;

import iwoplaza.neonshot.world.entity.IDamageable;
import org.joml.Vector2ic;

public interface IPlayerEntity extends IDamageable
{
    Vector2ic getPosition();
}
