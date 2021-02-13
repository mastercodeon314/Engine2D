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

public class Mesh
{
	public ArrayList<Line> Lines;
	public String Name = "";
	public Vector2 Origin = Vector2.Zero;
	//public Polygon Poly;
	public int LineCount = 0;
	public GameObject Parent;
	public float Rotation = 0f;

	public Rectangle BoundingRectangle;
	public Mesh(GameObject prnt)
	{
		this.Lines = new ArrayList<Line>();
		this.Parent = prnt;
	}
	
	public Mesh(GameObject prnt, float[] vertices)
	{
		this.Lines = new ArrayList<Line>();
		this.Parent = prnt;
		this.Set(vertices);
	}
	
	public Mesh(GameObject prnt, float[] vertices, Vector2 origin)
	{
		this(prnt, vertices);
		this.Origin = origin;
	}

	public void Add(Line l)
	{
		this.Lines.add(new Line(l.Start, l.End, l.Color));
		this.LineCount = Lines.size();
	}

	public void Add(Vector2 Start, Vector2 End)
	{
		this.Add(new Line(Start, End));
	}

	public void Add(float[] Start, float[] End)
	{
		this.Add(new Line(Start, End));
	}

	public void Remove(Line l)
	{
		this.Lines.remove(l);
		this.LineCount = Lines.size();
	}

//	public Rectangle getBoundingBox()
//	{
//		if (this.Lines.size() > 1)
//		{
//			this.BoundingRectangle = this.getBoundingRectangle();
//			this.Orgin = this.BoundingRectangle.getCenter(this.Orgin);
//			return this.BoundingRectangle;
//		}
//
//	return null;
//	}

	public void Rotate(float d)
	{
		if (this.Rotation + d > 360)
		{
			float rem = (this.Rotation + d) - 360f;
			this.Rotation = rem;
		}
		else
		{
			this.Rotation += d;
		}

		for (Line l : this.Lines)
		{
			Line ln = (Line)l;
			ln.setRotation(this.Origin, this.Rotation);
		}

//		Polygon p = new Polygon();
//		p.setVertices(this.getVertices());
//		//this.Orgin = GeometryUtils.polygonCentroid(p.getVertices(), 0, p.getVertices().length, this.Orgin);
//		p.setOrigin(this.Orgin.x, this.Orgin.y);
//		
//		p.rotate(d);
//		p.dirty();

//		this.Set(p.getTransformedVertices());
	}

	public void setRotation(float degrees)
	{
		this.Rotation = degrees;
		for (Line l : this.Lines)
		{
			l.setRotation(this.Origin, this.Rotation);
		}
	}

	public void Set(float[] verts)
	{
		this.Clear();

		for (int c = 0; c < verts.length;)
		{
			float x = verts[c];
			float y = verts[c + 1];
			float x2 = verts[c + 2];
			float y2 = verts[c + 3];

			this.Add(new Line(x, y, x2, y2));
			c += 4;
		}

		//this.Parent.ShapeRenderer.flush();

		//updatePolygon(verts);
	}

	public void Clear()
	{
		while (this.Lines.size() > 0)
		{
			this.Lines.remove(0);
		}
		//this.Lines = new ArrayList<Line>();
		this.LineCount = Lines.size();

		//this.Parent.ShapeRenderer.flush();
	}

	public int getLineCount()
	{
		return this.Lines.size();
	}

	public float[] getExtraPolyVertex()
	{
		float[] verts = getVertices();
		float[] Result = Arrays.copyOf(verts, verts.length + 2);
		return Result;
	}

	public void updateOrigin()
	{
		float[] vertices = this.getVertices();
		this.Origin = com.badlogic.gdx.math.GeometryUtils.polygonCentroid(vertices, 0 ,vertices.length, this.Origin);
	}
	
	
	/** Returns an axis-aligned bounding box of this polygon.
	 * 
	 * Note the returned Rectangle is cached in this polygon, and will be reused if this Polygon is changed.
	 * 
	 * @return this polygon's bounding box {@link Rectangle} */
	public Rectangle getBoundingRectangle()
	{
		if (this.Lines.size() > 1)
		{
			float[] vertices = getVertices();

			float minX = vertices[0];
			float minY = vertices[1];
			float maxX = vertices[0];
			float maxY = vertices[1];

			final int numFloats = vertices.length;
			for (int i = 2; i < numFloats; i += 2)
			{
				minX = minX > vertices[i] ? vertices[i] : minX;
				minY = minY > vertices[i + 1] ? vertices[i + 1] : minY;
				maxX = maxX < vertices[i] ? vertices[i] : maxX;
				maxY = maxY < vertices[i + 1] ? vertices[i + 1] : maxY;
			}

			Rectangle bounds = new Rectangle();
			bounds.x = minX;
			bounds.y = minY;
			bounds.width = maxX - minX;
			bounds.height = maxY - minY;

			return bounds;
		}
		return null;
	}

	public float[] getVertices()
	{
		int lineCount = this.Lines.size();
		// Payload data total = lineCount * 4 floats per each line [2 for start x, y, 2 for end x, y], 4 for color
		int totalVertices = lineCount * 4;

		ArrayList<Float> Result = new ArrayList<Float>();

		for (int i = 0; i < Lines.size(); i++)
		{
			float[] line = ((Line)Lines.get(i)).getVertices();
			for (int c = 0; c < 4; c++)
			{
				Result.add(line[c]);
			}
		}
		Float[] r = Result.toArray(new Float[Result.size()]);
		float[] finalResult = new float[r.length];

		for (int i = 0; i < r.length; i++)
		{
			finalResult[i] = r[i];
		}

		return finalResult;
	}
	
	public float[] getConvexHull(Vector2 screenLoc)
	{
		ConvexHull cv = new ConvexHull();
		float[] verts = this.getVertices();
		float[] convex = cv.computePolygon(verts, false).toArray();
		
		//Converts from mesh coordinates to screen coordinates
		for (int i = 0; i + 2 < convex.length; i += 2)
		{
			Vector2 v = new Vector2(convex[i], convex[i + 1]);
			v.set(screenLoc.x + v.x, screenLoc.y + v.y);
			convex[i] = v.x;
			convex[i + 1] = v.y;
		}
		
		convex[convex.length - 2] = convex[0];
		convex[convex.length - 1] = convex[1];
		return convex;
	}
	
	private float[] getSlimVertices()
	{
		ArrayList<Float> nonRepeatVertices = new ArrayList<Float>();
		float[] vertices = this.getSlimVertices();
		
		for (int i = 0; i + 2 < vertices.length; i += 2)
		{
			Vector2 v1 = new Vector2(vertices[i], vertices[i + 1]);
			for (int c = 0; c + 2 < vertices.length; c += 2)
			{
				Vector2 v2 = new Vector2(vertices[c], vertices[c + 1]);
				if (verticesEqual(v1, v2) == false)
				{
					nonRepeatVertices.add(v1.x);
					nonRepeatVertices.add(v1.y);
				}
			}
		}
		
		float[] Result = new float[nonRepeatVertices.size()];
		
		for (int i = 0; i < nonRepeatVertices.size(); i++)
		{
			Result[i] = nonRepeatVertices.get(i);
		}
		
		return Result;
	}
	
	private boolean verticesEqual(Vector2 v1, Vector2 v2)
	{
		return (v1.x == v2.x) && (v1.y == v2.y);
	}

//	public Polygon toPolygon()
//	{
//		Polygon Result = new Polygon();
//		Result.setVertices(this.getVertices());
//		return Result;
//	}
}
