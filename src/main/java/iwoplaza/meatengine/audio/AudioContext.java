package iwoplaza.meatengine.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.AL10.alGetEnumValue;
import static org.lwjgl.openal.AL11.alSource3i;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.EXTEfx.*;

public class AudioContext
{

    public static AudioContext INSTANCE;
    private final long device;
    private final long context;

    private final SoundListener soundListener;

    private final int effect;
    private final int auxSlot;

    public AudioContext()
    {
        INSTANCE = this;

        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        this.device = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        this.context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        this.soundListener = new SoundListener();

        effect = alGenEffects();

        final float density = 0.1F;
        final float diffusion = 0.5F;
        final float gain = 0.5F;
        final float gainhf = 0.1F;
        final float gainlf = 0.9F;
        final float decayTime = 1.0F;
        final float decay_hfRatio = 0.7F;
        final float decay_lfRatio = 0.6F;
        final float reflectionsGain = 0.1F;
        final float reflectionsDelay = 0.05F;

        if(alGetEnumValue("AL_EFFECT_EAXREVERB") != 0)
        {
            System.out.println("Using EAX Reverb");
            /* EAX Reverb is available. Set the EAX effect type then load the
             * reverb properties. */
            alEffecti(effect, AL_EFFECT_TYPE, AL_EFFECT_EAXREVERB);
            alEffectf(effect, AL_EAXREVERB_DENSITY, density);
            alEffectf(effect, AL_EAXREVERB_DIFFUSION, diffusion);
            alEffectf(effect, AL_EAXREVERB_GAIN, gain);
            alEffectf(effect, AL_EAXREVERB_GAINHF, gainhf);
            alEffectf(effect, AL_EAXREVERB_GAINLF, gainlf);
            alEffectf(effect, AL_EAXREVERB_DECAY_TIME, decayTime);
            alEffectf(effect, AL_EAXREVERB_DECAY_HFRATIO, decay_hfRatio);
            alEffectf(effect, AL_EAXREVERB_DECAY_LFRATIO, decay_lfRatio);
            alEffectf(effect, AL_EAXREVERB_REFLECTIONS_GAIN, reflectionsGain);
            alEffectf(effect, AL_EAXREVERB_REFLECTIONS_DELAY, reflectionsDelay);
            alEffectf(effect, AL_EAXREVERB_LATE_REVERB_GAIN, 0.5F);
            alEffectf(effect, AL_EAXREVERB_LATE_REVERB_DELAY, 0.002F);
            alEffectf(effect, AL_EAXREVERB_ECHO_TIME, 0.25F);
            alEffectf(effect, AL_EAXREVERB_ECHO_DEPTH, 0.0F);
            alEffectf(effect, AL_EAXREVERB_MODULATION_TIME, 3.0F);
            alEffectf(effect, AL_EAXREVERB_MODULATION_DEPTH, 0.1F);
            alEffectf(effect, AL_EAXREVERB_AIR_ABSORPTION_GAINHF, 1.0F);
            alEffectf(effect, AL_EAXREVERB_HFREFERENCE, 5168F);
            alEffectf(effect, AL_EAXREVERB_LFREFERENCE, 140F);
            alEffectf(effect, AL_EAXREVERB_ROOM_ROLLOFF_FACTOR, 0.0F);
            alEffecti(effect, AL_EAXREVERB_DECAY_HFLIMIT, 1);
        }
        else
        {
            System.out.println("Using Standard Reverb");
            /* No EAX Reverb. Set the standard reverb effect type then load the
             * available reverb properties. */
            alEffecti(effect, AL_EFFECT_TYPE, AL_EFFECT_REVERB);
            alEffectf(effect, AL_REVERB_DENSITY, density);
            alEffectf(effect, AL_REVERB_DIFFUSION, diffusion);
            alEffectf(effect, AL_REVERB_GAIN, gain);
            alEffectf(effect, AL_REVERB_GAINHF, gainhf);
            alEffectf(effect, AL_REVERB_DECAY_TIME, decayTime);
            alEffectf(effect, AL_REVERB_DECAY_HFRATIO, decay_hfRatio);
            alEffectf(effect, AL_REVERB_REFLECTIONS_GAIN, reflectionsGain);
            alEffectf(effect, AL_REVERB_REFLECTIONS_DELAY, reflectionsDelay);
            alEffectf(effect, AL_REVERB_LATE_REVERB_GAIN, 1.1F);
            alEffectf(effect, AL_REVERB_LATE_REVERB_DELAY, 0.002F);
            alEffectf(effect, AL_REVERB_AIR_ABSORPTION_GAINHF, 1.0F);
            alEffectf(effect, AL_REVERB_ROOM_ROLLOFF_FACTOR, 0.0F);
            alEffecti(effect, AL_REVERB_DECAY_HFLIMIT, 1);
        }

        auxSlot = alGenAuxiliaryEffectSlots();
        alAuxiliaryEffectSloti(auxSlot, AL_EFFECTSLOT_EFFECT, effect);
    }

    public void dispose()
    {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public void connectSourceToEffect(SoundSource source)
    {
        alSource3i(source.getSourceId(), AL_AUXILIARY_SEND_FILTER, auxSlot, 0, AL_FILTER_NULL);
    }

    public SoundListener getSoundListener()
    {
        return soundListener;
    }

}
