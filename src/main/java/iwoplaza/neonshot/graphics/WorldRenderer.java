package iwoplaza.neonshot.graphics;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.mesh.Mesh;
import iwoplaza.meatengine.graphics.mesh.TexturedMesh;
import iwoplaza.meatengine.graphics.shader.core.TileShader;
import iwoplaza.meatengine.helper.ArrayHelper;
import iwoplaza.meatengine.world.Entity;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.CommonShaders;
import iwoplaza.neonshot.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldRenderer implements IDisposable, IAssetConsumer
{

    private static final float Z_NEAR = -10F;
    private static final float Z_FAR = 1000F;

    private final World world;

    private Mesh bakedMesh;
    public TextureAsset tileMap;

    public WorldRenderer(World world)
    {
        this.world = world;
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        loader.registerAsset(tileMap = new TextureAsset(AssetLocation.asResource(Statics.RES_ORIGIN, "/textures/tilemap.png")));
    }

    public void bakeMesh(IGameRenderContext context)
    {
        List<Integer> indices = new ArrayList<>();
        List<Float> positions = new ArrayList<>();
        List<Float> texCoords = new ArrayList<>();

        final int tileSize = context.getTileSize();
//        final int tileMapWidth = WorldRenderer.tileMap.getWidth();
//        final int tileMapHeight = WorldRenderer.tileMap.getHeight();
//
//        for (int i = 0; i < this.tiles.length; ++i)
//        {
//            TileData data = this.tiles[i];
//            if (data != null)
//            {
//                int tileX = i % width;
//                int tileY = i / width;
//                Tile tile = TileRegistry.get(data.getTileId());
//                tile.getRenderer().render(data, new TileLocation(this, tileX, tileY), tileSize, tileMapWidth, tileMapHeight, indices
//                        , positions, texCoords);
//            }
//        }

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

        shader.setColor(1, 1, 1, 1);
        shader.setUseTexture(true);

        this.tileMap.bind();

        if (this.bakedMesh == null)
            this.bakeMesh(context);

        this.bakedMesh.render();

        shader.unbind();

        for (Entity entity : this.world.getEntities())
        {
            context.getRendererRegistry().renderEntity(context, entity);
        }

//        if (Main.GAME_ENGINE.isDebugModeOn())
//            DebugGameScreenRenderer.renderPhysicsGizmos(this, renderer);
    }

    @Override
    public void dispose()
    {
        if (this.bakedMesh != null)
            this.bakedMesh.dispose();
    }
}
