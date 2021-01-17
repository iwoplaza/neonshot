package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;

import java.util.List;

public abstract class EnemyEntity extends LivingEntity
{
    protected IPlayerEntity target;

    public EnemyEntity()
    {
        super();
    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.target == null)
        {
            this.findTarget();
        }
    }

    protected void findTarget()
    {
        List<IPlayerEntity> players = this.world.getPlayers();
        if (players.size() > 0)
        {
            this.target = players.get(0);
        }
    }

    @Override
    public void onKilled()
    {
        super.onKilled();
        this.dead = true;
    }
}
