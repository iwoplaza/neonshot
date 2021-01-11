package ivesiris.neonshot.engine;

public class EngineContext
{
    private float deltaTime;
    private float partialTicks;

    public EngineContext()
    {
        this.deltaTime = 0;
        this.partialTicks = 0;
    }

    public void updateTime(float deltaTime, float partialTicks)
    {
        this.deltaTime = deltaTime;
        this.partialTicks = partialTicks;
    }

    public float getDeltaTime()
    {
        return this.deltaTime;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }
}
