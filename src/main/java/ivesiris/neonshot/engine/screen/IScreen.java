package ivesiris.neonshot.engine.screen;

import ivesiris.neonshot.engine.Window;

public interface IScreen
{

    void init(Window window) throws Exception;
    void update();
    void updatePreFrame(Window window);
    void render(float deltaTime, Window window) throws Exception;
    void cleanUp();

    void handleKeyPressed(int key, int mods);
    void handleKeyReleased(int key, int mods);

}
