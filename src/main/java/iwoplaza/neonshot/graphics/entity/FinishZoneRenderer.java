package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.FinishZoneEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class FinishZoneRenderer implements IGameEntityRenderer<FinishZoneEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public FinishZoneRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/finish_zone.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void render(IGameRenderContext ctx, FinishZoneEntity entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = new Vector2f(entity.getPrevPosition());
        Vector2f nextPosition = new Vector2f(entity.getNextPosition());

        position.lerp(nextPosition, partialTicks);
        position.mul(tileSize);

        GlStack.translate(position.x, position.y, 0);

        float lifetime = entity.getLifetime() + partialTicks;
        GlStack.translate(0, (float) (Math.sin(lifetime * 0.15f) * 3.0f + 3.0f), 0);

        this.sprite.setFrameX((entity.getLifetime() / 2) % 6);
        this.sprite.setFrameY(0);
        this.sprite.draw();

        GlStack.pop();
    }
}
