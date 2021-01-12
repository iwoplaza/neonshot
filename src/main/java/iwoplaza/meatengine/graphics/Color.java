package iwoplaza.meatengine.graphics;

public class Color
{
    private float r;
    private float g;
    private float b;
    private float a;

    public Color(float r, float g, float b, float a)
    {
        this.set(r, g, b, a);
    }

    public void set(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR()
    {
        return r;
    }

    public float getG()
    {
        return g;
    }

    public float getB()
    {
        return b;
    }

    public float getA()
    {
        return a;
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

    public void set(Color color)
    {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }
}
