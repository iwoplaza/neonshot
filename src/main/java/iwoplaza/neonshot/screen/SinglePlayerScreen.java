package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.meatengine.screen.IScreen;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.graphics.GameRenderContext;
import iwoplaza.neonshot.graphics.GameRenderer;
import iwoplaza.neonshot.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.PlayerEntity;

import java.io.IOException;

public class SinglePlayerScreen implements IScreen
{
    private final GameRenderContext context;
    private final World world;
    private GameRenderer gameRenderer;

    private PlayerEntity player;

    public SinglePlayerScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);
        this.world = new World();
        this.gameRenderer = new GameRenderer(world);
    }

    @Override
    public void init(Window window)
    {
        this.player = new PlayerEntity();
        this.world.spawnEntity(this.player);
        this.gameRenderer.init(window);
    }

    @Override
    public void onResized(Window window)
    {
        this.gameRenderer.onResized(window);
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        gameRenderer.registerAssets(loader);
    }

    @Override
    public void update(IEngineContext context)
    {
        this.world.update(context);
    }

    @Override
    public void updatePreFrame(IEngineContext context, Window window)
    {
    }

    @Override
    public void render(IEngineContext context, Window window) throws Exception
    {
        this.context.update(context);

        this.gameRenderer.render(this.context, window, world);
    }

    @Override
    public void dispose()
    {
        this.world.dispose();
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {

    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {

    }
}
