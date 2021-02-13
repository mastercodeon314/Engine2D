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

class LineTypes
{
	 public static int Line2D = 0;
	 public static int Line3D = 1;
	
}

public class LineBase
{
	public Vector2 Start;
	public Vector2 End;
	public Vector3 Start3;
	public Vector3 End3;
	public Color Color;

	public int LineType = LineTypes.Line2D;
	
	public LineBase(int lnType)
	{
		if (lnType == 0 || lnType == 1)
		{
			this.LineType = lnType;
		}
		
		this.Start = Vector2.Zero;
		this.End = Vector2.Zero;
		this.Start3 = Vector3.Zero;
		this.End3 = Vector3.Zero;
		
	}
	
	public LineBase(Vector3 start, Vector3 end)
	{
		this(LineTypes.Line3D);

		this.Start3 = start;
		this.End3 = end;
		this.Color = Color.WHITE;
	}

	public LineBase(int type, float[] start, float[] end)
	{
		this(type);
		
		if (this.LineType == LineTypes.Line3D)
		{
		Start3.set(start[0], start[1], start[2]);
		End3.set(end[0], end[1], end[2]);
		}
		else
		{
			Start.set(start[0], start[1]);
			End.set(end[0], end[1]);
		}
	}
	
	public LineBase(Vector2 start, Vector2 end)
	{
		this(LineTypes.Line2D);

		this.Start = start;
		this.End = end;
		this.Color = Color.WHITE;
	}


	public LineBase(float sx, float sy, float ex, float ey)
	{
		this(LineTypes.Line2D);
		Start.set(sx, sy);
		End.set(ex, ey);
	}

	public LineBase(float sx, float sy, float ex, float ey, Color col)
	{
		this(sx, sy, ex, ey);
		this.Color = col;
	}

	public LineBase(float sx, float sy, float sz, float ex, float ey, float ez)
	{
		this(LineTypes.Line3D);
		Start3.set(sx, sy, sz);
		End3.set(ex, ey, ez);
	}

	public LineBase(float sx, float sy, float sz, float ex, float ey, float ez, Color col)
	{
		this(sx, sy, sz, ex, ey, ez);
		this.Color = col;
	}
}
