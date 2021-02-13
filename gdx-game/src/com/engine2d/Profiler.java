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
import java.text.DecimalFormat;
//END IMPORTS

public class Profiler
{
	public double timeElapsed;
	public double Fps = 0;
	public int fps1 = 0;


	// desired fps
	private final static int 	MAX_FPS = 50;
	// maximum number of frames to be skipped
	private final static int	MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

	// Stuff for stats */
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
	// we'll be reading the stats every second
	private final static int 	STAT_INTERVAL = 1000; //ms
	// the average will be calculated by storing
	// the last n FPSs
	private final static int	FPS_HISTORY_NR = 10;
	// last time the status was stored
	private long lastStatusStore = 0;
	// the status time counter
	private long statusIntervalTimer	= 0l;
	// number of frames skipped since the game started
	private long totalFramesSkipped			= 0l;
	// number of frames skipped in a store cycle (1 sec)
	private long framesSkippedPerStatCycle 	= 0l;

	// number of rendered frames in an interval
	private int frameCountPerStatCycle = 0;
	private long totalFrameCount = 0l;
	// the last FPS values
	private double 	fpsStore[];
	// the number of times the stat has been read
	private long 	statsCount = 0;
	// the average FPS since the game started
	private double 	averageFps = 0.0;

	public double actualFps = 0.0;

	double beginTime;		// the time when the cycle begun
	double timeDiff;		// the time it took for the cycle to execute
	double sleepTime;		// ms to sleep (<0 if we're behind)
	int framesSkipped;	// number of frames being skipped 


	public float CpuUsage = 0f;
	//private CpuUtil cpu;

	final Runtime runtime = Runtime.getRuntime();
	public long MemoryUsed;
	public long MaxMemoryUsable;
	public long MemoryFree;

	public Profiler()
	{
		//cpu = new CpuUtil();
		//cpu.startCpuMonitoring();
		initTimingElements();
	}

	private void initTimingElements()
	{
		
		// initialise timing elements
		fpsStore = new double[FPS_HISTORY_NR];
		for (int i = 0; i < FPS_HISTORY_NR; i++)
		{
			fpsStore[i] = 0.0;
		}
	}

	public void Start()
	{
		this.fps1 = Gdx.graphics.getFramesPerSecond();
		sleepTime = 0.0;
		beginTime = System.currentTimeMillis();
		framesSkipped = 0;	// resetting
	}

	public void End()
	{
		
		// calculate how long did the cycle take
		timeDiff = System.currentTimeMillis() - beginTime;

		// calculate sleep time
		sleepTime = (FRAME_PERIOD - timeDiff);

		if (sleepTime > 0)
		{
			// if sleepTime > 0 we're OK
			try
			{
				// send the thread to sleep for a short period
				// very useful for battery saving
				Thread.sleep((long)sleepTime);
			}
			catch (InterruptedException e)
			{}
		}

		while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS)
		{
			// we need to catch up
			//this.gamePanel.update(); // update without rendering
			sleepTime += FRAME_PERIOD;	// add frame period to check if in next frame
			framesSkipped++;
		}

		// for statistics
		framesSkippedPerStatCycle += framesSkipped;

		frameCountPerStatCycle++;
		totalFrameCount++;

		// check the actual time
		statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

		if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL)
		{
			// calculate the actual frames pers status check interval
			this.Fps = (frameCountPerStatCycle / (STAT_INTERVAL / 1000));

			//stores the latest fps in the array
			fpsStore[(int) statsCount % FPS_HISTORY_NR] = this.Fps;

			// increase the number of times statistics was calculated
			statsCount++;

			double totalFps = 0.0;

			// sum up the stored fps values
			for (int i = 0; i < FPS_HISTORY_NR; i++)
			{
				totalFps += fpsStore[i];
			}

			// obtain the average
			if (statsCount < FPS_HISTORY_NR)
			{
				// in case of the first 10 triggers
				this.Fps = totalFps / statsCount;
			}
			else
			{
				this.Fps = totalFps / FPS_HISTORY_NR;
			}

			// saving the number of total frames skipped
			totalFramesSkipped += framesSkippedPerStatCycle;

			// resetting the counters after a status record (1 sec)
			framesSkippedPerStatCycle = 0;
			statusIntervalTimer = 0;
			frameCountPerStatCycle = 0;

			statusIntervalTimer = System.currentTimeMillis();
			lastStatusStore = statusIntervalTimer;
		}
	}

	public void UpdateCpu()
	{
		//CpuUsage = cpu.ProcessCpuUsage;
	}

	public void UpdateMemory()
	{
		MemoryUsed = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
		MaxMemoryUsable = runtime.maxMemory() / 1048576L;
		MemoryFree = MaxMemoryUsable - MemoryUsed;
	}
}
