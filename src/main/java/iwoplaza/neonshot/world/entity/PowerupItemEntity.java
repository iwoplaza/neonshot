package iwoplaza.neonshot.world.entity;

import iwoplaza.neonshot.powerup.Powerup;
import org.joml.Vector2ic;

public class PowerupItemEntity extends ItemEntity
{
    private final Powerup powerup;

    public PowerupItemEntity(Powerup powerup, Vector2ic position)
    {
        super(position);
        this.powerup = powerup;
    }

    public PowerupItemEntity(Powerup powerup, Vector2ic position, int maxLifetime)
    {
        super(position, maxLifetime);
        this.powerup = powerup;
    }

    @Override
    public Vector2ic getTextureFrame()
    {
        return powerup.getTextureFrame();
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void applyEffects(PlayerEntity player)
    {
        this.dead = true;
        player.addPowerup(this.powerup);
    }
}
