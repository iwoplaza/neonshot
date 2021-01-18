package iwoplaza.meatengine.audio;

import iwoplaza.meatengine.assets.SoundAsset;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class SoundSource
{

    private final int sourceId;

    public SoundSource(boolean loop, boolean relative)
    {
        this.sourceId = alGenSources();

        if (loop)
            alSourcei(sourceId, AL_LOOPING, AL_TRUE);

        if (relative)
            alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
    }

    public int getSourceId()
    {
        return this.sourceId;
    }

    public void setBuffer(SoundAsset soundAsset)
    {
        this.setBuffer(soundAsset.getSound());
    }

    public void setBuffer(SoundBuffer buffer)
    {
        this.setBuffer(buffer.getBufferId());
    }

    public void setBuffer(int bufferId)
    {
        stop();
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    public void setPosition(Vector3f position)
    {
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }

    public void setPosition(float x, float y, float z)
    {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }

    public void setSpeed(Vector3f speed)
    {
        alSource3f(sourceId, AL_VELOCITY, speed.x, speed.y, speed.z);
    }

    public void setGain(float gain)
    {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void setPitch(float pitch)
    {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public void setProperty(int param, float value)
    {
        alSourcef(sourceId, param, value);
    }

    public void play()
    {
        alSourcePlay(sourceId);
    }

    public boolean isPlaying()
    {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void pause()
    {
        alSourcePause(sourceId);
    }

    public void stop()
    {
        alSourceStop(sourceId);
    }

    public void cleanUp()
    {
        stop();
        alDeleteSources(sourceId);
    }

}
