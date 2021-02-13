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

public class Line3 extends LineBase
{
	public Vector3 Start3;
	public Vector3 End3;
	public Color Color;
	public float Rotation = 0f;
	public float[] InitalVertices;

	public Line3()
	{
		super(1);
		this.Start3 = new Vector3();
		this.End3 = new Vector3();
		this.Color = Color.WHITE;
	}

	public Line3(Vector3 start, Vector3 end)
	{
		super(start, end);
		
		this.Start3 = start;
		this.End3 = end;
		this.Color = Color.WHITE;
		this.InitalVertices = this.getVertices();
	}

	public Line3(float[] start, float[] end)
	{
		//this();
		super(LineTypes.Line3D, start, end);
		Start3.set(start[0], start[1], start[2]);
		End3.set(end[0], end[1], end[2]);
		this.InitalVertices = this.getVertices();
	}

	public Line3(float sx, float sy, float sz, float ex, float ey, float ez)
	{
		//this();
		super(sx, sy, sz, ex, ey, ez);
		Start3.set(sx, sy, sz);
		End3.set(ex, ey, ez);
		this.InitalVertices = this.getVertices();
	}

	public Line3(float sx, float sy, float sz, float ex, float ey, float ez, Color col)
	{
		this(sx, sy, sz, ex, ey, ez);
		this.Color = col;
	}

	public float[] getVertices()
	{
		return new float[]
		{
			this.Start3.x,
			this.Start3.y,
			this.Start3.z,
			this.End3.x,
			this.End3.y,
			this.End3.z
		};
	}

	public void setRotation(Vector3 origin, float degrees)
	{
		this.Rotation = degrees;

		final float[] localVertices = this.InitalVertices;
		final float[] worldVertices = new float[localVertices.length];
		final float originX = origin.x;
		final float originY = origin.y;
		final float originZ = origin.z;
		final float scaleX = 1f;
		final float scaleY = 1f;
		final float scaleZ = 1f;
		final boolean scale = scaleX != 1 || scaleY != 1 || scaleZ != 1;
		final float rotation = this.Rotation;
		final float cos = MathUtils.cosDeg(rotation);
		final float sin = MathUtils.sinDeg(rotation);

		for (int i = 0; i < localVertices.length; i += 3)
		{
			float x = localVertices[i] - originX;
			float y = localVertices[i + 1] - originY;
			float z = localVertices[i + 2] - originZ;
			
			// scale if needed
			if (scale)
			{
				x *= scaleX;
				y *= scaleY;
				z *= scaleZ;
			}

			// TODO: Implement Z axis rotation
			// rotate if needed
			if (rotation != 0)
			{
				float oldX = x;
				x = cos * x - sin * y;
				y = sin * oldX + cos * y;
			}

			worldVertices[i] = x + originX;
			worldVertices[i + 1] = y + originY;
			worldVertices[i + 2] = z + originZ;
		}

		this.Set(new Vector3(worldVertices[0], worldVertices[1], worldVertices[2]), new Vector3(worldVertices[2], worldVertices[3], worldVertices[4]));
	}

	public void Set(Vector3 start, Vector3 end)
	{
		this.Start3 = start;
		this.End3 = end;
	}

	// Data encoding order is, x1, x2, y1, y2, z1, z2, (R,G,B,A)
	public int[] ToRawData()
	{
		int[] Result = new int[10];
		float x1 = this.Start3.x, x2 = this.End3.x;
		float y1 = this.Start3.y, y2 = this.End3.y;
		float z1 = this.Start3.z, z2 = this.End3.z;
		
		Result[0] = NumberUtils.floatToIntBits(x1);
		Result[1] = NumberUtils.floatToIntBits(x2);
		Result[2] = NumberUtils.floatToIntBits(y1);
		Result[3] = NumberUtils.floatToIntBits(y2);
		Result[4] = NumberUtils.floatToIntBits(z1);
		Result[5] = NumberUtils.floatToIntBits(z2);
		
		Result[6] = NumberUtils.floatToIntBits(this.Color.r);
		Result[7] = NumberUtils.floatToIntBits(this.Color.g);
		Result[8] = NumberUtils.floatToIntBits(this.Color.b);
		Result[9] = NumberUtils.floatToIntBits(this.Color.a);


		return Result;
	}
}
