package iwoplaza.meatengine.util;

public class Easing
{
    public static float easeIn(float t)
    {
        return t * t;
    }

    public static float easeOut(float t)
    {
        t = 1 - t;
        t *= t;
        return 1 - t;
    }

    public static float easeInOut(float t)
    {
        t *= 2;

        if (t < 1)
        {
            return easeIn(t) * 0.5f;
        }
        else
        {
            return 0.5f + easeOut(t - 1) * 0.5f;
        }
    }
}
