package iwoplaza.meatengine.loader;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.graphics.Texture;
import iwoplaza.meatengine.graphics.font.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontLoader
{
    private Map<String, Object> variables;

    public FontLoader()
    {
        this.variables = new HashMap<>();
    }

    public Font load(String fontData, ITextureGetter textureGetter) throws IOException
    {
        int variablesDefEnd = fontData.indexOf("\nchars count");

        String variableDef = fontData.substring(5, variablesDefEnd);

        int i = 0;
        while (i < variableDef.length())
        {
            int equalsIndex = variableDef.indexOf('=', i);
            if (equalsIndex == -1)
                break;

            String key = variableDef.substring(i, equalsIndex);
            Object value = null;
            if (variableDef.charAt(equalsIndex+1) == '"')
            {
                int endQuoteIndex = variableDef.indexOf('"', equalsIndex+2);
                value = variableDef.substring(equalsIndex+2, endQuoteIndex);
                i = endQuoteIndex + 1;
            }
            else
            {
                int spaceIndex = variableDef.indexOf(' ', equalsIndex);
                String strValue = variableDef.substring(equalsIndex+1, spaceIndex);
                try
                {
                    value = Integer.parseInt(strValue);
                }
                catch(NumberFormatException ex)
                {
                    value = strValue;
                }
                i = spaceIndex + 1;
            }
            variables.put(key, value);
        }

        Font font = new Font(textureGetter.getTexture(getString("file")));
        List<String> lines = new ArrayList<>();

        i = fontData.indexOf("char id=");
        while (i != -1)
        {
            lines.add(fontData.substring(i, fontData.indexOf('\n', i)));
            i = fontData.indexOf("char id=", i+1);
        }

        for (String line : lines)
        {
            int[] values = new int[8];

            int seek = 0;
            for (int j = 0; j < values.length; j++)
            {
                int equalIndex = line.indexOf('=', seek);
                int spaceIndex = line.indexOf(' ', equalIndex);
                String substr = line.substring(equalIndex+1, spaceIndex);
                values[j] = Integer.parseInt(substr);

                seek = spaceIndex;
            }

            font.addCharacter(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
        }

        return font;
    }

    private String getString(String key)
    {
        return (String) this.variables.get(key);
    }

    private int getInt(String key)
    {
        return (int) this.variables.get(key);
    }

    public static Font loadFontResource(AssetLocation location) throws IOException
    {
        AssetLocation directory = location.getDirectory();
        FontLoader fontLoader = new FontLoader();

        return fontLoader.load(
                ResourceLoader.loadStringResource(location.getResourcePath()),
                filename -> Texture.createFromStream(directory.getRelative(filename).getInputStream())
        );
    }

    @FunctionalInterface
    private interface ITextureGetter
    {
        Texture getTexture(String filename) throws IOException;
    }
}
