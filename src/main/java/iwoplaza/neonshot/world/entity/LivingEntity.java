package iwoplaza.neonshot.world.entity;

public abstract class LivingEntity extends SlidingEntity implements IDamageable
{
    protected int currentHealth;

    public LivingEntity()
    {
        this.currentHealth = this.getMaxHealth();
    }

    @Override
    public int getHealth()
    {
        return currentHealth;
    }

    public abstract void onKilled();

    @Override
    public void inflictDamage(IDamageSource source, int amount)
    {
        this.currentHealth -= amount;
        if (this.currentHealth <= 0)
        {
            onKilled();
            this.currentHealth = 0;
        }
    }
}
