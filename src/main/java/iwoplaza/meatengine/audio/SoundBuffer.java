package iwoplaza.meatengine.audio;

import iwoplaza.meatengine.IDisposable;
import iwoplaza.meatengine.loader.ResourceLoader;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;

public class SoundBuffer implements IDisposable
{

    private ShortBuffer pcm = null;
    private ByteBuffer vorbis = null;
    private final int bufferId;

    public SoundBuffer(String fileName) throws IOException
    {
        this.bufferId = alGenBuffers();
        try (STBVorbisInfo info = STBVorbisInfo.malloc())
        {
            readVorbis(fileName, 32 * 1024, info);

            // Copy to buffer
            alBufferData(this.bufferId, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }
    }

    public int getBufferId()
    {
        return bufferId;
    }

    private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws IOException
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            vorbis = ResourceLoader.resourceToByteBuffer(resource, bufferSize);
            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_memory(vorbis, error, null);
            if (decoder == NULL)
            {
                throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
            }

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();

            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            pcm = MemoryUtil.memAllocShort(lengthSamples);

            pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
            stb_vorbis_close(decoder);

            return pcm;
        }
    }

    @Override
    public void dispose()
    {
        alDeleteBuffers(this.bufferId);
        if (pcm != null)
        {
            MemoryUtil.memFree(pcm);
        }
    }

}
