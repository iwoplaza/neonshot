package iwoplaza.neonshot;

import iwoplaza.meatengine.GameEngine;
import iwoplaza.meatengine.assets.AssetLoader;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.neonshot.graphics.ChallengeRoomDebug;
import iwoplaza.neonshot.graphics.HealthBarRenderer;
import iwoplaza.neonshot.graphics.entity.BulletRenderer;
import iwoplaza.neonshot.graphics.entity.ItemRenderer;
import iwoplaza.neonshot.graphics.entity.PawnEnemyRenderer;
import iwoplaza.neonshot.graphics.entity.PlayerRenderer;
import iwoplaza.neonshot.screen.SinglePlayerScreen;
import iwoplaza.neonshot.screen.TitleScreen;
import iwoplaza.neonshot.world.entity.*;

public class Main
{
    public static GameEngine GAME_ENGINE;
    public static TitleScreen TITLE_SCREEN;
    public static SinglePlayerScreen SINGLE_PLAYER_SCREEN;

    private static AssetLoader assetLoader = new AssetLoader();
    private static RendererRegistry<IGameRenderContext> rendererRegistry = new RendererRegistry<>();

    public static void main(String[] args)
    {
        try
        {
            GAME_ENGINE = new GameEngine("NeonShot",
                    900, 600, true);

            GAME_ENGINE.addInitFunction(() -> {
                CommonShaders.loadShaders();
                CommonFonts.loadFonts();
                Tiles.registerTiles();
                HealthBarRenderer.INSTANCE.init();
                ChallengeRoomDebug.INSTANCE.init(assetLoader);

                rendererRegistry.registerRenderer(PlayerEntity.class, new PlayerRenderer());
                rendererRegistry.registerRenderer(SimpleBulletEntity.class, new BulletRenderer<>());
                rendererRegistry.registerRenderer(PawnEnemyEntity.class, new PawnEnemyRenderer());
                rendererRegistry.registerRenderer(BandageEntity.class, new ItemRenderer<>());
                rendererRegistry.registerRenderer(PowerupItemEntity.class, new ItemRenderer<>());
                // Registers all assets from registered renderers.
                rendererRegistry.registerAssets(assetLoader);

                GAME_ENGINE.getLocalizer().setLanguage("us_en");

                TITLE_SCREEN =          new TitleScreen(GAME_ENGINE.getLocalizer());
                SINGLE_PLAYER_SCREEN =  new SinglePlayerScreen(rendererRegistry);
                GAME_ENGINE.registerScreens(TITLE_SCREEN, SINGLE_PLAYER_SCREEN);
                // Registers all assets from registered screens.
                GAME_ENGINE.registerAssets(assetLoader);
//                GAME_ENGINE.showScreen(TITLE_SCREEN);
                GAME_ENGINE.showScreen(SINGLE_PLAYER_SCREEN);
            });

            GAME_ENGINE.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
