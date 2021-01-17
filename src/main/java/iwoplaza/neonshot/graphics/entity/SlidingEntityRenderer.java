package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.SlidingEntity;
import org.joml.Vector2f;

public abstract class SlidingEntityRenderer<E extends SlidingEntity> implements IGameEntityRenderer<E>
{
    protected Vector2f getSlidingPosition(E entity, float partialTicks)
    {
        Vector2f position = new Vector2f(entity.getNextPosition());
        int moveCooldown = entity.getMoveCooldown();
        if (moveCooldown > 0)
        {
            float moveProgress = 1 - ((entity.getMoveDuration() - moveCooldown) + partialTicks) / entity.getMoveDuration();

            moveProgress *= moveProgress;

            position.lerp(new Vector2f(entity.getPrevPosition()), moveProgress);
        }

        return position;
    }

    public abstract void drawEntity(IGameRenderContext ctx, E entity);

    @Override
    public void render(IGameRenderContext ctx, E entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = this.getSlidingPosition(entity, partialTicks);
        position.mul(tileSize);

        GlStack.translate(position.x, position.y, 0);

        this.drawEntity(ctx, entity);

        GlStack.pop();
    }
}
