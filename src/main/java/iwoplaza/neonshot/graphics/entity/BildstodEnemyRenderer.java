package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.BildstodEnemyEntity;
import iwoplaza.neonshot.world.entity.SentryEnemyEntity;

import java.io.IOException;

public class BildstodEnemyRenderer extends EnemyRenderer<BildstodEnemyEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public BildstodEnemyRenderer()
    {
        super();
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/bildstod.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    protected float getSlideEasing(BildstodEnemyEntity entity, float t)
    {
        return t;
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void drawEntity(IGameRenderContext ctx, BildstodEnemyEntity entity)
    {
        this.applyDamageOverlayColor(ctx, entity, this.sprite);

        if (this.isVisible(ctx, entity))
        {
            this.sprite.draw();
        }

        super.drawEntity(ctx, entity);
    }
}
