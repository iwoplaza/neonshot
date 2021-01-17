package iwoplaza.neonshot.world;

import iwoplaza.neonshot.world.entity.LivingEntity;
import org.joml.Vector2ic;

@FunctionalInterface
public interface IChallengeEntityFactory
{
    LivingEntity create(Vector2ic position);
}
