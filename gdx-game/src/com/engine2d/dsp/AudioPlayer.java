/*
 *      _______                       _____   _____ _____  
 *     |__   __|                     |  __ \ / ____|  __ \ 
 *        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
 *        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
 *        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
 *        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
 *                                                         
 * -------------------------------------------------------------
 *
 * TarsosDSP is developed by Joren Six at IPEM, University Ghent
 *  
 * -------------------------------------------------------------
 *
 *  Info: http://0110.be/tag/TarsosDSP
 *  Github: https://github.com/JorenSix/TarsosDSP
 *  Releases: http://0110.be/releases/TarsosDSP/
 *  
 *  TarsosDSP includes modified source code by various authors,
 *  for credits and info, see README.
 * 
 */


package com.engine2d.dsp;

import javax.sound.sampled.AudioFormat;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.*;
import com.engine2d.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;

/**
 * This AudioProcessor can be used to sync events with sound. It uses a pattern
 * described in JavaFX Special Effects Taking Java RIA to the Extreme with
 * Animation, Multimedia, and Game Element Chapter 9 page 185: <blockquote><i>
 * The variable LineWavelet is the Java Sound object that actually makes the sound. The
 * write method on LineWavelet is interesting because it blocks until it is ready for
 * more data. </i></blockquote> If this AudioProcessor chained with other
 * AudioProcessors the others should be able to operate in real time or process
 * the signal on a separate thread.
 * 
 * @author Joren Six
 */
public final class AudioPlayer implements AudioProcessor
{
	private final AudioFormat format;
	private AudioDevice playbackDevice;
	public boolean NeedsSleepTime = true;
	/**
	 * Creates a new audio player.
	 * 
	 * @param format
	 *            The AudioFormat of the buffer.
	 * @throws LineUnavailableException
	 *             If no output LineWavelet is available.
	 */
	public AudioPlayer(final AudioFormat format)
	{
		this.format = format;
		playbackDevice = Gdx.audio.newAudioDevice((int)format.getSampleRate(), true);
	}

	@Override
	public Object[] process(AudioEvent audioEvent)
	{
		playbackDevice.writeSamples(audioEvent.getFloatBuffer(), 0, audioEvent.getFloatBuffer().length);
		
		//line.write(audioEvent.getByteBuffer(), byteOverlap, byteStepSize);
		
		return new Object[] { true, audioEvent.getFloatBuffer() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see be.tarsos.util.RealTimeAudioProcessor.AudioProcessor#
	 * processingFinished()
	 */
	public void processingFinished()
	{
		// cleanup
		
		playbackDevice.dispose();

	}
}
