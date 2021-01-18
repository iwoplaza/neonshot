package iwoplaza.neonshot;

import iwoplaza.meatengine.GameEngine;
import iwoplaza.meatengine.assets.AssetLoader;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.neonshot.graphics.ChallengeRoomDebug;
import iwoplaza.neonshot.graphics.HealthBarRenderer;
import iwoplaza.neonshot.graphics.entity.*;
import iwoplaza.neonshot.screen.LevelSelectScreen;
import iwoplaza.neonshot.screen.SinglePlayerScreen;
import iwoplaza.neonshot.screen.TitleScreen;
import iwoplaza.neonshot.screen.VersusGameScreen;
import iwoplaza.neonshot.world.entity.*;

public class Main
{
    public static GameEngine GAME_ENGINE;
    public static TitleScreen TITLE_SCREEN;
    public static LevelSelectScreen LEVEL_SELECT_SCREEN;
    public static SinglePlayerScreen SINGLE_PLAYER_SCREEN;
    public static VersusGameScreen VERSUS_GAME_SCREEN;

    private static AssetLoader assetLoader = new AssetLoader();
    private static RendererRegistry<IGameRenderContext> rendererRegistry = new RendererRegistry<>();

    public static void main(String[] args)
    {
        try
        {
            GAME_ENGINE = new GameEngine("NEON_SHOT",
                    900, 600, true);

            GAME_ENGINE.addInitFunction(() -> {
                CommonShaders.loadShaders();
                CommonFonts.loadFonts();
                Tiles.registerTiles();
                HealthBarRenderer.INSTANCE.init();
                ChallengeRoomDebug.INSTANCE.init(assetLoader);

                rendererRegistry.registerRenderer(PlayerEntity.class, new PlayerRenderer());
                rendererRegistry.registerRenderer(SimpleBulletEntity.class, new BulletRenderer<>());
                rendererRegistry.registerRenderer(BadBulletEntity.class, new BulletRenderer<>());
                rendererRegistry.registerRenderer(PawnEnemyEntity.class, new PawnEnemyRenderer());
                rendererRegistry.registerRenderer(SentryEnemyEntity.class, new SentryEnemyRenderer());
                rendererRegistry.registerRenderer(BandageEntity.class, new ItemRenderer<>());
                rendererRegistry.registerRenderer(PowerupItemEntity.class, new ItemRenderer<>());
                rendererRegistry.registerRenderer(FinishZoneEntity.class, new FinishZoneRenderer());
                // Registers all assets from registered renderers.
                rendererRegistry.registerAssets(assetLoader);

                GAME_ENGINE.getLocalizer().setLanguage("us_en");

                TITLE_SCREEN =          new TitleScreen(GAME_ENGINE.getLocalizer());
                LEVEL_SELECT_SCREEN =   new LevelSelectScreen(GAME_ENGINE.getLocalizer());
                SINGLE_PLAYER_SCREEN =  new SinglePlayerScreen(rendererRegistry);
                VERSUS_GAME_SCREEN =    new VersusGameScreen(rendererRegistry);
                GAME_ENGINE.registerScreens(TITLE_SCREEN, LEVEL_SELECT_SCREEN, SINGLE_PLAYER_SCREEN, VERSUS_GAME_SCREEN);
                // Registers all assets from registered screens.
                GAME_ENGINE.registerAssets(assetLoader);
                GAME_ENGINE.showScreen(TITLE_SCREEN);
            });

            GAME_ENGINE.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
