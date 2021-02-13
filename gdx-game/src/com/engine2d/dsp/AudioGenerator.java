package com.engine2d.dsp;

//IMPORTS
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.*;
import com.badlogic.gdx.assets.loaders.*;
import com.bitfire.postprocessing.*;
import com.bitfire.postprocessing.effects.*;
import com.bitfire.utils.*;
import java.time.*;
import org.apache.commons.codec.language.bm.*;
import java.lang.*;
import com.badlogic.gdx.audio.*;
import com.engine2d.UI.*;
import com.engine2d.events.IEvent;
import com.badlogic.gdx.backends.android.AndroidApplication;
import android.content.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

//import be.tarsos.dsp.AudioGenerator;
import be.tarsos.dsp.effects.DelayEffect;
import be.tarsos.dsp.effects.FlangerEffect;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.synthesis.AmplitudeLFO;
import be.tarsos.dsp.synthesis.NoiseGenerator;
import be.tarsos.dsp.synthesis.SineGenerator;

import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import java.lang.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.nio.ByteOrder;
import com.engine2d.dsp.Utils;
//END IMPORTS

public class AudioGenerator implements Runnable
{
	public Thread RunThread;
	
	/**
	 * A list of registered audio processors. The audio processors are
	 * responsible for actually doing the digital signal processing
	 */
	private final List<com.engine2d.dsp.AudioProcessor> audioProcessors;
	private final TarsosDSPAudioFormat format;
	private final int samplesProcessed;
	private final float[] audioBuffer;

	/**
	 * The audio event that is send through the processing chain.
	 */
	private AudioEvent audioEvent;

	/**
	 * If true the dispatcher stops dispatching audio.
	 */
	private boolean stopped;

	/**
	 * Create a new generator.
	 * @param audioBufferSize
	 *            The size of the buffer defines how much samples are processed
	 *            in one step. Common values are 1024,2048.
	 *
	 **/
	public AudioGenerator(final int audioBufferSize, final int samplerate)
	{

		audioProcessors = new CopyOnWriteArrayList<com.engine2d.dsp.AudioProcessor>();

		format = getTargetAudioFormat(samplerate);

		//setStepSizeAndOverlap(audioBufferSize, bufferOverlap);

		audioEvent = new AudioEvent(format);

		audioBuffer = new float[audioBufferSize];
		audioEvent.setFloatBuffer(audioBuffer);

		stopped = false;

		samplesProcessed = 0;
		
		
		
		RunThread = new Thread(this);
	}

	/**
	 * Constructs the target audio format. The audio format is one channel
	 * signed PCM of a given sample rate.
	 * 
	 * @param targetSampleRate
	 *            The sample rate to convert to.
	 * @return The audio format after conversion.
	 */
	private TarsosDSPAudioFormat getTargetAudioFormat(int targetSampleRate)
	{
		TarsosDSPAudioFormat audioFormat = new TarsosDSPAudioFormat(TarsosDSPAudioFormat.Encoding.PCM_SIGNED, 
																	targetSampleRate, 
																	2 * 8, 
																	1, 
																	2 * 1, 
																	targetSampleRate, 
																	ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()));
		return audioFormat;
	}

	/**
	 * Adds an AudioProcessor to the chain of processors.
	 * 
	 * @param audioProcessor
	 *            The AudioProcessor to add.
	 */
	public void addAudioProcessor(final com.engine2d.dsp.AudioProcessor audioProcessor)
	{
		audioProcessors.add(audioProcessor);
	}

	/**
	 * Removes an AudioProcessor to the chain of processors and calls processingFinished.
	 * 
	 * @param audioProcessor
	 *            The AudioProcessor to remove.
	 */
	public void removeAudioProcessor(final com.engine2d.dsp.AudioProcessor audioProcessor)
	{
		audioProcessors.remove(audioProcessor);
		audioProcessor.processingFinished();
	}
	
	public void clearAudioProcessors()
	{
		for (AudioProcessor i : audioProcessors)
		{
			i.processingFinished();
		}
		
		audioProcessors.clear();
	}

	@Override
	public void run()
	{
		audioProcessors.add(new AudioPlayer( new AudioFormat(44100, 16, 1, true, false)));
		
		// As long as the stream has not ended
		while (stopped == false)
		{

			//audioEvent.setFloatBuffer(new float[(int)audioEvent.getSampleRate()]);
			
			//Makes sure the right buffers are processed, they can be changed by audio processors.
			for (final com.engine2d.dsp.AudioProcessor processor : audioProcessors)
			{
				Object[] res = processor.process(audioEvent);
				
				if (processor.NeedsSleepTime == true)
				{
					int milis = ((int)format.getSampleRate() / audioEvent.getFloatBuffer().length) * 100;
					try{ Thread.sleep(Utils.convertSampleLengthToMiliseconds(audioEvent.getFloatBuffer().length, (int)format.getSampleRate())); } catch (Exception ex) {}
				}
				
				if ((boolean)res[0] == false)
				{
					//skip to the next audio processors if false is returned.
					audioEvent.clearFloatBuffer();
					continue;
				}
				else
				{
					
				}
			}
			
			if (stopped == false)
			{
				
				audioEvent.setBytesProcessed(samplesProcessed * format.getFrameSize());

				// Read, convert and process consecutive overlapping buffers.
				// Slide the buffer.
			}
		}
	}
}
