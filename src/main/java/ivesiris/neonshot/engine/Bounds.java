package ivesiris.neonshot.engine;

import org.joml.Vector2f;

public class Bounds
{

    public final float minX;
    public final float minY;
    public final float maxX;
    public final float maxY;

    public Bounds(float minX, float minY, float maxX, float maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public float getMinX()
    {
        return minX;
    }

    public float getMinY()
    {
        return minY;
    }

    public float getMaxX()
    {
        return maxX;
    }

    public float getMaxY()
    {
        return maxY;
    }

    public boolean isPointInside(float x, float y)
    {
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY;
    }

    public boolean isPointInside(Vector2f vec)
    {
        return isPointInside(vec.x, vec.y);
    }

    public boolean doesIntersectWith(Bounds other)
    {
        return other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY;
    }

}
