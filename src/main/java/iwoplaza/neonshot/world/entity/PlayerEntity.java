package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.neonshot.Direction;
import org.joml.Vector2ic;

public class PlayerEntity extends SlidingEntity implements IPlayerEntity
{

    private static final int MOVE_WINDUP_DURATION = 2;
    private static final int MOVE_DURATION = 6;

    private int moveWindup = 0;
    /**
     * Describes what the entity wants to do.
     */
    private MoveState moveState = MoveState.IDLE;

    @Override
    public void dispose()
    {

    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

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
            }
        }
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
        SimpleBulletEntity bullet = new SimpleBulletEntity(this.nextPosition, this.direction);
        this.world.spawnEntity(bullet);
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
}
