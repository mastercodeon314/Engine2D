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

public class Line
{
	public Vector2 Start = new Vector2();
	public Vector2 End = new Vector2();
	public Color Color = Color.WHITE;
	public float Rotation = 0f;
	public float[] InitalVertices;

	public Line()
	{
		////super(LineTypes.Line2D);
		this.Start = new Vector2();
		this.End = new Vector2();
		this.Color = Color.WHITE;
	}

	public Line(Vector2 start, Vector2 end)
	{
		//super(start, end);
		this.Start = start;
		this.End = end;
		this.Color = Color.WHITE;
		this.InitalVertices = this.getVertices();
	}
	
	public Line(Vector2 start, Vector2 end, Color col)
	{
		//super(start, end);
		this.Start = start;
		this.End = end;
		this.Color = col;
		this.InitalVertices = this.getVertices();
	}
	
	public Line(float[] start, float[] end)
	{
		//super(LineTypes.Line2D, start, end);
		
		Start.set(start[0], start[1]);
		End.set(end[0], end[1]);
		this.InitalVertices = this.getVertices();
	}

	public Line(float sx, float sy, float ex, float ey)
	{
		//super(sx, sy, ex, ey);
		
		Start.set(sx, sy);
		End.set(ex, ey);
		this.InitalVertices = this.getVertices();
	}

	public Line(float sx, float sy, float ex, float ey, Color col)
	{
		this(sx, sy, ex, ey);
		this.Color = col;
	}

	public float[] getVertices()
	{
		return new float[]
		{
			this.Start.x,
			this.Start.y,
			this.End.x,
			this.End.y
		};
	}

	public void setRotation(Vector2 origin, float degrees)
	{
		this.Rotation = degrees;

		final float[] localVertices = this.InitalVertices;
		final float[] worldVertices = new float[localVertices.length];
		final float originX = origin.x;
		final float originY = origin.y;
		final float scaleX = 1f;
		final float scaleY = 1f;
		final boolean scale = scaleX != 1 || scaleY != 1;
		final float rotation = this.Rotation;
		final float cos = MathUtils.cosDeg(rotation);
		final float sin = MathUtils.sinDeg(rotation);

		for (int i = 0; i < localVertices.length; i += 2)
		{
			float x = localVertices[i] - originX;
			float y = localVertices[i + 1] - originY;

			// scale if needed
			if (scale)
			{
				x *= scaleX;
				y *= scaleY;
			}

			// rotate if needed
			if (rotation != 0)
			{
				float oldX = x;
				x = cos * x - sin * y;
				y = sin * oldX + cos * y;
			}

			worldVertices[i] = x + originX;
			worldVertices[i + 1] = y + originY;
		}

		this.Set(new Vector2(worldVertices[0], worldVertices[1]), new Vector2(worldVertices[2], worldVertices[3]));
	}

	public void Set(Vector2 start, Vector2 end)
	{
		this.Start = start;
		this.End = end;
	}

	// Data encoding order is, x1, x2, y1, y2, (R,G,B,A)
	public int[] ToRawData()
	{
		int[] Result = new int[8];
		float x1 = this.Start.x, x2 = this.End.x;
		float y1 = this.Start.y, y2 = this.End.y;

		Result[0] = NumberUtils.floatToIntBits(x1);
		Result[1] = NumberUtils.floatToIntBits(x2);
		Result[2] = NumberUtils.floatToIntBits(y1);
		Result[3] = NumberUtils.floatToIntBits(y2);
		Result[4] = NumberUtils.floatToIntBits(this.Color.r);
		Result[5] = NumberUtils.floatToIntBits(this.Color.g);
		Result[6] = NumberUtils.floatToIntBits(this.Color.b);
		Result[7] = NumberUtils.floatToIntBits(this.Color.a);


		return Result;
	}
}
