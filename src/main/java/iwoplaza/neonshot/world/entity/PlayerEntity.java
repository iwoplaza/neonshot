package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.powerup.Powerup;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends LivingEntity implements IPlayerEntity
{

    private static final int MOVE_WINDUP_DURATION = 2;
    private static final int MOVE_DURATION = 6;
    private static final int SHOOT_DURATION = 8;
    private static final int BULLET_DAMAGE = 10;

    private int shootCooldown = 0;
    private int moveWindup = 0;
    /**
     * Describes what the entity wants to do.
     */
    private MoveState moveState = MoveState.IDLE;

    protected Direction direction = Direction.EAST;

    private List<Powerup> powerups = new ArrayList<>();

    @Override
    public void dispose()
    {

    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.shootCooldown > 0)
            this.shootCooldown--;

        switch (moveState)
        {
            case IDLE:
                onIdle();
                break;
            case WINDUP:
                onWindup();
                break;
            case MOVING:
                onMoving();
                break;
        }
    }

    protected void onIdle()
    {
        if (this.moveCooldown > 0)
        {
            this.moveCooldown--;
        }
    }

    protected void onWindup()
    {
        if (this.moveCooldown > 0)
        {
            this.moveCooldown--;
        }
        else if (this.moveWindup > 0)
        {
            this.moveWindup--;
        }
        else
        {
            this.setMoveState(MoveState.MOVING);
        }
    }

    protected void onMoving()
    {
        if (this.moveCooldown > 0)
        {
            this.moveCooldown--;
        }
        else
        {
            if (this.moveStep(this.direction))
            {
                this.moveCooldown = getMoveDuration();
                this.onMoved();
            }
        }
    }

    private void onMoved()
    {
        World world = (World) this.world;
        world.getChallengeRooms().forEach(r -> {
            if (this.nextPosition.equals(r.getEntrance()))
            {
                r.activate();
            }
        });

        world.getEntities().forEach(e -> {
            if (e instanceof IConsumable)
            {
                IConsumable consumable = ((IConsumable) e);
                if (consumable.isConsumableFrom(this.nextPosition))
                {
                    consumable.applyEffects(this);
                }
            }
        });
    }

    private void setMoveState(MoveState moveState)
    {
        // Side-effects
        switch(moveState)
        {
            case IDLE:
                break;
            case MOVING:
                this.moveCooldown = 0;
                break;
            case WINDUP:
                this.moveWindup = getMoveWindupDuration();
                break;
        }

        this.moveState = moveState;
    }

    public void setMoveDirection(Direction direction)
    {
        if (this.moveState == MoveState.IDLE)
        {
            if (direction != null)
            {
                // We got a new direction
                this.direction = direction;
                this.setMoveState(MoveState.WINDUP);
            }
        }
        else
        {
            if (direction == null)
            {
                this.setMoveState(MoveState.IDLE);
            }
            else
            {
                if (direction == this.direction)
                {
                    return;
                }
                // We got a new direction
                this.direction = direction;
                this.setMoveState(MoveState.WINDUP);
            }
        }
    }

    public MoveState getMoveState()
    {
        return moveState;
    }

    public void shoot()
    {
        if (this.shootCooldown > 0)
            return;

        SimpleBulletEntity bullet = new SimpleBulletEntity(this, this.nextPosition, this.direction, this.getBulletDamage());
        this.world.spawnEntity(bullet);
        this.shootCooldown = getShootDuration();
    }

    public void heal(int healAmount)
    {
        if (healAmount < 0)
        {
            return;
        }

        this.currentHealth += healAmount;
        if (this.currentHealth > this.getMaxHealth())
        {
            this.currentHealth = this.getMaxHealth();
        }
    }

    public void addPowerup(Powerup powerup)
    {
        if (powerups.contains(powerup))
        {
            return;
        }

        this.powerups.add(powerup);
    }

    @Override
    public void onKilled()
    {
        super.onKilled();
    }

    @Override
    public int getMaxHealth()
    {
        return 100;
    }

    public int getMoveWindupDuration()
    {
        return MOVE_WINDUP_DURATION;
    }

    @Override
    public int getMoveDuration()
    {
        int duration = MOVE_DURATION;

        for (Powerup powerup : this.powerups)
        {
            duration = powerup.alterMoveDuration(duration);
        }

        return duration;
    }

    public int getShootDuration()
    {
        int duration = SHOOT_DURATION;

        for (Powerup powerup : this.powerups)
        {
            duration = powerup.alterShootDuration(duration);
        }

        return duration;
    }

    public int getBulletDamage()
    {
        int damage = BULLET_DAMAGE;

        for (Powerup powerup : this.powerups)
        {
            damage = powerup.alterBulletDamage(damage);
        }

        return damage;
    }


    public int getShootCooldown()
    {
        return shootCooldown;
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return this.nextPosition.equals(tileLocation);
    }

    @Override
    public Vector2ic getPosition()
    {
        return this.nextPosition;
    }

    public Direction getDirection()
    {
        return direction;
    }

    @Override
    public int getInvincibilityDuration()
    {
        return 30;
    }

    public List<Powerup> getPowerups()
    {
        return powerups;
    }

    @Override
    public boolean isHittableFrom(Vector2ic position)
    {
        return position.equals(this.nextPosition);
    }
}
