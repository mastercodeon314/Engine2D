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

public class ScreenLocation extends Location
{
	public Vector2 WorldCoords;
	public ScreenLocation(Vector2 sceneCenter)
	{
		super(sceneCenter);
	}

	public ScreenLocation(Vector2 sceneCenter, float x, float y)
	{
		super(sceneCenter, x, y);
	}

	public ScreenLocation(Vector2 sceneCenter, Vector2 l)
	{
		super(sceneCenter, l);
	}

	@Override
	public void Set(Vector2 l)
	{
		// TODO: Implement this method
		super.Set(l);
		
		this.WorldCoords = this.toWorldCoordinates();
	}
	
	public Vector2 toWorldCoordinates()
	{
		float x = super.x - super.scCenter.x;
		float y = super.y - super.scCenter.y;
		return new Vector2(x, y);
	}
}
