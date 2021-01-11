package ivesiris.neonshot.engine.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL43.glCopyImageSubData;

public class Texture
{

    private final int width;
    private final int height;
    private final int textureId;

    public Texture(String fileName) throws IOException
    {
        PNGDecoder decoder = new PNGDecoder(
                Texture.class.getResourceAsStream(fileName)
        );
        this.width = decoder.getWidth();
        this.height = decoder.getHeight();
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * this.width * this.height);
        decoder.decode(buf, this.width * 4, PNGDecoder.Format.RGBA);
        buf.flip();

        this.textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
    }

    public Texture(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
    }

    public void cleanUp()
    {
        glDeleteTextures(textureId);
    }

    public int getId()
    {
        return textureId;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void bind()
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void bind(int textureSlot)
    {
        glActiveTexture(GL_TEXTURE0 + textureSlot);
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void copyDataFrom(int otherTextureId)
    {
        glCopyImageSubData(
                otherTextureId, GL_TEXTURE_2D, 0,
                0, 0, 0,
                this.textureId, GL_TEXTURE_2D, 0,
                0, 0, 0,
                this.width, this.height,
                1);
    }

}
