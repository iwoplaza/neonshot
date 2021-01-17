package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.meatengine.Direction;
import iwoplaza.neonshot.Statics;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class PlayerRenderer implements IGameEntityRenderer<PlayerEntity>
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

    private Vector2f getPlayerRenderPosition(PlayerEntity entity, float partialTicks)
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
    public void render(IGameRenderContext ctx, PlayerEntity entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = this.getPlayerRenderPosition(entity, partialTicks);
        position.mul(tileSize);

        GlStack.translate(position.x, position.y, 0);

        Direction turnDir = entity.getDirection();
        this.playerSprite.setFrameX(turnDir.ordinal());

        int shootFrame = Math.min(entity.getShootDuration() - entity.getShootCooldown(), 3) % 3;

        this.playerSprite.setFrameY(shootFrame);
        this.playerSprite.draw();

        GlStack.pop();
    }
}
