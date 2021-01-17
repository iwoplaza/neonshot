package iwoplaza.neonshot.world.entity;

import java.util.LinkedList;
import java.util.List;

public abstract class LivingEntity extends SlidingEntity implements IDamageable
{
    protected int currentHealth;

    protected List<IDeathListener> deathListeners = new LinkedList<>();

    public LivingEntity()
    {
        this.currentHealth = this.getMaxHealth();
    }

    public void registerDeathListener(IDeathListener deathListener)
    {
        this.deathListeners.add(deathListener);
    }

    @Override
    public int getHealth()
    {
        return currentHealth;
    }

    public void onKilled()
    {
        this.deathListeners.forEach(l -> l.onDeath(this));
    }

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
