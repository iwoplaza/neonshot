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
import iwoplaza.meatengine.world.tile.TileMap;
import iwoplaza.neonshot.Main;
import iwoplaza.neonshot.PlayerController;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.graphics.GameRenderContext;
import iwoplaza.neonshot.graphics.GameRenderer;
import iwoplaza.neonshot.graphics.StaticCamera;
import iwoplaza.neonshot.loader.GameLevelLoader;
import iwoplaza.neonshot.powerup.Powerups;
import iwoplaza.neonshot.ui.game.FinishScreen;
import iwoplaza.neonshot.ui.game.PlayerHUD;
import iwoplaza.neonshot.world.entity.*;
import org.joml.Vector2i;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class VersusGameScreen implements IScreen
{
    private static final int POWER_UP_SPAWN_COOLDOWN = 200;

    private final GameRenderContext context;
    private final GameRenderer gameRenderer;
    private final GameLevelLoader levelLoader;
    private final PlayerHUD playerOneHUD;
    private final PlayerHUD playerTwoHUD;
    private final PlayerController playerOneController;
    private final PlayerController playerTwoController;

    private World world;
    private FinishScreen finishScreen = null;

    private List<PlayerEntity> players = new ArrayList<>();
    private PlayerEntity playerOne;
    private PlayerEntity playerTwo;

    private int powerUpSpawnCooldown = POWER_UP_SPAWN_COOLDOWN;

    public VersusGameScreen(RendererRegistry<IGameRenderContext> rendererRegistry)
    {
        this.context = new GameRenderContext(rendererRegistry);
        this.gameRenderer = new GameRenderer();
        this.playerOneHUD = new PlayerHUD(true);
        this.playerTwoHUD = new PlayerHUD(false);
        this.playerOneController = new PlayerController(GLFW_KEY_W, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_SPACE);
        this.playerTwoController = new PlayerController(GLFW_KEY_UP, GLFW_KEY_RIGHT, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_PERIOD);

        this.levelLoader = new GameLevelLoader((world, x, y) -> {
            if (this.playerOne == null)
            {
                this.playerOne = spawnPlayer(world, x, y, this.playerOneHUD);
            }
            else
            {
                this.playerTwo = spawnPlayer(world, x, y, this.playerTwoHUD);
            }

            world.getTileMap().setTile(x, y, Tiles.CHESSBOARD_FLOOR);
        }, () -> {});
    }

    @Override
    public void dispose()
    {
        if (this.world != null)
            this.world.dispose();
        this.playerOneHUD.dispose();
        this.playerTwoHUD.dispose();
    }

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        this.gameRenderer.registerAssets(loader);
        this.playerOneHUD.registerAssets(loader);
        this.playerTwoHUD.registerAssets(loader);
    }

    @Override
    public void init(Window window) throws Exception
    {
        this.gameRenderer.init(window);
    }

    @Override
    public void onOpened(Window window)
    {
        this.onResized(window);
    }

    @Override
    public void onResized(Window window)
    {
        this.gameRenderer.onResized(window);
        this.playerOneHUD.onResized(window);
        this.playerTwoHUD.onResized(window);

        glViewport(0, 0, window.getWidth(), window.getHeight());
    }

    public void startLevel(String levelName)
    {
        for (PlayerEntity player : this.players)
        {
            player.dispose();
        }
        this.players.clear();

        this.playerOne = null;
        this.playerTwo = null;
        this.powerUpSpawnCooldown = POWER_UP_SPAWN_COOLDOWN;

        if (this.world != null)
        {
            this.world.dispose();
            this.world = null;
        }

        try
        {
            this.world = levelLoader.loadFromName(levelName, key -> {
                throw new IllegalStateException(String.format("Couldn't find entity factory for '%s'", key));
            });

            this.gameRenderer.setWorld(this.world);

            StaticCamera camera = new StaticCamera();
            camera.setPosition(this.world.getTileMap().getWidth()/2, this.world.getTileMap().getHeight()/2);
            this.gameRenderer.setCamera(camera);
        }
        catch (IOException e)
        {
            throw new IllegalStateException(String.format("Couldn't load level '%s'", levelName));
        }

        if (this.playerOne == null || this.playerTwo == null)
        {
            throw new IllegalStateException(String.format("Two player start positions are missing from the loaded level: '%s'", levelName));
        }

        this.finishScreen = null;
    }

    private PlayerEntity spawnPlayer(World world, int x, int y, PlayerHUD hud)
    {
        PlayerEntity player = new PlayerEntity(5);
        player.setPosition(x, y);
        player.registerDeathListener(this::onGameOver);
        world.spawnEntity(player);
        this.players.add(player);

        hud.setPlayer(player);

        return player;
    }

    public void onGameOver(LivingEntity deadPlayer)
    {
        String title = "PLAYER ONE WINS";
        if (deadPlayer == playerOne)
        {
            title = "PLAYER TWO WINS";
        }

        this.finishScreen = new FinishScreen(title, () -> {
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
            if (this.powerUpSpawnCooldown > 0)
            {
                this.powerUpSpawnCooldown--;
            }
            else
            {
                this.spawnPowerup();
                this.powerUpSpawnCooldown = POWER_UP_SPAWN_COOLDOWN;
            }

            this.world.update(context);
            this.gameRenderer.update(this.context);
        }
    }

    private void spawnPowerup()
    {
        final Random random = new Random();
        final TileMap tileMap = this.world.getTileMap();
        final int mapWidth = tileMap.getWidth();
        final int mapHeight = tileMap.getHeight();

        Vector2i position = new Vector2i();
        do
        {
            position.set(random.nextInt(mapWidth), random.nextInt(mapHeight));
        }
        while (!world.canTraverseTo(position, true));

        ItemEntity itemToSpawn;
        if (random.nextFloat() < 0.5)
        {
            itemToSpawn = new BandageEntity(position, 300);
        }
        else
        {
            // Spawning a powerup
            int choice = random.nextInt(Powerups.POWERUPS.size());
            itemToSpawn = new PowerupItemEntity(Powerups.POWERUPS.get(choice), position, 300);
        }

        this.world.spawnEntity(itemToSpawn);
    }

    @Override
    public void updatePerFrame(IEngineContext context, Window window)
    {
        this.world.updatePerFrame(context);

        this.playerOneController.handleControls(this.playerOne, window);
        this.playerTwoController.handleControls(this.playerTwo, window);
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
            this.playerOneHUD.render(this.context);
            this.playerTwoHUD.render(this.context);
        }
    }

    @Override
    public void handleKeyPressed(int key, int mods)
    {
        if (this.finishScreen != null)
        {
            this.finishScreen.handleKeyPressed(key, mods);
        }
        else
        {
            if (key == GLFW_KEY_ESCAPE)
            {
                Main.GAME_ENGINE.showScreen(Main.TITLE_SCREEN);
            }
        }
    }

    @Override
    public void handleKeyReleased(int key, int mods)
    {

    }
}
