package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.Direction;
import iwoplaza.neonshot.Statics;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Vector2f;

import java.io.IOException;

public class PlayerRenderer implements IGameEntityRenderer<PlayerEntity>
{
    private final Mesh borderMesh;
    private TextureAsset playerTexture;
    private Sprite playerSprite;

    public PlayerRenderer()
    {
        int dx = 40;
        int dy = 14;
        int t = 2;

        int[] indices = new int[] {
                // Left
                0, 3, 4, 4, 3, 7,
                // Bottom
                0, 4, 1, 1, 4, 5,
                // Right
                5, 6, 2, 1, 5, 2,
                // Top
                7, 2, 6, 7, 3, 2
        };

        float[] positions = new float[] {
                -dx, -dy,
                dx, -dy,
                dx, dy,
                -dx, dy,

                -dx + t, -dy + t,
                dx - t, -dy + t,
                dx - t, dy - t,
                -dx + t, dy - t
        };

        this.borderMesh = new FlatMesh(indices, positions);
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        // Assets are disposed of automatically.
        loader.registerAsset(playerTexture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/player.png")));

        this.playerSprite = new Sprite(playerTexture, 32, 32);
    }

    @Override
    public void dispose()
    {
        this.borderMesh.dispose();
    }

    @Override
    public void render(IGameRenderContext ctx, PlayerEntity entity)
    {
        GlStack.push();

        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        Vector2f position = new Vector2f(entity.getNextPosition());
        int moveCooldown = entity.getMoveCooldown();
        if (moveCooldown > 0)
        {
            float moveProgress = 1 - ((entity.getMoveDuration() - moveCooldown) + partialTicks) / entity.getMoveDuration();

            moveProgress *= moveProgress;

            position.lerp(new Vector2f(entity.getPrevPosition()), moveProgress);
        }

        position.mul(tileSize);

        GlStack.translate(position.x, position.y, 0);

        Direction turnDir = entity.getDirection();
        this.playerSprite.setFrameX(turnDir.ordinal());
        this.playerSprite.draw();
//        LineRenderer.draw(position.x, position.y, position.x + groundNormal.x * 5, position.y + groundNormal.y * 5);

        GlStack.pop();
    }
}
