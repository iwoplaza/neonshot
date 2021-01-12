package iwoplaza.meatengine.graphics.entity;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.world.Entity;

public interface IEntityRenderer<T extends Entity, C extends IEngineContext> extends IDisposable, IAssetConsumer
{
    void render(C context, T entity);
}
