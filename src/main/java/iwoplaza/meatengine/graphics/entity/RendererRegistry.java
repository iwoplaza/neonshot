package iwoplaza.meatengine.graphics.entity;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.world.Entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RendererRegistry<C extends IEngineContext> implements IDisposable, IAssetConsumer
{
    private final Map<Class<? extends Entity>, IEntityRenderer<?, C>> entityRendererMap;

    public RendererRegistry()
    {
        this.entityRendererMap = new HashMap<>();
    }

    public <T extends Entity> void registerRenderer(Class<T> entityType, IEntityRenderer<T, C> renderer)
    {
        this.entityRendererMap.put(entityType, renderer);
    }

    public IEntityRenderer<?, C> getRenderer(Entity entity)
    {
        return this.entityRendererMap.get(entity.getClass());
    }

    public <T extends Entity> void renderEntity(C context, T entity)
    {
        @SuppressWarnings("unchecked")
        IEntityRenderer<T, C> renderer = (IEntityRenderer<T, C>) getRenderer(entity);
        renderer.render(context, entity);
    }

    public void registerAssets(IAssetLoader loader) throws IOException
    {
        for (IEntityRenderer<?, C> r : this.entityRendererMap.values())
        {
            r.registerAssets(loader);
        }
    }

    @Override
    public void dispose()
    {
        this.entityRendererMap.values().forEach(IDisposable::dispose);
    }
}
