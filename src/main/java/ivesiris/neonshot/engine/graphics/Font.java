package ivesiris.neonshot.engine.graphics;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Font
{

    public final Texture texture;
    public final int textureWidth;
    public final int textureHeight;
    private final Map<Integer, Character> characterMap;

    public Font(String textureName, int textureWidth, int textureHeight) throws IOException
    {
        this.texture = new Texture("/fonts/" + textureName);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.characterMap = new HashMap<>();
    }

    public void addCharacter(int ascii, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance)
    {
        this.characterMap.put(ascii, new Character(ascii, x, y, width, height, xOffset, yOffset, xAdvance));
    }

    public Character getChar(int index)
    {
        return characterMap.get(index);
    }

    public int getTextWidth(String text, TextRenderOptions options)
    {
        final int length = text.length();
        int width = 0;

        for (int i = 0; i < length; ++i)
        {
            int c = text.charAt(i);
            Character character = this.characterMap.get(c);
            if (character != null)
            {
                width += character.xAdvance;
                if (i != length - 1)
                    width += options.letterSpacing;
            }
        }

        return width;
    }

    public static class Character
    {

        public final int ascii;
        public final float x, y;
        public final float width, height;
        public final float xOffset, yOffset;
        public final float xAdvance;

        public Character(int ascii, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance)
        {
            this.ascii = ascii;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xAdvance = xAdvance;
        }

    }

}
