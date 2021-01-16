package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import org.joml.Vector2f;

public class Camera implements ICamera<IEngineContext, IGameRenderContext>
{
    private IPlayerEntity entityToFollow;
    private Vector2f prevPosition = new Vector2f();
    private Vector2f nextPosition = new Vector2f();

    public Camera()
    {

    }

    public void update(IEngineContext context)
    {
        if (this.entityToFollow != null)
        {
            this.prevPosition.set(this.nextPosition);
            Vector2f targetPosition = new Vector2f(entityToFollow.getPosition());
            this.nextPosition = targetPosition.lerp(this.prevPosition, 0.5f);
        }
    }

    @Override
    public void applyTransform(IGameRenderContext context)
    {
        if (this.entityToFollow != null)
        {
            Vector2f pos = new Vector2f(this.prevPosition);
            pos.lerp(this.nextPosition, context.getPartialTicks());
            pos.mul(context.getTileSize());
            pos.floor();
            GlStack.scale(2);
            GlStack.translate(-pos.x, -pos.y, 0);
        }
    }

    public void follow(IPlayerEntity entity)
    {
        this.entityToFollow = entity;
    }
}
