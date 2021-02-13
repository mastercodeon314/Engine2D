package com.engine2d.dsp;

import com.engine2d.dsp.synth.*;
import com.engine2d.dsp.musical.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

public class DspEngine
{
	public float SampleRate;
	AudioGenerator generator;
	AudioPlayer player;
	public DspEngine(float sampleRate)
	{
		this.SampleRate = sampleRate;
		
		generator = new AudioGenerator(1024, 0);
		player = new AudioPlayer(new AudioFormat(44100, 16, 1, true, false));
	}
}
