package iwoplaza.neonshot.world.entity;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public class BandageEntity extends ItemEntity
{
    private static final int HEAL_AMOUNT = 30;
    private static final Vector2ic TEXTURE_FRAME = new Vector2i(0, 0);

    public BandageEntity(Vector2ic position, int maxLifetime)
    {
        super(position, maxLifetime);
    }

    public BandageEntity(Vector2ic position)
    {
        super(position);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public Vector2ic getTextureFrame()
    {
        return TEXTURE_FRAME;
    }

    @Override
    public void applyEffects(PlayerEntity player)
    {
        this.dead = true;
        player.heal(HEAL_AMOUNT);
    }
}
