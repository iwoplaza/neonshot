package ivesiris.neonshot.engine;

import ivesiris.neonshot.engine.lang.LocalizationLoader;
import ivesiris.neonshot.engine.lang.Localizer;
import ivesiris.neonshot.engine.screen.IScreen;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class GameEngine implements Runnable
{

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;
    public static final float UPDATE_INTERVAL = 1F / TARGET_UPS;

    private final Thread gameLoopThread;
    private final Window window;
    private final List<InitFunction> initFunctions;
    private IScreen[] registeredScreens;
    private IScreen screen;
    private final Timer timer;
    private final KeyboardHandler keyboardHandler;
    private Localizer localizer;
    private EngineContext engineContext = new EngineContext();

    private boolean running = true;
    private boolean debugMode = false;

    public GameEngine(String windowTitle, int width, int height, boolean vSync)
    {
        this.gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        this.window = new Window(windowTitle, width, height, vSync);
        this.initFunctions = new ArrayList<>();
        this.screen = null;
        this.timer = new Timer();
        this.keyboardHandler = new KeyboardHandler(this);
    }

    public void addInitFunction(InitFunction func)
    {
        this.initFunctions.add(func);
    }

    public void registerScreens(IScreen... screens)
    {
        this.registeredScreens = screens;
    }

    public void start()
    {
        gameLoopThread.start();
    }

    @Override
    public void run()
    {
        try
        {
            init();
            gameLoop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            cleanup();
        }
    }

    public Window getWindow()
    {
        return window;
    }

    public Localizer getLocalizer()
    {
        return localizer;
    }

    public boolean isDebugModeOn()
    {
        return debugMode;
    }

    protected void init() throws Exception
    {
        localizer = LocalizationLoader.createFromResources();

        window.init();
        timer.init();

        for (InitFunction function : this.initFunctions)
        {
            function.init();
        }

        for (IScreen screen : registeredScreens)
        {
            screen.init(window);
        }

        glfwSetKeyCallback(window.getHandle(), this.keyboardHandler);
    }

    protected void gameLoop() throws Exception
    {
        float elapsedTime;
        float accumulator = 0F;

        while (running && !window.shouldClose())
        {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= UPDATE_INTERVAL)
            {
                update(UPDATE_INTERVAL);
                accumulator -= UPDATE_INTERVAL;
            }

            this.engineContext.updateTime(elapsedTime, accumulator/UPDATE_INTERVAL);

            render();

            if (!window.isVSyncEnabled())
            {
                sync();
            }
        }
    }

    private void sync()
    {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime)
        {
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException ie)
            {
            }
        }
    }

    protected void input()
    {
        screen.updatePreFrame(window);
    }

    protected void update(float interval)
    {
        screen.update();
    }

    protected void render() throws Exception
    {
        if (screen != null)
            screen.render(this.engineContext, window);

        window.update();
    }

    void handleKeyPressed(int key, int mods)
    {
        if (screen != null)
            screen.handleKeyPressed(key, mods);
    }

    void handleKeyReleased(int key, int mods)
    {
        if (screen != null)
            screen.handleKeyReleased(key, mods);
    }

    protected void cleanup()
    {
        for (IScreen screen : registeredScreens)
        {
            screen.cleanUp();
        }
    }

    public void showScreen(IScreen screen)
    {
        this.screen = screen;
    }

    public void exit()
    {
        this.running = false;
    }
}
