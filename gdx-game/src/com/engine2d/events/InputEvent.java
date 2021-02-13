package com.engine2d.events;

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
import java.util.*;
//END IMPORTS

public class InputEvent implements IEvent
{
	public final int EventType;
	public final int[] Data;
	public boolean Used = false;

	public InputEvent(int type, int[] data)
	{
		this.EventType = type;
		this.Data = data;
	}
	
	public void Fire()
	{
		
	}
	
	public void Fire(int EventType, Object data)
	{
		
	}
}
