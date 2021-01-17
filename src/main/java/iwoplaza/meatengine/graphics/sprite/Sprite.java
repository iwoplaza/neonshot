package iwoplaza.meatengine.graphics.sprite;

import iwoplaza.meatengine.assets.TextureAsset;
import iwoplaza.meatengine.graphics.Color;
import iwoplaza.meatengine.graphics.Texture;

public class Sprite
{
    private final TextureAsset texture;
    private final int frameWidth;
    private final int frameHeight;

    private int frameX = 0;
    private int frameY = 0;
    private final Color overlayColor = new Color(0, 0, 0, 0);

    public Sprite(TextureAsset texture, int frameWidth, int frameHeight)
    {
        this.texture = texture;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public void draw()
    {
        SpriteRenderer.INSTANCE.draw(this);
    }

    public void setOverlayColor(Color color)
    {
        this.overlayColor.set(color);
    }

    public int getFrameWidth()
    {
        return frameWidth;
    }

    public int getFrameHeight()
    {
        return frameHeight;
    }

    public int getFrameX()
    {
        return frameX;
    }

    public void setFrameX(int spriteFrameX)
    {
        this.frameX = spriteFrameX;
    }

    public int getFrameY()
    {
        return frameY;
    }

    public void setFrameY(int spriteFrameY)
    {
        this.frameY = spriteFrameY;
    }

    public Texture getTexture()
    {
        return this.texture.getTexture();
    }

    public Color getOverlayColor()
    {
        return overlayColor;
    }
}
