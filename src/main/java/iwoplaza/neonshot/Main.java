package iwoplaza.neonshot;

import iwoplaza.meatengine.GameEngine;
import iwoplaza.meatengine.assets.AssetLoader;
import iwoplaza.meatengine.graphics.entity.RendererRegistry;
import iwoplaza.neonshot.graphics.IGameRenderContext;
import iwoplaza.neonshot.graphics.entity.PlayerRenderer;
import iwoplaza.neonshot.screen.SinglePlayerScreen;
import iwoplaza.neonshot.screen.TitleScreen;
import iwoplaza.neonshot.world.entity.PlayerEntity;

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
                rendererRegistry.registerRenderer(PlayerEntity.class, new PlayerRenderer());
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

                assetLoader.preloadAssets();
            });

            GAME_ENGINE.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
