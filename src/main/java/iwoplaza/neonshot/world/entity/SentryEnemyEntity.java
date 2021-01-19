package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class SentryEnemyEntity extends EnemyEntity implements IDamageSource
{
    /**
     * The time after spawning it has to wait for before performing any action.
     */
    private static final int SPAWN_WAIT = 60;
    private static final int MAX_SHOOT_COOLDOWN = 40;
    private static final int DAMAGE = 10;

    protected int shootCooldown = SPAWN_WAIT;

    public SentryEnemyEntity(Vector2ic position)
    {
        this.setPosition(position);
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.shootCooldown > 0)
        {
            this.shootCooldown--;
            if (this.shootCooldown == 0)
            {
                this.performShooting();
                this.shootCooldown = MAX_SHOOT_COOLDOWN;
            }
        }
    }

    private void performShooting()
    {
        for (Direction direction : Direction.values())
        {
            Vector2i position = new Vector2i(this.nextPosition);
            BadBulletEntity bullet = new BadBulletEntity(this, position, direction, DAMAGE);
            this.world.spawnEntity(bullet);
        }
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return this.nextPosition.equals(tileLocation);
    }

    @Override
    public int getMaxHealth()
    {
        return 30;
    }

    @Override
    public boolean isHittableFrom(Vector2ic position)
    {
        return this.nextPosition.equals(position);
    }

    @Override
    public int getMoveDuration()
    {
        return 10;
    }

    public int getShootCooldown()
    {
        return shootCooldown;
    }

    public static int getMaxShootCooldown()
    {
        return MAX_SHOOT_COOLDOWN;
    }
}
