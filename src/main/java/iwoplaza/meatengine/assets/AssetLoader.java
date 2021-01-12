package iwoplaza.meatengine.assets;

import iwoplaza.meatengine.IDisposable;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AssetLoader implements IAssetLoader, IDisposable
{
    private Map<AssetLocation, IAsset> registeredAssets = new HashMap<>();
    private Queue<IAsset> assetsToLoad = new LinkedList<>();
    private boolean assetsHaveBeenPreloaded = false;

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

        if (assetsHaveBeenPreloaded) {
            asset.load();
        }
        else {
            assetsToLoad.add(asset);
        }
    }

    /**
     * This method MUST be called at some point. Otherwise, all assets will be waiting to be preloaded.
     * @throws IOException
     */
    public void preloadAssets() throws IOException
    {
        while (!assetsToLoad.isEmpty())
        {
            IAsset assetToLoad = assetsToLoad.remove();
            assetToLoad.load();
        }

        assetsHaveBeenPreloaded = true;
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
