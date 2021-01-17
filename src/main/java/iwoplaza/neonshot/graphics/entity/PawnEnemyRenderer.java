package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.PathfinderDebug;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.PawnEnemyEntity;

import java.io.IOException;

public class PawnEnemyRenderer extends EnemyRenderer<PawnEnemyEntity>
{
    private TextureAsset texture;
    private Sprite sprite;

    public PawnEnemyRenderer()
    {
        super();
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

    @Override
    public void drawEntity(IGameRenderContext ctx, PawnEnemyEntity entity)
    {
        this.sprite.draw();

        super.drawEntity(ctx, entity);
    }

    @Override
    public void render(IGameRenderContext ctx, PawnEnemyEntity entity)
    {
        super.render(ctx, entity);

        PathfinderDebug.INSTANCE.draw(ctx, entity.getPathfindingActor());
    }
}
