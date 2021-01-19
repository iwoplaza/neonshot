package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.meatengine.util.Easing;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.ChallengeDoorEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class ChallengeDoorRenderer implements IGameEntityRenderer<ChallengeDoorEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public ChallengeDoorRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/challenge_door.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void render(IGameRenderContext ctx, ChallengeDoorEntity entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();
        float progress = 0;

        if (entity.isOpening())
        {
            progress = Easing.easeInOut(1 - (entity.getOpenProgress() + partialTicks) / entity.getOpenDuration());
        }
        else
        {
            progress = Easing.easeInOut(Math.min((entity.getLifetime() + partialTicks) / entity.getOpenDuration(), 1));
        }

        Vector2f basePosition = new Vector2f(entity.getNextPosition());
        basePosition.mul(tileSize);

        GlStack.translate(basePosition.x, basePosition.y, 0);

        // Top of the door
        GlStack.push();
        this.sprite.setFrameX(1);
        this.sprite.setFrameY(3 - progress * 2);
        this.sprite.draw();

        GlStack.translate(0, tileSize, 0);
        this.sprite.setFrameX(1);
        this.sprite.setFrameY(2 - progress * 2);
        this.sprite.draw();
        GlStack.pop();

        // Front of the door
        GlStack.push();
        GlStack.translate(0, Math.max(1 - progress * 2, 0) * tileSize, 0);
        this.sprite.setFrameX(0);
        this.sprite.setFrameY(Math.min(2 - progress * 2, 1));
        this.sprite.draw();
        GlStack.pop();

        GlStack.pop();
    }
}
