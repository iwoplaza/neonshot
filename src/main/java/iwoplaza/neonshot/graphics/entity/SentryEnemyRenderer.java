package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.PawnEnemyEntity;
import iwoplaza.neonshot.world.entity.SentryEnemyEntity;

import java.io.IOException;

public class SentryEnemyRenderer extends EnemyRenderer<SentryEnemyEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public SentryEnemyRenderer()
    {
        super();
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/sentry.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void drawEntity(IGameRenderContext ctx, SentryEnemyEntity entity)
    {
        this.applyDamageOverlayColor(ctx, entity, this.sprite);

        if (this.isVisible(ctx, entity))
        {
            final int frames = 3;

            this.sprite.setFrameX(Math.max(0, frames - entity.getShootCooldown()/4));
            this.sprite.draw();
        }

        super.drawEntity(ctx, entity);
    }
}
