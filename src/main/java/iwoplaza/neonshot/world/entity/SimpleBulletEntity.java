package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.world.Entity;
import org.joml.Vector2ic;

public class SimpleBulletEntity extends BulletEntity
{
    public SimpleBulletEntity(Entity owner, Vector2ic position, Direction direction)
    {
        super(owner, position, direction);
    }

    @Override
    protected int getDamage()
    {
        return 10;
    }
}
