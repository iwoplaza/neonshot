package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.world.Entity;
import org.joml.Vector2ic;

public class SimpleBulletEntity extends BulletEntity
{
    private final int damage;

    public SimpleBulletEntity(Entity owner, Vector2ic position, Direction direction, int damage)
    {
        super(owner, position, direction);
        this.damage = damage;
    }

    @Override
    protected int getDamage()
    {
        return damage;
    }
}
