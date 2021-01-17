package iwoplaza.neonshot.powerup;

import org.joml.Vector2ic;

public class SpeedPowerup extends Powerup
{
    public SpeedPowerup(String key, String unlocalizedName, Vector2ic textureFrame)
    {
        super(key, unlocalizedName, textureFrame);
    }

    @Override
    public int alterMoveDuration(int duration)
    {
        return (int) (duration * 0.9f);
    }
}
