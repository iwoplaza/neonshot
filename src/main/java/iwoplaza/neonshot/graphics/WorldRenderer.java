package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.Drawable;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.mesh.TexturedMesh;
import iwoplaza.meatengine.graphics.shader.core.TileShader;
import iwoplaza.meatengine.helper.ArrayHelper;
import iwoplaza.meatengine.world.Entity;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.world.tile.*;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Main;
import iwoplaza.neonshot.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldRenderer implements IDisposable, IAssetConsumer
{

    private TextureAsset tileMapTexture;

    private World world;
    private Mesh bakedMesh;

    public WorldRenderer() {}

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        loader.registerAsset(tileMapTexture = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "/textures/tilemap.png")));
    }

    public void setWorld(World world)
    {
        this.world = world;
        this.bakedMesh = null;
    }

    public void bakeMesh(IGameRenderContext context)
    {
        List<Integer> indices = new ArrayList<>();
        List<Float> positions = new ArrayList<>();
        List<Float> texCoords = new ArrayList<>();

        final int tileSize = context.getTileSize();
        final int textureWidth = this.tileMapTexture.getWidth();
        final int textureHeight = this.tileMapTexture.getHeight();

        TileMap tileMap = this.world.getTileMap();

        for (int tileX = 0; tileX < tileMap.getWidth(); ++tileX)
        {
            for (int tileY = 0; tileY < tileMap.getHeight(); ++tileY)
            {
                TileData data = tileMap.getTileAt(tileX, tileY);
                if (data != null)
                {
                    Tile tile = TileRegistry.get(data.getTileId());
                    tile.getRenderer().render(data, new TileLocation(tileX, tileY), tileSize, textureWidth, textureHeight, indices
                            , positions, texCoords);
                }
            }
        }

        if (this.bakedMesh != null)
            this.bakedMesh.dispose();

        this.bakedMesh = new TexturedMesh(
                ArrayHelper.listToIntArray(indices),
                ArrayHelper.listToFloatArray(positions),
                ArrayHelper.listToFloatArray(texCoords)
        );
    }

    public void render(IGameRenderContext context, Window window, World world)
    {
        // Render
//        this.renderBackground();

        TileShader shader = CommonShaders.tileShader;
        shader.bind();

        shader.getProjectionMatrix().set(GlStack.MAIN.projectionMatrix);
        shader.getModelViewMatrix().set(GlStack.MAIN.top());
        shader.getColor().set(1, 1, 1, 1);
        shader.getUseTexture().set(true);

        this.tileMapTexture.bind();

        if (this.bakedMesh == null || world.getTileMap().isDirty())
            this.bakeMesh(context);

        this.bakedMesh.render();

        shader.unbind();

        for (Entity entity : this.world.getEntities())
        {
            context.getRendererRegistry().renderEntity(context, entity);
        }

        if (Main.GAME_ENGINE.isDebugModeOn())
            ChallengeRoomDebug.INSTANCE.draw(context, this.world);
    }

    @Override
    public void dispose()
    {
        if (this.bakedMesh != null)
            this.bakedMesh.dispose();
    }
}
