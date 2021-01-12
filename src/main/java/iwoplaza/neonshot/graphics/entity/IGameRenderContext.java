package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;

public interface IGameRenderContext extends IEngineContext
{
    int getTileSize();

    RendererRegistry<IGameRenderContext> getRendererRegistry();
}
