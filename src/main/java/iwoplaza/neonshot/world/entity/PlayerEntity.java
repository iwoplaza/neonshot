package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.tile.TileLocation;
import iwoplaza.neonshot.Direction;
import org.joml.Vector2i;

public class PlayerEntity extends DirectionalEntity
{

    private static final int MOVE_WINDUP_DURATION = 2;
    private static final int MOVE_DURATION = 6;

    /**
     * Describes what the player wants to do.
     */
    private MoveState moveState = MoveState.IDLE;
    private int moveWindup = 0;
    private int moveCooldown = 0;

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
                if (this.moveCooldown > 0)
                {
                    this.moveCooldown--;
                }
                break;
            case WINDUP:
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
                break;
            case MOVING:
                if (this.moveCooldown > 0) {
                    this.moveCooldown--;
                }
                else {
                    if (this.moveStep(this.direction))
                    {
                        this.moveCooldown = getMoveDuration();
                    }
                }
                break;
        }
    }

    private boolean moveStep(Direction direction)
    {
        this.prevPosition.set(this.nextPosition);
        Vector2i nextPosition = new Vector2i(this.prevPosition);
        nextPosition.add(direction.getAsVector());

        if (this.world.canTraverseTo(nextPosition))
        {
            this.nextPosition.set(nextPosition);
            return true;
        }

        return false;
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
                this.moveWindup = MOVE_WINDUP_DURATION;
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

    public void shoot()
    {
        SimpleBulletEntity bullet = new SimpleBulletEntity(this.nextPosition, this.direction);
        this.world.spawnEntity(bullet);
    }

    public MoveState getMoveState()
    {
        return moveState;
    }

    public int getMoveCooldown()
    {
        return moveCooldown;
    }

    public int getMoveDuration()
    {
        return MOVE_DURATION;
    }

}
