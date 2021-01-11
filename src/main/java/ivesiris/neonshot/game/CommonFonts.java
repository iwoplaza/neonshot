package ivesiris.neonshot.game;

import ivesiris.neonshot.engine.graphics.Font;
import ivesiris.neonshot.engine.loader.FontLoader;

public class CommonFonts
{

    public static Font georgia;

    public static void loadFonts() throws Exception
    {
        georgia = FontLoader.loadFontResource("/fonts/georgia.fnt");
    }

}