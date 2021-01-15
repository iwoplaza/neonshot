package iwoplaza.neonshot.screen;

import iwoplaza.meatengine.IEngineContext;
import iwoplaza.meatengine.Window;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.meatengine.screen.IScreen;
import iwoplaza.meatengine.world.World;
import iwoplaza.meatengine.world.tile.TileMap;
import iwoplaza.neonshot.Direction;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.graphics.GameRenderContext;
import iwoplaza.neonshot.graphics.GameRenderer;
import iwoplaza.neonshot.graphics.IGameRenderContext;
import iwoplaza.neonshot.world.entity.PlayerEntity;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class SinglePlayerScreen implements IScreen
{
    private final GameRenderContext context;
    private final World world;
    private GameRenderer gameRenderer;

    private PlayerEntity player;

    public SinglePlayerScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);
        this.world = new World(10, 10);
        this.gameRenderer = new GameRenderer(world);

        this.initMap();
    }

    private void initMap()
    {
        TileMap tileMap = this.world.getTileMap();

        for (int x = 0; x < tileMap.getWidth(); ++x)
        {
            for (int y = 0; y < tileMap.getHeight(); ++y)
            {
                if (x != 0 && y != 0)
                {
                    tileMap.setTile(x, y, Tiles.CHESSBOARD_FLOOR);
                }
                else
                {
                    tileMap.setTile(x, y, Tiles.VOID);
                }
            }
        }
        tileMap.setTile(0, 0, Tiles.VOID);
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

    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {

    }
}
