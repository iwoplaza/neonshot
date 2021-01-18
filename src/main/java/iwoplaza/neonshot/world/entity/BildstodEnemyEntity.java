package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.pathfinding.PathfindingActor;
import iwoplaza.meatengine.world.IWorld;
import org.joml.Vector2ic;

public class BildstodEnemyEntity extends EnemyEntity implements IDamageSource
{
    /**
     * The time after spawning it has to wait for before performing any action.
     */
    private static final int SPAWN_WAIT = 20;
    private static final int MOVE_DURATION = 5;
    private static final int WAIT_DURATION = 10;
    private static final int ATTACK_DURATION = 30;
    private static final int DAMAGE = 10;

    protected int waitCooldown = SPAWN_WAIT;
    protected int attackCooldown = 0;
    protected Direction moveDirection = Direction.NORTH;
    protected int stepsToTake = 0;

    public BildstodEnemyEntity(Vector2ic position)
    {
        this.setPosition(position);
    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (attackCooldown > 0)
        {
            attackCooldown--;
        }

        if (this.waitCooldown > 0)
        {
            this.waitCooldown--;
        }
        else if (this.moveCooldown > 0)
        {
            this.moveCooldown--;
        }
        else
        {
            this.performActions();
        }
    }

    @Override
    public void dispose()
    {

    }

    private Direction getDirectionToTarget()
    {
        Direction direction = Direction.WEST;

        Vector2ic targetPosition = this.target.getPosition();
        boolean horizontally = Math.abs(targetPosition.x() - this.nextPosition.x) > Math.abs(targetPosition.y() - this.nextPosition.y);
        if (horizontally)
        {
            if (targetPosition.x() < this.nextPosition.x)
                direction = Direction.WEST;
            else
                direction = Direction.EAST;
        }
        else
        {
            if (targetPosition.y() < this.nextPosition.y)
                direction = Direction.SOUTH;
            else
                direction = Direction.NORTH;
        }

        return direction;
    }

    protected void performActions()
    {
        this.prevPosition.set(this.nextPosition);

        if (attackCooldown == 0)
        {
            if (target.getPosition().gridDistance(this.nextPosition) <= 1)
            {
                target.inflictDamage(this, DAMAGE);
                this.attackCooldown = ATTACK_DURATION;
            }
        }

        boolean shouldReevaluate = this.stepsToTake == 0;

        if (!shouldReevaluate)
        {
            shouldReevaluate = !this.moveStep(moveDirection);
        }

        if (shouldReevaluate)
        {
            this.moveDirection = getDirectionToTarget();
            this.waitCooldown = WAIT_DURATION;

            if (this.moveDirection.isHorizontal())
            {
                this.stepsToTake = Math.abs(this.target.getPosition().x() - this.nextPosition.x);
            }
            else
            {
                this.stepsToTake = Math.abs(this.target.getPosition().y() - this.nextPosition.y);
            }
        }
        else
        {
            this.moveCooldown = getMoveDuration();
            this.stepsToTake--;
        }
    }

    @Override
    public void onSpawnedIn(IWorld world)
    {
        super.onSpawnedIn(world);
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return this.nextPosition.equals(tileLocation);
    }

    @Override
    public boolean isHittableFrom(Vector2ic position)
    {
        return this.nextPosition.equals(position);
    }

    @Override
    public int getMoveDuration()
    {
        return MOVE_DURATION;
    }

    @Override
    public int getMaxHealth()
    {
        return 20;
    }
}
