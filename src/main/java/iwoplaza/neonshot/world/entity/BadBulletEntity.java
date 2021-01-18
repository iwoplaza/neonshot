package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.world.Entity;
import org.joml.Vector2ic;

public class BadBulletEntity extends BulletEntity
{
    private final int damage;

    public BadBulletEntity(Entity owner, Vector2ic position, Direction direction, int damage)
    {
        super(owner, position, direction, 5);
        this.damage = damage;
    }

    @Override
    protected int getDamage()
    {
        return damage;
    }

    @Override
    public boolean canDamage(IDamageable damageable)
    {
        return super.canDamage(damageable) && damageable instanceof PlayerEntity;
    }

    @Override
    public int getSpriteFrame()
    {
        return 1;
    }
}
