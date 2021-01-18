package iwoplaza.meatengine.util;

import java.util.Objects;

public class Color implements IColorc
{
    private float r;
    private float g;
    private float b;
    private float a;

    public Color(float r, float g, float b, float a)
    {
        this.set(r, g, b, a);
    }

    public Color(float r, float g, float b)
    {
        this(r, g, b, 1);
    }

    public void set(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setR(float r)
    {
        this.r = r;
    }

    public void setG(float g)
    {
        this.g = g;
    }

    public void setB(float b)
    {
        this.b = b;
    }

    public void setA(float a)
    {
        this.a = a;
    }

    public void set(IColorc color)
    {
        this.r = color.r();
        this.g = color.g();
        this.b = color.b();
        this.a = color.a();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Float.compare(color.r, r) == 0 &&
                Float.compare(color.g, g) == 0 &&
                Float.compare(color.b, b) == 0 &&
                Float.compare(color.a, a) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(r, g, b, a);
    }

    @Override
    public float r()
    {
        return this.r;
    }

    @Override
    public float g()
    {
        return this.g;
    }

    @Override
    public float b()
    {
        return this.b;
    }

    @Override
    public float a()
    {
        return this.a;
    }
}
