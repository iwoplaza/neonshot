package iwoplaza.neonshot.powerup;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Powerups
{
    public static final Powerup SPEED = new SpeedPowerup("speed", "powerup.speed", new Vector2i(1, 0));
    public static final Powerup RAPIDFIRE = new RapidfirePowerup("rapidfire", "powerup.rapidfire", new Vector2i(2, 0));
    public static final Powerup EXTRA_DAMAGE = new ExtraDamagePowerup("extraDamage", "powerup.extraDamage", new Vector2i(3, 0));

    public static final List<Powerup> POWERUPS = new ArrayList<>();
    static
    {
        POWERUPS.add(SPEED);
        POWERUPS.add(RAPIDFIRE);
        POWERUPS.add(EXTRA_DAMAGE);
    }
}
