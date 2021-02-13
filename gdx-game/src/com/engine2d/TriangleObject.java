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
import com.engine2d.scripting.*;
import com.engine2d.scripting.lua.*;
import android.util.*;
//END IMPORTS

public class TriangleObject extends MeshObject
{
	public TriangleObject(Scene s)
	{
		super(s);
		//this.Mesh = MeshFileReader.getMeshFromFile("ShipMesh", this);
		Init();
	}
	
	private void Init()
	{
		this.Mesh = new Mesh(this);
		Line l1, l2, l3;
		
		float size = 5f;
		
		l1 = new Line(0, 0, -size, -size, Color.BLUE);
		l2 = new Line(0, 0, size, -size, Color.RED);
		l3 = new Line(-size, -size, size, -size, Color.ORANGE);
		
		this.Mesh.Add(l1);
		this.Mesh.Add(l2);
		this.Mesh.Add(l3);
		this.Mesh.Name = "Triangle " + String.valueOf(com.engine2d.utils.Utils.rnd.nextInt(1000));
		this.Mesh.Origin = new Vector2(0f, -5f);
		
		MeshFile m = new MeshFile(this.Mesh);
		m.Build();
		m.Save("Triangle");
		
		
		this.Scripts.Add(Lua.EngineScripts.Get(("Ship")));
		
		float x = com.engine2d.utils.Utils.rnd.nextInt((int)(this.Scene.Size.getWidth()));
		float y = com.engine2d.utils.Utils.rnd.nextInt((int)(this.Scene.Size.getHeight()));
		ScreenLocation l = new ScreenLocation(this.Scene.Center, x, y);
		this.SetLocation(l.toWorldCoordinates());
		
		//this.Scripts.Add(Lua.EngineScripts.Get("Tween"));
		
	}

	@Override
	public void Rotate(float degrees)
	{
		// TODO: Implement this method
		super.Rotate(degrees);
	}
}
