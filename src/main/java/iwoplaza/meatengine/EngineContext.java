package iwoplaza.meatengine;

public class EngineContext implements IEngineContext
{
    private final float updateInterval;
    private float deltaTime;
    private float partialTicks;

    public EngineContext(float updateInterval)
    {
        this.updateInterval = updateInterval;
        this.deltaTime = 0;
        this.partialTicks = 0;
    }

    public void updateTime(float deltaTime, float partialTicks)
    {
        this.deltaTime = deltaTime;
        this.partialTicks = partialTicks;
    }

    @Override
    public float getUpdateInterval()
    {
        return updateInterval;
    }

    @Override
    public float getDeltaTime()
    {
        return this.deltaTime;
    }

    @Override
    public float getPartialTicks()
    {
        return this.partialTicks;
    }

}
