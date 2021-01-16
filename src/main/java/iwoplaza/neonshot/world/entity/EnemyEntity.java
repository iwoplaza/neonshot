package iwoplaza.neonshot.world.entity;

public abstract class EnemyEntity extends SlidingEntity implements IDamageable
{
    protected int currentHealth;

    public EnemyEntity()
    {
        this.currentHealth = this.getMaxHealth();
    }

    @Override
    public int getHealth()
    {
        return currentHealth;
    }

    @Override
    public void inflictDamage(IDamageSource source, int amount)
    {
        this.currentHealth -= amount;
        if (this.currentHealth <= 0)
        {
            this.dead = true;
            this.currentHealth = 0;
        }
    }
}
