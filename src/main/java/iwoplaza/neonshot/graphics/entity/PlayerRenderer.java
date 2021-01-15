package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.Transform2f;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.graphics.IGameRenderContext;
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

        Vector2f position = new Vector2f(new Vector2f(entity.getPrevPosition()).lerp(new Vector2f(entity.getNextPosition()), partialTicks));
        position.mul(tileSize);

        GlStack.translate(position.x * tileSize, position.y * tileSize, 0);

        FlatShader shader = CommonShaders.flatShader;

        shader.bind();
        shader.setColor(1, 1, 1, 1);
        shader.setProjectionMatrix(GlStack.MAIN.projectionMatrix);
        shader.setModelViewMatrix(GlStack.MAIN.top());

        //this.playerTexture.bind();

        borderMesh.render();

        shader.unbind();

        this.playerSprite.setFrameX(1);
        this.playerSprite.draw();
//        LineRenderer.draw(position.x, position.y, position.x + groundNormal.x * 5, position.y + groundNormal.y * 5);

        GlStack.pop();
    }
}
