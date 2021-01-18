package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2ic;

public class Camera implements ICamera<IEngineContext, IGameRenderContext>
{
    private IPlayerEntity entityToFollow;
    private Vector2f prevPosition = new Vector2f();
    private Vector2f nextPosition = new Vector2f();

    public Camera()
    {

    }

    public void snapToEndpoint()
    {
        Vector2fc targetPosition = getTargetPosition();
        this.prevPosition.set(targetPosition);
        this.nextPosition.set(targetPosition);
    }

    public void update(IEngineContext context)
    {
        if (this.entityToFollow != null)
        {
            this.prevPosition.set(this.nextPosition);
            this.nextPosition.set(getTargetPosition());
            this.nextPosition.lerp(this.prevPosition, 0.5f);
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

    private Vector2fc getTargetPosition()
    {
        return new Vector2f(entityToFollow.getPosition());
    }
}
