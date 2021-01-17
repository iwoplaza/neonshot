package iwoplaza.neonshot.world.entity;

public abstract class EnemyEntity extends LivingEntity
{
    public EnemyEntity()
    {
        super();
    }

    @Override
    public void onKilled()
    {
        this.dead = true;
    }
}
