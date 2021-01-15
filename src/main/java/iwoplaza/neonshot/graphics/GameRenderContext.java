package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;

public class GameRenderContext implements IGameRenderContext
{

    private final RendererRegistry<IGameRenderContext> rendererRegistry;
    private float updateInterval;
    private float deltaTime;
    private float partialTicks;

    public GameRenderContext(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.rendererRegistry = rendererRegistry;
    }

    public void update(IEngineContext context)
    {
        this.updateInterval = context.getUpdateInterval();
        this.deltaTime = context.getDeltaTime();
        this.partialTicks = context.getPartialTicks();
    }

    @Override
    public int getTileSize()
    {
        return 32;
    }

    @Override
    public RendererRegistry<IGameRenderContext> getRendererRegistry()
    {
        return rendererRegistry;
    }

    @Override
    public float getUpdateInterval()
    {
        return updateInterval;
    }

    @Override
    public float getDeltaTime()
    {
        return deltaTime;
    }

    @Override
    public float getPartialTicks()
    {
        return partialTicks;
    }
}
