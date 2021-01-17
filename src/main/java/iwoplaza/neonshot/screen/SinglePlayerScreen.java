package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.Camera;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.meatengine.screen.IScreen;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.Direction;
import iwoplaza.neonshot.GameLevelLoader;
import iwoplaza.neonshot.Statics;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.graphics.GameRenderContext;
import iwoplaza.neonshot.graphics.GameRenderer;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.PawnEnemyEntity;
import iwoplaza.neonshot.world.entity.PlayerEntity;
import org.joml.Vector2i;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.opengl.GL11.glViewport;

public class SinglePlayerScreen implements IScreen
{
    private final GameRenderContext context;
    private World world;
    private GameRenderer gameRenderer;

    private PlayerEntity player;

    public SinglePlayerScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);

        try
        {
            GameLevelLoader levelLoader = new GameLevelLoader((world, x, y) -> {
                this.player = new PlayerEntity();
                this.player.setPosition(x, y);
                world.spawnEntity(this.player);

                world.getTileMap().setTile(x, y, Tiles.CHESSBOARD_FLOOR);
            });
            this.world = levelLoader.loadFromStream((AssetLocation.asResource(Statics.RES_ORIGIN, "levels/level1.png")).getInputStream());
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

        glViewport(0, 0, window.getWidth(), window.getHeight());
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

        this.gameRenderer.update(this.context);
    }

    @Override
    public void updatePerFrame(IEngineContext context, Window window)
    {
        world.updatePerFrame(context);

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
    }

    @Override
    public void dispose()
    {
        this.world.dispose();
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {
        if (key == GLFW_KEY_SPACE)
        {
            this.player.shoot();
        }
    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {

    }
}
