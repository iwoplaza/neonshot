package iwoplaza.meatengine.assets;

import iwoplaza.meatengine.graphics.Texture;

import java.io.IOException;

public class TextureAsset extends Asset
{
    private Texture texture;

    public TextureAsset(AssetLocation location)
    {
        super(location);
    }

    public void bind()
    {
        this.texture.bind();
    }

    public Texture getTexture()
    {
        return texture;
    }

    public int getWidth()
    {
        return this.texture.getWidth();
    }

    public int getHeight()
    {
        return this.texture.getHeight();
    }

    @Override
    public void load() throws IOException
    {
        this.texture = Texture.createFromStream(this.location.getInputStream());
    }

    @Override
    public void inheritFrom(IAsset asset)
    {
        TextureAsset src = (TextureAsset) asset;
        this.texture = src.texture;
    }

    @Override
    public void dispose()
    {
        if (this.texture != null)
        {
            this.texture.dispose();
            this.texture = null;
        }
    }

}
