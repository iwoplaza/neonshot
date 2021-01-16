package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.PathfinderDebug;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Direction;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.PawnEnemyEntity;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class PawnEnemyRenderer implements IGameEntityRenderer<PawnEnemyEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public PawnEnemyRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(texture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/pawn.png")));

        this.sprite = new Sprite(texture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    private Vector2f getRenderPosition(PawnEnemyEntity entity, float partialTicks)
    {
        Vector2f position = new Vector2f(entity.getNextPosition());
        int moveCooldown = entity.getMoveCooldown();
        if (moveCooldown > 0)
        {
            float moveProgress = 1 - ((entity.getMoveDuration() - moveCooldown) + partialTicks) / entity.getMoveDuration();

            moveProgress *= moveProgress;

            position.lerp(new Vector2f(entity.getPrevPosition()), moveProgress);
        }

        return position;
    }

    @Override
    public void render(IGameRenderContext ctx, PawnEnemyEntity entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = this.getRenderPosition(entity, partialTicks);
        position.mul(tileSize);

        GlStack.translate(position.x, position.y, 0);

        this.sprite.draw();

        GlStack.pop();

        PathfinderDebug.INSTANCE.draw(ctx, entity.getPathfindingActor());
    }
}
