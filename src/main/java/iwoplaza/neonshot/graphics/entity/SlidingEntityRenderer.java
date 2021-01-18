package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.SlidingEntity;
import org.joml.Vector2f;

public abstract class SlidingEntityRenderer<E extends SlidingEntity> implements IGameEntityRenderer<E>
{
    protected Vector2f getSlidingPosition(E entity, float partialTicks)
    {
        final Vector2f position = new Vector2f(entity.getNextPosition());
        final int moveCooldown = entity.getMoveCooldown();
        if (moveCooldown > 0)
        {
            final float moveProgress = ((entity.getMoveDuration() - moveCooldown) + partialTicks) / entity.getMoveDuration();
            float t = getSlideEasing(entity, moveProgress);

            position.lerp(new Vector2f(entity.getPrevPosition()), 1 - t);
        }

        return position;
    }

    protected float getSlideEasing(E entity, float t)
    {
        t = 1 - t;
        t *= t;
        return 1 - t;
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
