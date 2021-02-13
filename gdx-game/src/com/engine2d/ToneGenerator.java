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

public class ToneGenerator
{
	static float sineAngle = 0;
	
	// NEEDS A MINIMUM OF 512 FOR LENGTH
	public static float[] Sine(float freq, int length, float rate)
	{
		float increment = (float)(2 * Math.PI) * freq / rate;
		
		float[] Result = new float[length];
		
		for (int i = 0; i < length; i++)
		{
			Result[i] = (float)(Math.sin(sineAngle));
			sineAngle += increment;
		}
		
		return Result;
	}
}
