package ivesiris.neonshot.engine.loader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ivesiris.neonshot.engine.lang.LocalizationLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}
