package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Direction;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.BulletEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class BulletRenderer<T extends BulletEntity> implements IGameEntityRenderer<T>
{
    private TextureAsset bulletTexture;
    private Sprite bulletSprite;

    public BulletRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(bulletTexture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/bullet.png")));

        this.bulletSprite = new Sprite(bulletTexture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void render(IGameRenderContext ctx, T entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = new Vector2f(entity.getPrevPosition());
        Vector2f nextPosition = new Vector2f(entity.getNextPosition());

        position.lerp(nextPosition, partialTicks);
        position.mul(tileSize);

        Direction turnDir = entity.getDirection();
        GlStack.translate(position.x, position.y, 0);
        GlStack.translate(tileSize/2.0f, tileSize/2.0f, 0);
        GlStack.rotate((float) (turnDir.ordinal() * Math.PI / 2.0), 0, 0, 1);
        GlStack.translate(-tileSize/2.0f, -tileSize/2.0f, 0);

        this.bulletSprite.draw();

        GlStack.pop();
    }
}
