package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.IEngineContext;

import java.util.LinkedList;
import java.util.List;

public abstract class LivingEntity extends SlidingEntity implements IDamageable
{
    protected int currentHealth;
    protected int invincibilityFrames = 0;

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

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.invincibilityFrames > 0)
        {
            this.invincibilityFrames--;
        }
    }

    public void onKilled()
    {
        this.deathListeners.forEach(l -> l.onDeath(this));
    }

    @Override
    public void inflictDamage(IDamageSource source, int amount)
    {
        if (this.invincibilityFrames > 0)
            return;

        this.currentHealth -= amount;
        this.invincibilityFrames = getInvincibilityDuration();

        if (this.currentHealth <= 0)
        {
            onKilled();
            this.currentHealth = 0;
        }
    }

    public int getInvincibilityDuration()
    {
        return 5;
    }

    public int getInvincibilityFrames()
    {
        return invincibilityFrames;
    }
}
