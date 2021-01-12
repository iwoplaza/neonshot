package iwoplaza.meatengine.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import iwoplaza.meatengine.IDisposable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL43.glCopyImageSubData;

public class Texture implements IDisposable
{

    private final int width;
    private final int height;
    private final int textureId;

    public Texture(int width, int height, ByteBuffer buf)
    {
        this.width = width;
        this.height = height;
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

    @Override
    public void dispose()
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

    public static Texture createFromStream(InputStream stream) throws IOException
    {
        PNGDecoder decoder = new PNGDecoder(stream);
        int width = decoder.getWidth();
        int height = decoder.getHeight();
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * width * height);
        decoder.decode(buf, width * 4, PNGDecoder.Format.RGBA);
        buf.flip();

        return new Texture(width, height, buf);
    }

}
