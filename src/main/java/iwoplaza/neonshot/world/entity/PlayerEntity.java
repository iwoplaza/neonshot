package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.meatengine.world.World;
import org.joml.Vector2ic;

public class PlayerEntity extends LivingEntity implements IPlayerEntity
{

    private static final int MOVE_WINDUP_DURATION = 2;
    private static final int MOVE_DURATION = 6;
    private static final int SHOOT_DURATION = 8;

    private int shootCooldown = 0;
    private int moveWindup = 0;
    /**
     * Describes what the entity wants to do.
     */
    private MoveState moveState = MoveState.IDLE;

    protected Direction direction = Direction.EAST;

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

        SimpleBulletEntity bullet = new SimpleBulletEntity(this, this.nextPosition, this.direction);
        this.world.spawnEntity(bullet);
        this.shootCooldown = getShootDuration();
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
        return MOVE_DURATION;
    }

    public int getShootDuration()
    {
        return SHOOT_DURATION;
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
    public boolean isHittableFrom(Vector2ic position)
    {
        return position.equals(this.nextPosition);
    }
}
