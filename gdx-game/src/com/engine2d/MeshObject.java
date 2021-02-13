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
import com.badlogic.gdx.math.*;
//END IMPORTS

public class MeshObject extends GameObject
{
	public MeshObject(Scene s)
	{
		super(s);
	}

	@Override
	public void Draw(ShapeRenderer sr)
	{
		if (this.Visible == true)
		{
			//this.ShapeRenderer.setProjectionMatrix(this.Scene.Camera.combined);
			this.ShapeRenderer.begin();
			Vector2 screenLoc = this.Location.ScreenCoords;

			//this.ShapeRenderer.setColor(Color.RED);
			//this.ShapeRenderer.line(screenLoc.x + this.Mesh.Origin.x, screenLoc.y + this.Mesh.Origin.y, this.Scene.Center.x, this.Scene.Center.y);


			for (int i = 0; i < this.Mesh.Lines.size(); i++)
			{
				Line l = this.Mesh.Lines.get(i);

				sr.setColor(Color.WHITE);
				
				this.ShapeRenderer.point(screenLoc.x + this.Mesh.Origin.x, screenLoc.y + this.Mesh.Origin.y, 0);

				this.ShapeRenderer.setColor(l.Color);

				//sr.point(l.End.x + this.Location.x, l.End.y + this.Location.y, 0);
				this.ShapeRenderer.line(l.Start.x + screenLoc.x, l.Start.y + screenLoc.y, l.End.x + screenLoc.x, l.End.y + screenLoc.y);
				
				//float[] convex = this.Mesh.getConvexHull(screenLoc);
				//this.ShapeRenderer.setColor(Color.GREEN);
				//this.ShapeRenderer.polygon(convex);
			}

			this.ShapeRenderer.end();
		}
		
		// TODO: Implement this method
		super.Draw(sr);
	}
}
