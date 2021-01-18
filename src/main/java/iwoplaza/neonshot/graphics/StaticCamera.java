package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.ICamera;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.world.IPlayerEntity;
import org.joml.Vector2f;
import org.joml.Vector2fc;

public class StaticCamera implements ICamera<IEngineContext, IGameRenderContext>
{
    private Vector2f position = new Vector2f();

    public StaticCamera()
    {

    }

    public void update(IEngineContext context)
    {
    }

    @Override
    public void applyTransform(IGameRenderContext context)
    {
        Vector2f pos = new Vector2f(this.position);
        pos.mul(context.getTileSize());
        pos.floor();

        GlStack.scale(2);
        GlStack.translate(-pos.x, -pos.y, 0);
    }

    public void setPosition(int x, int y)
    {
        this.position.set(x, y);
    }
}
