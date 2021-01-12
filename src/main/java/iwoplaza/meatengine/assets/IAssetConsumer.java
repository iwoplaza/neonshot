package iwoplaza.meatengine.assets;

import java.io.IOException;

@FunctionalInterface
public interface IAssetConsumer
{
    void registerAssets(IAssetLoader loader) throws IOException;
}
