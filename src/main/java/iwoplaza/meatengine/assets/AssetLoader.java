package iwoplaza.meatengine.assets;

import iwoplaza.meatengine.IDisposable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetLoader implements IAssetLoader, IDisposable
{
    private Map<AssetLocation, IAsset> registeredAssets = new HashMap<>();

    @Override
    public void registerAsset(IAsset asset) throws IOException
    {
        AssetLocation location = asset.getLocation();

        if (registeredAssets.containsKey(location))
        {
            asset.inheritFrom(registeredAssets.get(location));
            return;
        }

        registeredAssets.put(location, asset);
        asset.load();
    }

    @Override
    public void dispose()
    {
        for (IAsset asset : registeredAssets.values())
        {
            asset.dispose();
        }
    }
}
