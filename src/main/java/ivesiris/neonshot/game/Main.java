package ivesiris.neonshot.game;

import ivesiris.neonshot.engine.GameEngine;
import ivesiris.neonshot.game.screen.TitleScreen;

public class Main
{
    public static GameEngine GAME_ENGINE;
    public static TitleScreen TITLE_SCREEN;

    public static void main(String[] args)
    {
        try
        {
            TITLE_SCREEN = new TitleScreen();
            GAME_ENGINE = new GameEngine("NeonShot",
                    900, 600, true);

            GAME_ENGINE.addInitFunction(() -> {
                CommonShaders.loadShaders();
                CommonFonts.loadFonts();

                GAME_ENGINE.getLocalizer().setLanguage("us_en");
            });

            GAME_ENGINE.registerScreens(TITLE_SCREEN);
            GAME_ENGINE.showScreen(TITLE_SCREEN);
            GAME_ENGINE.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
