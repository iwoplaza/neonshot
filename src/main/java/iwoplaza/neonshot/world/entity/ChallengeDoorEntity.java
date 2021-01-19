package iwoplaza.neonshot.world.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import org.joml.Vector2ic;

public class ChallengeDoorEntity extends TileboundEntity
{
    private static final int OPEN_DURATION = 10;

    private Direction direction;
    private int openProgress = 0;
    private boolean opening = false;

    public ChallengeDoorEntity(Vector2ic position, Direction direction)
    {
        this.setPosition(position);
        this.direction = direction;
    }

    @Override
    public void dispose()
    {

    }

    public void open()
    {
        if (this.opening)
            return;

        this.openProgress = 0;
        this.opening = true;
    }

    @Override
    public void update(IEngineContext context)
    {
        super.update(context);

        if (this.opening)
        {
            this.openProgress++;
            if (this.openProgress >= this.getOpenDuration())
            {
                this.dead = true;
            }
        }
    }

    public boolean isOpening()
    {
        return opening;
    }

    public int getOpenProgress()
    {
        return openProgress;
    }

    public int getOpenDuration()
    {
        return OPEN_DURATION;
    }

    public Direction getDirection()
    {
        return direction;
    }

    @Override
    public boolean doesOccupyPosition(Vector2ic tileLocation)
    {
        return this.nextPosition.equals(tileLocation);
    }
}
