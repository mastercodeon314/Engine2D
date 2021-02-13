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
import android.util.*;
import com.engine2d.scripting.*;
//END IMPORTS

public class GameObject extends UidObject
{
	static private String createVertexShader(boolean hasNormals, boolean hasColors, int numTexCoords)
	{
		String shader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n"
			+ (hasNormals ? "attribute vec3 " + ShaderProgram.NORMAL_ATTRIBUTE + ";\n" : "")
			+ (hasColors ? "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" : "");

		for (int i = 0; i < numTexCoords; i++)
		{
			shader += "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		}

		shader += "uniform mat4 u_projModelView;\n";
		shader += (hasColors ? "varying vec4 v_col;\n" : "");

		for (int i = 0; i < numTexCoords; i++)
		{
			shader += "varying vec2 v_tex" + i + ";\n";
		}

		shader += "void main() {\n" + "   gl_Position = u_projModelView * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n"
			+ (hasColors ? "   v_col = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" : "");

		for (int i = 0; i < numTexCoords; i++)
		{
			shader += "   v_tex" + i + " = " + ShaderProgram.TEXCOORD_ATTRIBUTE + i + ";\n";
		}
		shader += "   gl_PointSize = 1.0;\n";
		shader += "}\n";
		return shader;
	}

	static private String createFragmentShader(boolean hasNormals, boolean hasColors, int numTexCoords)
	{
		String shader = "#ifdef GL_ES\n" + "precision mediump float;\n" + "#endif\n";

		if (hasColors) shader += "varying vec4 v_col;\n";
		for (int i = 0; i < numTexCoords; i++)
		{
			shader += "varying vec2 v_tex" + i + ";\n";
			shader += "uniform sampler2D u_sampler" + i + ";\n";
		}

		shader += "void main() {\n" + "   gl_FragColor = " + (hasColors ? "v_col" : "vec4(1, 1, 1, 1)");

		if (numTexCoords > 0) shader += " * ";

		for (int i = 0; i < numTexCoords; i++)
		{
			if (i == numTexCoords - 1)
			{
				shader += " texture2D(u_sampler" + i + ",  v_tex" + i + ")";
			}
			else
			{
				shader += " texture2D(u_sampler" + i + ",  v_tex" + i + ") *";
			}
		}

		shader += ";\n}";
		return shader;
	}

	/** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
	public void createDefaultShader()
	{
		String vertexShader = createVertexShader(false, true, 0);
		String fragmentShader = createFragmentShader(false, true, 0);
		java.io.File vF = new java.io.File(EngineFiles.Instance.Sounds, "vert.fragment");
		java.io.File fF = new java.io.File(EngineFiles.Instance.Sounds, "vert.vertex");

		try
		{
			vF.createNewFile();
			fF.createNewFile();

			FileOutputStream a = new FileOutputStream(vF);
			a.write(vertexShader.getBytes());
			a.close();

			a = new FileOutputStream(fF);
			a.write(fragmentShader.getBytes());
			a.close();
		}
		catch (Exception ex)
		{}
	}

	public String Name;
	public WorldLocation Location;
	public Vector2 Heading;
	public Scene Scene;
	public Mesh Mesh;
	public ShapeRenderer ShapeRenderer;
	public IMovement MovementHandler;
	public float Rotation = 0f;
	public boolean Visible = true;
	public SizeF Size;
	public GameScriptList Scripts;
	public boolean UsesInput = true;
	
	public GameObject(Scene s)
	{
		this.Scene = s;
		this.Location = new WorldLocation(s.Center);
		this.Heading = Vector2.Zero;
		this.Scripts = new GameScriptList();

		//createDefaultShader();
		InitRenderer();

//		Thread t = new Thread(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				InitRenderer();
//			}
//		});
//		t.start();
	}

	private void InitRenderer()
	{
		this.ShapeRenderer = new ShapeRenderer(5000);
		this.ShapeRenderer.setAutoShapeType(true);
	}

	public GameObject(Scene s, boolean addToScene)
	{
		this(s);

		//Adds this GameObject to scene objects
		if (addToScene == true) this.Scene.Objects.Add(this);
	}

	// Adds degrees to rotation
	public void Rotate(float degrees)
	{
		if (this.Visible == true) this.Mesh.Rotate(degrees);
	}

	// Sets degrees to rotation
	public void setRotation(float degrees)
	{
		if (this.Visible == true) this.Mesh.setRotation(degrees);
	}

	public void SetLocation(float x, float y)
	{
		this.Location.Set(new Vector2(x, y));
	}

	public void SetLocation(Vector2 l)
	{
		this.Location.Set(l);
	}

	public void Tween_X_Location(float x)
	{
		this.Location.Set(new Vector2(this.Location.x + x, this.Location.y));
	}

	public void Tween_Y_Location(float y)
	{
		this.Location.Set(new Vector2(this.Location.x, this.Location.y + y));
	}

	public void Draw(ShapeRenderer sr)
	{

	}

	public void Update()
	{
		if (this.MovementHandler != null)
		{
			this.MovementHandler.Update();
		}

		if (this.Scripts.Size() > 0)
		{
			IScript[] scrs = this.Scripts.ToArray();
			if (scrs != null)
			{
				for (IScript s : scrs)
				{
					s.Execute("update", new Object[] {this});
				}
			}
		}
	}

	public void Dispose()
	{
		super.Dispose();
	}

}
