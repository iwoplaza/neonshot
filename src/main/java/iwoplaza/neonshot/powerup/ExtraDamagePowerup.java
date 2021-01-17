package iwoplaza.neonshot.powerup;

import org.joml.Vector2ic;

public class ExtraDamagePowerup extends Powerup
{
    public ExtraDamagePowerup(String key, String unlocalizedName, Vector2ic textureFrame)
    {
        super(key, unlocalizedName, textureFrame);
    }

    @Override
    public int alterBulletDamage(int baseDamage)
    {
        return (int) (baseDamage * 1.5f);
    }
}
