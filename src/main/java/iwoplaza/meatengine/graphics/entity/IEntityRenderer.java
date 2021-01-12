package iwoplaza.meatengine.graphics.entity;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.Entity;
import iwoplaza.meatengine.assets.IAssetConsumer;

public interface IEntityRenderer<T extends Entity, C extends IEngineContext> extends IDisposable, IAssetConsumer
{
    void render(C context, T entity);
}
