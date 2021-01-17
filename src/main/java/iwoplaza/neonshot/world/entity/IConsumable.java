package iwoplaza.neonshot.world.entity;

import org.joml.Vector2ic;

public interface IConsumable
{
    boolean isConsumableFrom(Vector2ic position);

    void applyEffects(PlayerEntity player);
}
