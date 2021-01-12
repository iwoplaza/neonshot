package iwoplaza.meatengine.graphics.mesh;

import iwoplaza.meatengine.graphics.font.Font;
import iwoplaza.meatengine.helper.ArrayHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextMesh extends Mesh
{
    public TextMesh(int[] indices, float[] positions, float[] texCoords)
    {
        super(indices);

        this.bindVertexArray();
        createFloatVBO(positions, 2);
        createFloatVBO(texCoords, 2);
        this.unbindVertexArray();
    }

    public static TextMesh build(Font font, String text)
    {
        final int textureWidth = font.texture.getWidth();
        final int textureHeight = font.texture.getHeight();
        List<Integer> indices = new ArrayList<>();
        List<Float> positions = new ArrayList<>();
        List<Float> texCoords = new ArrayList<>();

        final int length = text.length();
        float x = 0;
        float y = 0;

        int indexOffset = 0;
        for (int i = 0; i < length; ++i)
        {
            char c = text.charAt(i);
            Font.Character character = font.getChar(c);
            if (character != null)
            {
                Collections.addAll(positions,
                        x, y + character.height,
                        x, y,
                        x + character.width, y,
                        x + character.width, y + character.height);

                float u0 = character.x / textureWidth;
                float v0 = character.y / textureHeight;
                float u1 = (character.x + character.width) / textureWidth;
                float v1 = (character.y + character.height) / textureHeight;

                Collections.addAll(indices, indexOffset, indexOffset + 1, indexOffset + 3, indexOffset + 3, indexOffset + 1, indexOffset + 2);
                Collections.addAll(texCoords,
                        u0, v0,
                        u0, v1,
                        u1, v1,
                        u1, v0);

                indexOffset += 4;
                x += character.xAdvance;
            }
        }

        return new TextMesh(
                ArrayHelper.listToIntArray(indices),
                ArrayHelper.listToFloatArray(positions),
                ArrayHelper.listToFloatArray(texCoords));
    }
}
