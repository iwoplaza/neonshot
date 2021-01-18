package iwoplaza.meatengine.assets;

import iwoplaza.meatengine.audio.SoundBuffer;

import java.io.IOException;

public class SoundAsset extends Asset
{
    private SoundBuffer soundBuffer;

    public SoundAsset(AssetLocation location)
    {
        super(location);
    }

    public SoundBuffer getSound()
    {
        return soundBuffer;
    }

    @Override
    public void load() throws IOException
    {
        this.soundBuffer = new SoundBuffer(this.location.getResourcePath());
    }

    @Override
    public void inheritFrom(IAsset asset)
    {
        SoundAsset src = (SoundAsset) asset;
        this.soundBuffer = src.soundBuffer;
    }

    @Override
    public void dispose()
    {
        if (this.soundBuffer != null)
        {
            this.soundBuffer.dispose();
            this.soundBuffer = null;
        }
    }

}
