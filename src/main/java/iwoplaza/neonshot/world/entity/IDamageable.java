package iwoplaza.neonshot.world.entity;

public interface IDamageable
{
    int getHealth();

    int getMaxHealth();

    void inflictDamage(IDamageSource source, int amount);
}
