package com.engine2d;

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
import java.util.*;
//END IMPORTS

public class Audio
{
	private AudioDevice playbackDevice;
	private AudioRecorder inputDevice;
	
	private volatile float[][] Buffers;
	private volatile int BufferIndex = 0;
	private Thread OutputThread;
	
	public int SampleRate;
	public int BufferSize = 512;
	public volatile boolean IsRunning = false;
	public volatile boolean BufferInUse= false;
	public volatile int BufferCount = 4;
	
	public Audio() { this.SampleRate = 44100; }
	
	public Audio(int rate)
	{
		this.SampleRate = rate;
	}
	
	public Audio(int rate, int bufSize)
	{
		this(rate);
		this.BufferSize = bufSize;
	}
	
	public void Init()
	{
		playbackDevice = Gdx.audio.newAudioDevice(SampleRate, true);
		inputDevice = Gdx.audio.newAudioRecorder(SampleRate, true);

		this.Buffers = new float[this.BufferCount][];
		for (int i = 0; i < this.BufferCount; i++)
		{
			this.Buffers[i] = new float[this.BufferSize];
		}
		
		OutputThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				outputWriter();
			}
		});
		
		this.IsRunning = true;
		OutputThread.start();
	}
	
	public void Dispose()
	{
		playbackDevice.dispose();
		inputDevice.dispose();
	}
	
	private void outputWriter()
	{
		/*
			The audio writer will run inside of the while loop1 inside 
			another while loop that only runs
			when the IsRunning bool is true,
			otherwise it will repeatedly skip the audio writter loop.
			This way thr audii writer will never stop
			and can be paused or resume.
		*/
		
		while (true)
		{
			while (this.IsRunning == true)
			{
				//if (Buffer1Empty == false)
				{
					BufferInUse = true;
					
					synchronized (this.Buffers[this.BufferIndex])
					{
						this.Output(this.Buffers[this.BufferIndex]);
						nextBuffer();
					}
					
					BufferInUse = false;
					
					//this.Buffer1 = null;
					//this.Buffer2 = null;
				}
			}
		}
	}
	
	private void nextBuffer()
	{
		//this.Buffers[this.BufferIndex] = null;
		
		if (BufferInUse == false)
		{
			if (this.BufferIndex == 3)
			{
				this.BufferIndex = 0;
			}
			else
			{
				this.BufferIndex += 1;
			}
		}
	}
	
	public boolean Write(float[] samples)
	{
		if (samples.length == this.BufferSize)
		{
			//while (BufferInUse == true)
			//{}
			if (this.BufferInUse == true) { return false; }
			
			
			this.Buffers[this.BufferIndex] = samples;
			
//			if (BufferInUse == false)
//			{
//				if (this.BufferIndex == 3)
//				{
//					this.BufferIndex = 0;
//				}
//				else
//				{
//					this.BufferIndex += 1;
//				}
//			}
		}
		else
		{
			return false;
		}
		return true;
	}
	
	public void Resume()
	{
		this.IsRunning = true;
	}
	
	public void Pause()
	{
		this.IsRunning = false;
	}
	
	private void Output(float[] samples)
	{
		playbackDevice.writeSamples(samples, 0, samples.length);
	}
	
	private void Output(short[] samples)
	{
		playbackDevice.writeSamples(samples, 0, samples.length);
	}
	
	public short[] Input(int length)
	{
		short[] Result = new short[length];
		inputDevice.read(Result, 0, length);
		
		return Result;
	}
}
