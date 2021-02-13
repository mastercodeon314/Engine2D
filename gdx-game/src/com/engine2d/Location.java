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

public class Location
{
	private Vector2 loc;
	private Vector2 scCenter;
	public float x;
	public float y;
	
	// WARNING: All locations should be in World coordinates!
	
	public Location(Vector2 sceneCenter)
	{
		scCenter = sceneCenter;
		this.Set(Vector2.Zero);
	}
	
	public Location(Vector2 sceneCenter, float x, float y)
	{
		scCenter = sceneCenter;
		this.Set(new Vector2(x, y));
	}
	
	public Location(Vector2 sceneCenter, Vector2 l)
	{
		scCenter = sceneCenter;
		this.Set(l);
	}
	
	public Vector2 Get()
	{
		return this.loc;
	}
	
	public void Set(Vector2 l)
	{
		x = l.x;
		y = l.y;
		this.loc = l;
	}
	
	/*
		Screen to world...
		
		x = top left minus 
	*/
	
	public static Vector2 toWorldCoordinates(Vector2 scCenter, Vector2 screenLoc)
	{
		float x = scCenter.x - screenLoc.x;
		float y = scCenter.y - screenLoc.y;
		return new Vector2(x, -y);
	}
	
	public static Vector2 fromWorldCoordinates(Vector2 scCenter, Vector2 worldLoc)
	{
		float x = worldLoc.x + scCenter.x;
		float y = worldLoc.y + scCenter.y;
		return new Vector2(x, y);
	}
	
	public static Location getRandomLocation(Scene sc)
	{
		int x = MathUtils.random((int)-sc.Center.x, (int)sc.Center.x);
		int y = MathUtils.random((int)-sc.Center.y, (int)sc.Center.y);

		return new Location(new Vector2(x, y));
	}
}
