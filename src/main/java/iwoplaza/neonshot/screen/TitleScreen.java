package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.GlStack;
import iwoplaza.meatengine.lang.ILocalizer;
import iwoplaza.meatengine.screen.IScreen;
import iwoplaza.neonshot.Main;
import iwoplaza.neonshot.ui.menu.MenuUI;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TitleScreen implements IScreen
{

    private static final int WIDTH = 448;
    private static final int HEIGHT = 224;

    private final Matrix4f modelViewMatrix = new Matrix4f();

    private final MenuUI menu;

    public TitleScreen(ILocalizer localizer)
    {
        this.menu = new MenuUI(WIDTH / 2, 60);
        this.menu.addOption(localizer.getLocalized("menu.campaign"));
        this.menu.addOption(localizer.getLocalized("menu.versus"));
        this.menu.addOption(localizer.getLocalized("menu.toggle_fullscreen"));
        this.menu.addOption(localizer.getLocalized("menu.quit"));
        this.menu.updateBorderMesh();
    }

    @Override
    public void onOpened(Window window)
    {
        this.onResized(window);
    }

    @Override
    public void init(Window window)
    {
    }

    @Override
    public void registerAssets(IAssetLoader loader)
    {
        // No assets to register
    }

    @Override
    public void onResized(Window window)
    {
        final int windowWidth = window.getWidth();
        final int windowHeight = window.getHeight();

        GlStack.MAIN.projectionMatrix.identity().ortho(0, windowWidth, 0, windowHeight, -1, 1000);
        final float scale = this.getAppropriateScaling(window);
        this.modelViewMatrix.identity().translate(windowWidth / 2F, windowHeight / 2F, 0).scale(scale).translate(-WIDTH / 2F, -HEIGHT / 2F, 0);
    }

    @Override
    public void update(IEngineContext context)
    {

    }

    @Override
    public void updatePerFrame(IEngineContext context, Window window)
    {

    }

    @Override
    public void render(IEngineContext context, Window window)
    {
        glViewport(0, 0, window.getWidth(), window.getHeight());
        window.setClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GlStack.MAIN.set(modelViewMatrix);

        Matrix4f matrix = new Matrix4f(modelViewMatrix);
        matrix.translate(WIDTH / 2F - 512 / 2F, HEIGHT - 128, 0);

        this.menu.render(context);
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {
        if (key == GLFW_KEY_UP || key == GLFW_KEY_W)
            this.menu.selectPrevious();

        if (key == GLFW_KEY_DOWN || key == GLFW_KEY_S)
            this.menu.selectNext();

        if (key == GLFW_KEY_SPACE || key == GLFW_KEY_ENTER)
        {
            int option = this.menu.getSelectedOption();
            switch (option)
            {
                case 0:
                    Main.GAME_ENGINE.showScreen(Main.LEVEL_SELECT_SCREEN);
                    break;
                case 1:
                    Main.GAME_ENGINE.showScreen(Main.VERSUS_GAME_SCREEN);
                    Main.VERSUS_GAME_SCREEN.startLevel("versus");
                    break;
                case 2:
                    if (Main.GAME_ENGINE.getWindow().isFullscreen())
                        Main.GAME_ENGINE.getWindow().setWindowed();
                    else
                        Main.GAME_ENGINE.getWindow().setFullscreen();
                    break;
                case 3:
                    Main.GAME_ENGINE.exit();
                    break;
            }
        }
    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {
    }

    private int getAppropriateScaling(Window window)
    {
        int xScale = window.getWidth() / WIDTH;
        int yScale = window.getHeight() / HEIGHT;

        return Math.max(1, Math.min(xScale, yScale));
    }
}
