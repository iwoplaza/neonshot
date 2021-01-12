package iwoplaza.meatengine.assets;

import iwoplaza.meatengine.IDisposable;

import java.io.IOException;

public interface IAsset extends IDisposable
{
    AssetLocation getLocation();

    void load() throws IOException;

    void inheritFrom(IAsset asset);
}
