package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.screen.IScreen;

import java.io.IOException;

public class SinglePlayerScreen implements IScreen
{
    private final World world;

    public SinglePlayerScreen()
    {
        this.world = new World();
    }

    @Override
    public void init(Window window) throws Exception
    {

    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {

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
