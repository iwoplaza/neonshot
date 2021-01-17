package iwoplaza.neonshot.powerup;

import org.joml.Vector2ic;

public class Powerup
{
    private final String key;
    private final String unlocalizedName;
    private final Vector2ic textureFrame;

    public Powerup(String key, String unlocalizedName, Vector2ic textureFrame)
    {
        this.key = key;
        this.unlocalizedName = unlocalizedName;
        this.textureFrame = textureFrame;
    }

    public String getKey()
    {
        return key;
    }

    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    public Vector2ic getTextureFrame()
    {
        return textureFrame;
    }

    public int alterMoveDuration(int baseSpeed)
    {
        return baseSpeed;
    }

    public int alterShootDuration(int baseSpeed)
    {
        return baseSpeed;
    }

    public int alterBulletDamage(int baseDamage)
    {
        return baseDamage;
    }
}
