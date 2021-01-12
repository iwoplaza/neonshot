package iwoplaza.meatengine.assets;

import java.io.IOException;

public interface IAssetLoader
{
    void registerAsset(IAsset asset) throws IOException;
}
