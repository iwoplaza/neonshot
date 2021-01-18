package iwoplaza.meatengine.loader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import iwoplaza.meatengine.lang.LocalizationLoader;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class ResourceLoader
{
    public static String loadStringResource(String path)
    {
        String result = null;

        try (InputStream in = ResourceLoader.class.getResourceAsStream(path);
             Scanner scanner = new Scanner(in, "UTF-8"))
        {
            result = scanner.useDelimiter("\\A").next();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T loadJSONResource(String path, Class<T> classToLoad)
    {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(ResourceLoader.class.getResourceAsStream(path)));
        return gson.fromJson(reader, classToLoad);
    }

    public static <T> List<T> loadJSONResourcesFromDirectory(String directoryPath, Class<T> classToLoad) throws IOException
    {
        List<T> list = new ArrayList<T>();

        InputStream dirStream = LocalizationLoader.class.getResourceAsStream(directoryPath);
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(dirStream));
            String resource;

            while ((resource = br.readLine()) != null)
            {
                list.add(loadJSONResource(directoryPath + resource, classToLoad));
            }

            return list;
        }
        catch(NullPointerException e)
        {
            throw new IllegalArgumentException(String.format("The specified '%s' resource directory has not been found.", directoryPath));
        }
    }


    public static ByteBuffer resourceToByteBuffer(String resource, int bufferSize) throws IOException
    {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path))
        {
            try (SeekableByteChannel fc = Files.newByteChannel(path))
            {
                buffer = createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        }
        else
        {
            try (
                    InputStream source = ResourceLoader.class.getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source))
            {
                buffer = createByteBuffer(bufferSize);

                while (true)
                {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1)
                    {
                        break;
                    }
                    if (buffer.remaining() == 0)
                    {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)
    {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}
