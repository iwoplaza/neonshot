package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.graphics.Color;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.graphics.HealthBarRenderer;
import iwoplaza.neonshot.world.entity.EnemyEntity;

public abstract class EnemyRenderer<E extends EnemyEntity> extends SlidingEntityRenderer<E>
{
    private final HealthBarRenderer.HealthBarSpec healthBarSpec;

    public EnemyRenderer()
    {
        this.healthBarSpec = new HealthBarRenderer.HealthBarSpec(
                false,
                new Color(1, 0.2f, 0.2f, 1.0f),
                new Color(1, 0.7f, 0.2f, 1.0f)
        );
    }

    @Override
    public void drawEntity(IGameRenderContext ctx, E entity)
    {
        GlStack.push();

        GlStack.translate((int) (ctx.getTileSize() / 2 - HealthBarRenderer.SMALL_WIDTH / 2), 5, 0);

        HealthBarRenderer.INSTANCE.draw(ctx, entity, this.healthBarSpec);

        GlStack.pop();
    }
}
