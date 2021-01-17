package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.pathfinding.IPathfindingActor;
import iwoplaza.meatengine.pathfinding.PathfindingActor;
import iwoplaza.meatengine.world.IWorld;
import org.joml.Vector2ic;

public class PawnEnemyEntity extends EnemyEntity implements IDamageSource
{
    /**
     * The time after spawning it has to wait for before performing any action.
     */
    private static final int SPAWN_WAIT = 20;
    private static final int MOVE_DURATION = 10;
    private static final int WAIT_DURATION = 10;
    private static final int ATTACK_DURATION = 30;
    private static final int DAMAGE = 10;

    protected PathfindingActor pathfindingActor;

    protected int waitCooldown = SPAWN_WAIT;
    protected int attackCooldown = 0;

    public PawnEnemyEntity(Vector2ic position)
    {
        this.setPosition(position);
    }

    @Override
    public void onSpawnedIn(IWorld world)
    {
        super.onSpawnedIn(world);

        this.pathfindingActor = new PathfindingActor(this.world, this.nextPosition);
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (attackCooldown > 0)
        {
            attackCooldown--;
        }

        if (this.moveCooldown > 0)
        {
            this.moveCooldown--;
            if (this.moveCooldown == 0)
            {
                this.waitCooldown = WAIT_DURATION;
            }
        }
        else if (this.waitCooldown > 0)
        {
            this.waitCooldown--;
        }
        else
        {
            this.performActions();
        }

        if (this.target != null)
        {
            this.pathfindingActor.setTarget(this.target.getPosition());
        }

        this.pathfindingActor.update(context);
    }

    protected void performActions()
    {
        this.prevPosition.set(this.nextPosition);

        this.pathfindingActor.fetchDirection(dir -> {
            if (dir == null)
            {
                return true;
            }

            if (this.moveStep(dir))
            {
                this.pathfindingActor.setPosition(this.nextPosition);
                this.moveCooldown = getMoveDuration();

                // Popping of the node.
                return true;
            }

            if (attackCooldown == 0)
            {
                if (target.getPosition().gridDistance(this.nextPosition) <= 1)
                {
                    target.inflictDamage(this, DAMAGE);
                    this.attackCooldown = ATTACK_DURATION;
                }
            }

            // Leaving the node, since we don't change the position.
            return false;
        });
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

    public IPathfindingActor getPathfindingActor()
    {
        return this.pathfindingActor;
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
