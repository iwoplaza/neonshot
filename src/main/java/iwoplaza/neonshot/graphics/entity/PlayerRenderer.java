package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.graphics.shader.core.FlatShader;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.Transform2f;
import iwoplaza.meatengine.graphics.mesh.FlatMesh;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.io.IOException;

public class PlayerRenderer implements IGameEntityRenderer<PlayerEntity>
{
    private final Transform2f transform = new Transform2f();
    private final Mesh borderMesh;
    private TextureAsset playerTexture;

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
        loader.registerAsset(playerTexture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "textures/texture.png")));
    }

    @Override
    public void dispose()
    {
        this.borderMesh.dispose();
    }

    @Override
    public void render(IGameRenderContext ctx, PlayerEntity entity)
    {
        final int tileSize = ctx.getTileSize();
        final float partialTicks = ctx.getPartialTicks();

        FlatShader shader = CommonShaders.flatShader;

        shader.bind();
        shader.setColor(0, 0, 0, 1);

        Vector2f position = new Vector2f(new Vector2f(entity.getPrevPosition()).lerp(new Vector2f(entity.getNextPosition()), partialTicks));
        position.mul(tileSize);
        transform.setPosition(position);

        transform.updateTransform();
        Matrix4f matrix = transform.getMatrix();
//        matrix.rotateX((float) Math.PI/2);
        matrix.scale(tileSize, tileSize, tileSize);
        shader.setModelViewMatrix(matrix);

        this.playerTexture.bind();

        borderMesh.render();

        shader.unbind();

        //LineRenderer.draw(position.x, position.y, position.x + groundNormal.x * 5, position.y + groundNormal.y * 5);
    }
}