package iwoplaza.meatengine.graphics;

import iwoplaza.meatengine.IEngineContext;

public interface ICamera<C extends IEngineContext, R extends IGameRenderContext>
{
    void update(C context);

    void applyTransform(R context);
}
