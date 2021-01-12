package iwoplaza.meatengine.assets;

public abstract class Asset implements IAsset
{
    protected final AssetLocation location;

    protected Asset(AssetLocation location)
    {
        this.location = location;
    }

    @Override
    public AssetLocation getLocation()
    {
        return this.location;
    }
}
