package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.ItemEntity;
import org.joml.Vector2f;
import org.joml.Vector2ic;

import java.io.IOException;

public class ItemRenderer<E extends ItemEntity> implements IGameEntityRenderer<E>
{
    private final static int FLICKER_THRESHOLD = 150;

    private TextureAsset texture;
    private Sprite sprite;

    public ItemRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/items.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    public boolean isVisible(IGameRenderContext context, E entity)
    {
        int maxLifetime = entity.getMaxLifetime();
        int lifetime = entity.getLifetime();
        if (maxLifetime - lifetime < FLICKER_THRESHOLD)
        {
            return (lifetime/4) % 2 == 0;
        }

        return true;
    }

    @Override
    public void render(IGameRenderContext ctx, E entity)
    {
        if (!isVisible(ctx, entity))
        {
            return;
        }

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

        Vector2ic frame = entity.getTextureFrame();
        this.sprite.setFrameX(frame.x());
        this.sprite.setFrameY(frame.y());
        this.sprite.draw();

        GlStack.pop();
    }
}
