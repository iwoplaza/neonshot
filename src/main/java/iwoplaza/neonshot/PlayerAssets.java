package iwoplaza.neonshot;

import iwoplaza.meatengine.assets.AssetLocation;
import iwoplaza.meatengine.assets.IAssetConsumer;
import iwoplaza.meatengine.assets.IAssetLoader;
import iwoplaza.meatengine.assets.SoundAsset;

import java.io.IOException;

public class PlayerAssets implements IAssetConsumer
{
    public static final PlayerAssets INSTANCE = new PlayerAssets();

    public SoundAsset shotSound;
    public SoundAsset shuffleSound;

    @Override
    public void registerAssets(IAssetLoader loader) throws IOException
    {
        loader.registerAsset(shotSound = new SoundAsset(
                AssetLocation.asResource(Statics.RES_ORIGIN, "sfx/shot.ogg")
        ));

        loader.registerAsset(shuffleSound = new SoundAsset(
                AssetLocation.asResource(Statics.RES_ORIGIN, "sfx/shuffle.ogg")
        ));
    }
}
