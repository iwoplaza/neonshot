package iwoplaza.neonshot.world.entity;

import org.joml.Vector2ic;

public interface IDamageable
{
    int getHealth();

    int getMaxHealth();

    void inflictDamage(IDamageSource source, int amount);

    boolean isHittableFrom(Vector2ic position);
}
