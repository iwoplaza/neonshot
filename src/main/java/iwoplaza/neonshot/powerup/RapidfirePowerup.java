package iwoplaza.neonshot.powerup;

import org.joml.Vector2ic;

public class RapidfirePowerup extends Powerup
{
    public RapidfirePowerup(String key, String unlocalizedName, Vector2ic textureFrame)
    {
        super(key, unlocalizedName, textureFrame);
    }

    @Override
    public int alterShootDuration(int baseSpeed)
    {
        return (int) (baseSpeed * 0.7f);
    }
}
