package iwoplaza.meatengine.assets;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

/**
 * It's assumed that every asset is a resource. (for now)
 */
public class AssetLocation
{
    private String path;

    private AssetLocation(String path)
    {
        this.path = path;
    }

    public InputStream getInputStream()
    {
        return Asset.class.getResourceAsStream(this.path);
    }

    public String getResourcePath()
    {
        return this.path;
    }

    public AssetLocation getDirectory()
    {
        int lastSlash = this.path.lastIndexOf("/");
        return new AssetLocation(this.path.substring(0, lastSlash + 1));
    }

    public AssetLocation getRelative(String relativePath)
    {
        return new AssetLocation(this.path + relativePath);
    }

    @Override
    public String toString()
    {
        return this.path;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.path);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AssetLocation))
        {
            return false;
        }

        AssetLocation other = (AssetLocation) obj;
        return this.path.equals(other.path);
    }

    public static AssetLocation asResource(String path) throws FileNotFoundException
    {
        return asResource("meatengine", path);
    }

    public static AssetLocation asResource(String origin, String path) throws FileNotFoundException
    {
        AssetLocation location = new AssetLocation(String.format("/iwoplaza/%s/%s", origin, path));
        if (location.getInputStream() == null)
        {
            throw new FileNotFoundException(String.format("The '%s' resource doesn't seem to exist.", location.toString()));
        }
        return location;
    }
}
