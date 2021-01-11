package ivesiris.neonshot.engine.helper;

import java.util.List;

public class ArrayHelper
{
    public static float[] listToFloatArray(List<Float> list)
    {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); ++i)
            array[i] = list.get(i);
        return array;
    }

    public static int[] listToIntArray(List<Integer> list)
    {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); ++i)
            array[i] = list.get(i);
        return array;
    }
}
