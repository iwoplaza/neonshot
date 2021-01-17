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
    private World world;
    private GameRenderer gameRenderer;
    private PlayerHUD playerHUD;
    private FinishScreen finishScreen = null;

    private PlayerEntity player;

    public SinglePlayerScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);

        try
        {
            GameLevelLoader levelLoader = new GameLevelLoader((world, x, y) -> {
                this.player = new PlayerEntity();
                this.player.setPosition(x, y);
                this.player.registerDeathListener(p -> this.onGameOver());
                world.spawnEntity(this.player);

                world.getTileMap().setTile(x, y, Tiles.CHESSBOARD_FLOOR);

                this.playerHUD = new PlayerHUD(this.player);
            });
            this.world = levelLoader.loadFromName("level1", key -> {
                switch(key)
                {
                    case "Pawn":
                        return PawnEnemyEntity::new;
                    default:
                        throw new IllegalStateException(String.format("Couldn't find entity factory for '%s'", key));
                }
            });
            this.gameRenderer = new GameRenderer(world);

            Camera camera = new Camera();
            this.gameRenderer.setCamera(camera);
            camera.follow(this.player);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Window window)
    {
        this.gameRenderer.init(window);

        this.world.spawnEntity(new PawnEnemyEntity(new Vector2i(2, this.world.getTileMap().getHeight() - 3)));
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
            Main.GAME_ENGINE.showScreen(Main.TITLE_SCREEN);
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
