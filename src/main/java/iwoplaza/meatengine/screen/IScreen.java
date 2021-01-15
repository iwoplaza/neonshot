package iwoplaza.meatengine.screen;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetConsumer;

public interface IScreen extends IDisposable, IAssetConsumer
{

    void init(Window window) throws Exception;
    void onResized(Window window);
    void update(IEngineContext context);
    void updatePerFrame(IEngineContext context, Window window);
    void render(IEngineContext context, Window window) throws Exception;

    void handleKeyPressed(int key, int mods);
    void handleKeyReleased(int key, int mods);

}
