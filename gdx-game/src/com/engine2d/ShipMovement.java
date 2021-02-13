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
import java.io.*;
//END IMPORTS

public class ShipMovement implements IMovement
{
	private GameObject Parent;
	public float CurrentForce = 0f;
	public float Momentum = 0f;
	public float FallOff = 0.04f;
	
	
	public ShipMovement(GameObject obj)
	{
		this.Parent = obj;
	}
	
	
	public void Update()
	{
		float x = this.Parent.Location.Get().x;
		float y = this.Parent.Location.Get().y;
		
		if (Momentum != 0.0f)
		{
			if (Momentum < 0) Momentum = Momentum + (-Momentum * FallOff);
			else Momentum = Momentum - (Momentum * FallOff);

			
			Vector2 c = new Vector2(x, y + Momentum);

			this.Parent.Location.Set(c);
		}
	}
	
	public void ForceY(float f)
	{
		CurrentForce = f;
		if (f < 0) Momentum -= Math.abs(f * FallOff);
		else Momentum = Momentum + (f * FallOff);
		if (Math.abs(Momentum) > f) Momentum = f;
	}
}
