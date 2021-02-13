package com.engine2d.dsp;

import java.lang.*;
import java.util.*;
import com.badlogic.gdx.math.WindowedMean;

public class Utils
{
	public static int convertSampleLengthToMiliseconds(int samples, int sampleRate)
	{
		int Result = (samples / sampleRate) * 1000;
		return Result;
	}

	public static float averageSignal(float[] data)
	{
		WindowedMean averager = new WindowedMean(data.length);

		for (int i = 0; i < data.length; i++)
		{
			averager.addValue(data[i]);
		}

		return averager.getMean();
	}

	// WARNING: BOTH A AND B MUST BE EQUAL IN LENGTH!
	public static float[] mixSignals(float[] a, float[] b)
	{
		float[] Result = new float[a.length];
		float factor = averageSignal(a);

		for (int i = 0; i < a.length; i++)
		{
			a[i] = (a[i] / Float.max(a[i], a[i])) * factor;
			b[i] = (b[i] / Float.max(b[i], b[i])) * factor;

			float v = (a[i] + b[i]);
			Result[i] = (v / Float.max(v, v)) * factor;
		}

		return Result;
	}
}
