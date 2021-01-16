package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.pathfinding.IPathfindingActor;
import iwoplaza.meatengine.pathfinding.PathfindingActor;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.meatengine.world.IWorld;
import org.joml.Vector2ic;

import java.util.List;

public class PawnEnemyEntity extends EnemyEntity
{
    private static final int MOVE_DURATION = 10;
    private static final int WAIT_DURATION = 5;

    protected PathfindingActor pathfindingActor;

    protected int waitCooldown = 0;

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
            this.onMoving();
        }

        this.pathfindingActor.update(context);

        List<IPlayerEntity> players = this.world.getPlayers();
        if (players.size() > 0)
        {
            IPlayerEntity player = players.get(0);
            this.pathfindingActor.setTarget(player.getPosition());
        }
    }

    protected void onMoving()
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

            // Leaving the node, since we don't change the position.
            return false;
        });
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return this.nextPosition.equals(tileLocation);
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
