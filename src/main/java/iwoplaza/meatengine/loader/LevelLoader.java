package iwoplaza.meatengine.loader;

import de.matthiasmann.twl.utils.PNGDecoder;
import iwoplaza.meatengine.world.World;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class LevelLoader
{
    private final ColorResponse colorResponse;

    public LevelLoader(ColorResponse response)
    {
        this.colorResponse = response;
    }

    @FunctionalInterface
    public interface ColorResponse
    {
        void respondToColor(World world, int x, int y, int color);
    }

    public World loadFromStream(InputStream stream) throws IOException
    {
        PNGDecoder decoder = new PNGDecoder(stream);
        int width = decoder.getWidth();
        int height = decoder.getHeight();
        ByteBuffer buf = ByteBuffer.allocateDirect(3 * width * height);
        decoder.decode(buf, width * 3, PNGDecoder.Format.RGB);

        return this.loadFromBuffer(buf, width, height);
    }

    public World loadFromBuffer(ByteBuffer buf, int width, int height)
    {
        World world = new World(width, height);

        for (int i = 0; i < width * height; i++)
        {
            int x = i % width;
            int y = i / height;
            int color = 0;
            color |= (buf.get((y * width + x) * 3) & 0xFF) << 16;
            color |= (buf.get((y * width + x) * 3 + 1) & 0xFF) << 8;
            color |= (buf.get((y * width + x) * 3 + 2) & 0xFF);

            colorResponse.respondToColor(world, x, height - 1 - y, color);
        }

        return world;
    }
}
