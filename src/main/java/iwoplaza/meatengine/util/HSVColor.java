package iwoplaza.meatengine.util;

public class HSVColor implements IColorc
{
    private float r;
    private float g;
    private float b;
    private float a;

    private float hue;
    private float saturation;
    private float value;

    private HSVColor(float h, float s, float v, float a)
    {
        this.set(r, g, b, a);
    }

    private HSVColor(float h, float s, float v)
    {
        this(h, s, v, 1);
    }

    public float h()
    {
        return this.hue;
    }

    public float s()
    {
        return this.saturation;
    }

    public float v()
    {
        return this.value;
    }

    public void set(float h, float s, float v, float a)
    {
        this.hue = h % 1.0f;
        this.saturation = s;
        this.value = v;
        this.a = a;

        float c = (1 - Math.abs(2 * this.value - 1)) * this.saturation;
        float x = c * (1 - Math.abs((this.hue * 6) % 2 - 1));
        float m = this.value - c / 2;

        this.r = m;
        this.g = m;
        this.b = m;

        if (this.hue < 1 / 6.0f)
        {
            this.r += c;
            this.g += x;
        }
        else if (this.hue < 2 / 6.0f)
        {
            this.r += x;
            this.g += c;
        }
        else if (this.hue < 3 / 6.0f)
        {
            this.g += c;
            this.b += x;
        }
        else if (this.hue < 4 / 6.0f)
        {
            this.g += x;
            this.b += c;
        }
        else if (this.hue < 5 / 6.0f)
        {
            this.r += x;
            this.b += c;
        }
        else
        {
            this.r += c;
            this.b += x;
        }
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
