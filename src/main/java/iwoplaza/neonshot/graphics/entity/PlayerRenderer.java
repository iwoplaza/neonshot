package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.PlayerEntity;

import java.io.IOException;

public class PlayerRenderer extends LivingRenderer<PlayerEntity>
{
    private TextureAsset playerTexture;
    private Sprite playerSprite;

    public PlayerRenderer()
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(playerTexture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/entities/player.png")));

        this.playerSprite = new Sprite(playerTexture, 32, 32);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void drawEntity(IGameRenderContext ctx, PlayerEntity entity)
    {
        Direction turnDir = entity.getDirection();
        this.playerSprite.setFrameX(turnDir.ordinal());
        int shootFrame = Math.min(entity.getShootDuration() - entity.getShootCooldown(), 3) % 3;
        this.playerSprite.setFrameY(shootFrame);

        this.applyDamageOverlayColor(ctx, entity, this.playerSprite);

        if (this.isVisible(ctx, entity))
        {
            this.playerSprite.draw();
        }
    }

}
