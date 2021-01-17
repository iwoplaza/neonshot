package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.Camera;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.meatengine.screen.IScreen;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.Main;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.graphics.GameRenderContext;
import iwoplaza.neonshot.graphics.GameRenderer;
import iwoplaza.neonshot.loader.GameLevelLoader;
import iwoplaza.neonshot.ui.game.FinishScreen;
import iwoplaza.neonshot.ui.game.PlayerHUD;
import iwoplaza.neonshot.world.entity.PawnEnemyEntity;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Vector2i;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class SinglePlayerScreen implements IScreen
{
    private final GameRenderContext context;
    private final GameRenderer gameRenderer;
    private final GameLevelLoader levelLoader;
    private final PlayerHUD playerHUD;

    private World world;
    private FinishScreen finishScreen = null;

    private PlayerEntity player;

    public SinglePlayerScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);
        this.gameRenderer = new GameRenderer();
        this.playerHUD = new PlayerHUD();

        this.levelLoader = new GameLevelLoader((world, x, y) -> {
            this.player = new PlayerEntity();
            this.player.setPosition(x, y);
            this.player.registerDeathListener(p -> this.onGameOver());
            this.playerHUD.setPlayer(this.player);
            world.spawnEntity(this.player);

            world.getTileMap().setTile(x, y, Tiles.CHESSBOARD_FLOOR);

        }, this::onLevelComplete);
    }

    @Override
    public void onOpened(Window window)
    {
        this.onResized(window);
    }

    public void startLevel(String levelName)
    {
        this.player = null;

        try
        {
            this.world = levelLoader.loadFromName(levelName, key -> {
                switch(key)
                {
                    case "Pawn":
                        return PawnEnemyEntity::new;
                    default:
                        throw new IllegalStateException(String.format("Couldn't find entity factory for '%s'", key));
                }
            });

            this.gameRenderer.setWorld(this.world);

            Camera camera = new Camera();
            this.gameRenderer.setCamera(camera);
            camera.follow(this.player);
            camera.snapToEndpoint();
        }
        catch (IOException e)
        {
            throw new IllegalStateException(String.format("Couldn't load level '%s'", levelName));
        }

        if (this.player == null)
        {
            throw new IllegalStateException(String.format("A player start position is missing from the loaded level: '%s'", levelName));
        }

        this.finishScreen = null;
    }

    @Override
    public void init(Window window)
    {
        this.gameRenderer.init(window);
    }

    @Override
    public void onResized(Window window)
    {
        this.gameRenderer.onResized(window);
        this.playerHUD.onResized(window);

        glViewport(0, 0, window.getWidth(), window.getHeight());
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        this.gameRenderer.registerAssets(loader);
        this.playerHUD.registerAssets(loader);
    }

    public void onGameOver()
    {
        this.finishScreen = new FinishScreen("Game Over", () -> {
            Main.GAME_ENGINE.showScreen(Main.LEVEL_SELECT_SCREEN);
        });
    }

    public void onLevelComplete()
    {
        this.finishScreen = new FinishScreen("Level Complete", () -> {
            Main.GAME_ENGINE.showScreen(Main.LEVEL_SELECT_SCREEN);
        });
    }

    @Override
    public void update(IEngineContext context)
    {
        if (this.finishScreen != null)
        {
            // Do something?
        }
        else
        {
            this.world.update(context);

            this.gameRenderer.update(this.context);
        }
    }

    @Override
    public void updatePerFrame(IEngineContext context, Window window)
    {
        world.updatePerFrame(context);

        if (window.isKeyPressed(GLFW_KEY_SPACE))
        {
            this.player.shoot();
        }

        if (window.isKeyPressed(GLFW_KEY_A))
        {
            this.player.setMoveDirection(Direction.WEST);
        }
        else if (window.isKeyPressed(GLFW_KEY_D))
        {
            this.player.setMoveDirection(Direction.EAST);
        }
        else if (window.isKeyPressed(GLFW_KEY_W))
        {
            this.player.setMoveDirection(Direction.NORTH);
        }
        else if (window.isKeyPressed(GLFW_KEY_S))
        {
            this.player.setMoveDirection(Direction.SOUTH);
        }
        else
        {
            this.player.setMoveDirection(null);
        }
    }

    @Override
    public void render(IEngineContext context, Window window) throws Exception
    {
        this.context.update(context);

        this.gameRenderer.render(this.context, window, world);

        if (this.finishScreen != null)
        {
            this.finishScreen.render(context, window);
        }
        else
        {
            this.playerHUD.render(this.context);
        }
    }

    @Override
    public void dispose()
    {
        this.world.dispose();
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {
        if (this.finishScreen != null)
        {
            this.finishScreen.handleKeyPressed(key, mods);
        }
    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {

    }
}
