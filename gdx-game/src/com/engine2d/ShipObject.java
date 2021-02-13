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
//END IMPORTS

public class ShipObject extends MeshObject
{
	public ShipObject(Scene s)
	{
		super(s);
		//this.Mesh = MeshFileReader.getMeshFromFile("ShipMesh", this);
		Init();
		
		//this.Mesh = ObjFileReader.get2dMeshFromObj("Square", this);
	}
	
	private void Init()
	{
		this.Mesh = new Mesh(this);
		Line l1, l2, l3;
		
		l1 = new Line(0, 0, -10, -30, Color.BLUE);
		l2 = new Line(0, 0, 10, -30, Color.RED);
		l3 = new Line(-6.66f, -20, 6.66f, -20, Color.ORANGE);
		
		this.Mesh.Add(l1);
		this.Mesh.Add(l2);
		this.Mesh.Add(l3);
		this.Mesh.Name = "Space Ship";
		this.Mesh.Origin = new Vector2(0f, -15f);
		
		MeshFile m = new MeshFile(this.Mesh);
		m.Build();
		m.Save("ShipMesh");
		
		
		//this.Scripts.Add(Lua.EngineScripts.Get("Ship"));
		//this.Scripts.Add(Lua.EngineScripts.Get("Tween"));
		
	}

	@Override
	public void Rotate(float degrees)
	{
		// TODO: Implement this method
		super.Rotate(degrees);
	}
}
