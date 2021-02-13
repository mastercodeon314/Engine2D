package com.engine2d.utils;

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
import android.util.*;
//END IMPORTS

public class Utils
{
	public static Random rnd = new Random();
	
	public static SizeF getScreenSize()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		return new SizeF(w, h);
	}
	
	public static Vector2 getRandomLocation()
	{
		SizeF bounds = Utils.getScreenSize();
		
		int x = MathUtils.random(0, (int)bounds.getWidth());
		int y = MathUtils.random(0, (int)bounds.getHeight());
		
		return new Vector2(x, y);
	}
	
	public static Color getRandomColor()
	{
		float r = MathUtils.random(0f, 1f);
		float g = MathUtils.random(0f, 1f);
		float b = MathUtils.random(0f, 1f);
		
		return new Color(r, g, b, 1);
	}
	
	public static float TrimFloat(float f, int numOfDecimals)
	{
		if (f == 0f) return 0f;
		
		String v = String.valueOf(f);
		
		if (v.endsWith(".0") == true) return f;
		
		if (v.length() > 1 && v.contains(".") == true)
		{
			String[] vParts = splitString(v, '.');
			if (vParts.length > 1)
			{
				String fract = vParts[1].substring(0, numOfDecimals);
				v = vParts[0] + "." + fract;
			}
		}
		return Float.parseFloat(v);
	}
	
	public static Vector3 getAccelerometerReading()
	{
		return new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
	}
	
	public static void getAccelerometerReading(float[] xyzOut)
	{
		if (xyzOut != null)
		{
			if (xyzOut.length >= 3)
			{
				xyzOut[0] = Gdx.input.getAccelerometerX();
				xyzOut[1] = Gdx.input.getAccelerometerY();
				xyzOut[2] = Gdx.input.getAccelerometerZ();
			}
		}
	}
	
	private static String[] splitString(String s, char splitChar)
	{
		ArrayList<String> Result = new ArrayList<String>();
		ArrayList<Integer> SplitCharIndexes = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == splitChar)
			{
				SplitCharIndexes.add(i);
			}
		}
		
		if (SplitCharIndexes.size() > 1)
		{
			int lastIdx = 0;
			for (int i = 0; i < SplitCharIndexes.size(); i++)
			{
				int idx = SplitCharIndexes.get(i);
				Result.add(s.substring(lastIdx, idx));
				lastIdx = idx;
			}
		}
		else
		{
			Result.add(s.substring(0, SplitCharIndexes.get(0)).replace(".", ""));
			Result.add(s.substring(SplitCharIndexes.get(0), s.length() - 1).replace(".", ""));
		}
		String[] res = new String[Result.size()];
		res = Result.toArray(res);
		
		return res;
	}
}
