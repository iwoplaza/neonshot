package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import iwoplaza.neonshot.powerup.Powerups;

import java.util.List;
import java.util.Random;

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

        Random random = new Random();
        if (random.nextFloat() < 0.5f)
        {
            spawnRandomDrop();
        }
    }

    private void spawnRandomDrop()
    {
        Random random = new Random();

        ItemEntity itemToSpawn;
        if (random.nextFloat() < 0.5)
        {
            itemToSpawn = new BandageEntity(this.nextPosition, 500);
        }
        else
        {
            // Spawning a powerup
            int choice = random.nextInt(Powerups.POWERUPS.size());
            itemToSpawn = new PowerupItemEntity(Powerups.POWERUPS.get(choice), this.nextPosition, 500);
        }

        this.world.spawnEntity(itemToSpawn);
    }
}
