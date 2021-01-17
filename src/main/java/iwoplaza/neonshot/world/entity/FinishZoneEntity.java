package iwoplaza.neonshot.world.entity;

import org.joml.Vector2ic;

public class FinishZoneEntity extends TileboundEntity implements IConsumable
{
    private final IFinishCallback finishCallback;

    public FinishZoneEntity(Vector2ic position, IFinishCallback finishCallback)
    {
        this.setPosition(position);
        this.finishCallback = finishCallback;
    }

    @Override
    public void dispose()
    {

    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return false;
    }

    @Override
    public boolean isConsumableFrom(Vector2ic position)
    {
        return position.equals(this.nextPosition);
    }

    @Override
    public void applyEffects(PlayerEntity player)
    {
        this.finishCallback.finish();
        this.dead = true;
    }

    public interface IFinishCallback
    {
        void finish();
    }
}
