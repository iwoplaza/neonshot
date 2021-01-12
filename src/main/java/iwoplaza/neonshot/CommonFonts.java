package iwoplaza.neonshot;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.graphics.font.Font;
import iwoplaza.meatengine.loader.FontLoader;

public class CommonFonts
{

    public static Font georgia;

    public static void loadFonts() throws Exception
    {
        georgia = FontLoader.loadFontResource(AssetLocation.asResource(Statics.RES_ORIGIN, "fonts/georgia.fnt"));
    }

}