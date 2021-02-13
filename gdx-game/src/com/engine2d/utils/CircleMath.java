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
//END IMPORTS

public class CircleMath
{
	//SquareV1:
	// Top left vertex location
	//
	//SquareV2:
	// Bottom Right vertex location
	// 
	public void d(Vector2 SquareV1, Vector2 SquareV2)
	{
		float sDist = distance(SquareV1, SquareV2);
		
	}
	
	private float distance(Vector2 x, Vector2 y)
	{
		float Result = 0;
		float p1 = (float)Math.pow((y.x - x.x), 2);
		float p2 = (float)Math.pow((y.y - x.y), 2);
		Result = (float)Math.sqrt(p1 + p2);
		return Result;
	}
}
