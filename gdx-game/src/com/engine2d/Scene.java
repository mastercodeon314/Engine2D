package com.engine2d;

//IMPORTS
import android.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.bitfire.postprocessing.*;
import com.bitfire.postprocessing.effects.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.engine2d.UI.*;
import com.engine2d.utils.*;
import com.engine2d.events.*;
import java.util.*;
import java.io.*;
import com.engine2d.scripting.*;
//END IMPORTS

public class Scene
{
	public String Name;
	public Game EngineInstance;
	public SizeF Size;
	public ScreenLocation Location;
	public GameObjectList Objects;
	public boolean IsActive = true;
	public Camera Camera;
	public Viewport Viewport;
	public SpriteBatch Batch;
	public BitmapFont Font;
	public ShapeRenderer ShapeRenderer;
	public Color BackgroundColor = Color.BLACK;
	public ArrayList<Control> Controls;
	public PostProcessor PostProcessor;
	public Vector2 Center;
	public volatile long UID;
	public boolean Debug = true;
	
	public boolean Dragging = false;
	public Vector2 DragStart = Vector2.Zero;
	
	private volatile  boolean UidInstalized = false;
	private volatile boolean ErrorOccured = false;
	private volatile boolean IsDisposed = false;
	private MotionBlur fxaa;
	private int frameCounter = 1, updateCounter = 1;
	private Vector3 lastTouchedPos = new Vector3();

	public boolean Focused = false;
	public boolean isPressed = false;
	public boolean isClicked = false;
	private boolean skipClick = false;
	public IEvent Clicked;

	public SceneScriptList Scripts;
	public boolean FXEnabled = true;

	public String t = "";
	
	public ArrayList<String> DebugStrings = new ArrayList<String>();

	public Scene(Game en)
	{
		this.EngineInstance = en;
		this.EngineInstance.Scenes.Add(this);
		this.Name = "";
		
		// Sizing/Positioning
		this.Size = Utils.getScreenSize();
		this.Center = new Vector2(Size.getWidth() / 2, Size.getHeight() / 2);
		this.Location = new ScreenLocation(this.Center);
		
		
		this.Objects = new GameObjectList();
		this.Batch = new SpriteBatch();
		this.Font = new BitmapFont();
		this.Controls = new ArrayList<Control>();

		this.PostProcessor = new PostProcessor(false, false, false);

//		Bloom bloom = new Bloom((int)(Gdx.graphics.getWidth()), (int)(Gdx.graphics.getHeight()));
//		fxaa = new MotionBlur();
//		bloom.setEnabled(true);
//		bloom.setBaseIntesity(2);
//		bloom.setBloomIntesity(6);
//		bloom.setBlurPasses(4);
//		bloom.setBlurAmount(8.5f);
//		bloom.setBlurType(com.bitfire.postprocessing.filters.Blur.BlurType.Gaussian5x5b);
//		bloom.setThreshold(.1f);
//		fxaa.setEnabled(true);
//		fxaa.setBlurOpacity(1f);
//		this.PostProcessor.addEffect(bloom);
		//this.PostProcessor.addEffect(fxaa);

//		File vert = new File(EngineFiles.Instance.Shaders, "custom.vertex");
//		File frag = new File(EngineFiles.Instance.Shaders, "custom.fragment");
//		CustomEffect b = new CustomEffect(vert, frag);
//		b.setEnabled(true);
//		this.PostProcessor.addEffect(b);
//		
		// create the camera and setups the size of the scene
		this.Camera = new OrthographicCamera();
		((OrthographicCamera)this.Camera).setToOrtho(false, this.Size.getWidth(), this.Size.getHeight());

//		this.Camera = new PerspectiveCamera(67f, this.Size.getWidth(), this.Size.getHeight());
//		this.Camera.translate(new Vector3(0f, -30f, 15f));
//		this.Camera.lookAt(0, 0, 0);
//		this.Camera.update();

		this.Viewport = new FitViewport(this.Size.getWidth(), this.Size.getHeight(), this.Camera);

		this.ShapeRenderer = new ShapeRenderer();
		this.ShapeRenderer.setAutoShapeType(true);

		this.Scripts = new SceneScriptList();

		for (int i = 0; i < 24; i++)
		{
			this.DebugStrings.add("");
		}
		
		this.Instalize();
	}
	
	public void CapturePostFX()
	{
		
	}
	
	public void RenderPostFX()
	{
		
	}

	private void Instalize()
	{
		if (this.UidInstalized == false && this.IsDisposed == false)
		{
			this.UID = UidGenerator.GetUid();

			if (this.UID != -1)
			{
				this.UidInstalized = true;
			}
			else
			{
				this.ErrorOccured = true;
			}
		}
	}

	// Profiler Strings
	String a = "";
	String b = "";
	String c = "";
	String d = "";
	String e = "", f = "", g = "", h = "", i = "";

	// Main Engine2d Rendering Method
	public void Render(boolean update, boolean updatePost)
	{
		if (this.IsActive == true)
		{
			// UPDATE THE CAMERA
			//this.Camera.update();

			// PROCESS SCENE INPUT
			//this.ProcessInput();

			// BACKGROUND RENDER CALL
			/* This should be called in the
			extended classes of Scene
			BEFORE the super.Render is called. 
			this ensures the background rendering is done before
			any acutal rendering is processed. 
			*/
			//this.BackgroundRender();

			// POST PROCESS BEGIN
			//this.PostProcessor.capture();

			SceneScript[] sctps = this.Scripts.ToArray();

			if (sctps != null)
			{
				for (SceneScript s : sctps)
				{
					if (s.IsDrawScript == false)
					{
						s.UpdateFunc(this);
					}
					else
					{
						s.RenderFunc(this);
					}
				}
			}

			GameObject[] objects = this.Objects.ToArray();
			for (int i = 0; i < objects.length; i++)
			{
				/*	3D Drawing test
				 objects[i].ShapeRenderer.setProjectionMatrix(this.Camera.combined);
				 objects[i].ShapeRenderer.begin();
				 objects[i].ShapeRenderer.box(5f, 5f, 5f, 5f, 5f, 5f);
				 objects[i].ShapeRenderer.end();
				 */
				if (updatePost == false && update == true)
				{
					objects[i].Update();
				}

				// Draws the object
				objects[i].Draw(this.ShapeRenderer);

				if (updatePost == true && update == true)
				{
					objects[i].Update();
				}
			}
			
			// POST PROCESS RENDER
			//this.PostProcessor.render();
			
			frameCounter += 1;
			updateCounter += 1;
		}
	}
	
	public void RenderUI()
	{
		// UI DRAWING CODE START
		for (int i = 0; i < this.Controls.size(); i++)
		{
			this.Controls.get(i).Update();
			this.Controls.get(i).Render(ShapeRenderer);
		}
		
		

		if (Gdx.input.isTouched())
		{
			lastTouchedPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			Camera.unproject(lastTouchedPos);
		}
		else
		{
			//lastTouchedPos.set(0, 0, 0);
		}

		if (Debug == true)
		{
			this.Batch.begin();

			//if (updateCounter == 2)
			{
				//this.EngineInstance.Profiler.UpdateCpu();
				this.EngineInstance.Profiler.UpdateMemory();

				this.DebugStrings.set(0, "Global Object Count: " + this.EngineInstance.getObjectCount());
				this.DebugStrings.set(1, "Fps: " + this.EngineInstance.Profiler.Fps);
				this.DebugStrings.set(2, "Fps eng: " + this.EngineInstance.Profiler.fps1);
				//d = "Cpu Usage: " + this.EngineInstance.Profiler.CpuUsage + "%";
				this.DebugStrings.set(3, "Memory Used: " + this.EngineInstance.Profiler.MemoryUsed + " Mb");
				this.DebugStrings.set(4, "Memory Free: " + this.EngineInstance.Profiler.MemoryFree + " Mb");
				//				this.DebugStrings.set(5, "Last Touch Location: {" + Utils.TrimFloat(this.lastTouchedPos.x, 2) + ", " + Utils.TrimFloat(this.lastTouchedPos.y, 2) + "}");
				//g = "Last Touch Location: {" + NumberUtils.floatToIntBits(this.lastTouchedPos.x);
				this.DebugStrings.set(5, "Frame Count: " + frameCounter);
				this.DebugStrings.set(6, "Textbox Text " + t);
				this.DebugStrings.set(7, d);
				
				// Scene Size
				Vector2 SceneSize = new Vector2(this.Size.getWidth(), this.Size.getHeight());
				d = "Scene size: {" + this.Size.getWidth() + ", " + this.Size.getHeight() + "}";
				this.DebugStrings.set(8, d);
				
				// Screen Size
				Vector2 ScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
				d = "Screen size: {" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight() + "}";
				this.DebugStrings.set(9, d);
				
				
				d = "Scene Center: {" + (SceneSize.x / 2) + ", " + (SceneSize.y / 2) + "}";
				this.DebugStrings.set(10, d);
				
				d = "Screen Center: {" + (ScreenSize.x / 2) + ", " + (ScreenSize.y / 2) + "}";
				this.DebugStrings.set(11, d);
				//d = "Screen Center: {" + (ScreenSize.x / 2) + ", " + (ScreenSize.y / 2) + "}";
				//this.DebugStrings.set(12, d);
				updateCounter = 1;
			}

			this.Font.setColor(Color.WHITE);

			int start = (int)this.Camera.viewportHeight - 12;

			Object[] debugs = this.DebugStrings.toArray();

			for (int i = 0; i < debugs.length; i++)
			{
				String s = (String)debugs[i];

				if (s == "") continue;

				this.Font.draw(this.Batch, s, 10, start - (i * 16));


				//start -= 16;
			}

			// Profiler Display code
//				this.Font.draw(this.Batch, a, 10, 502);
//				this.Font.draw(this.Batch, b, 10, 518);
//				this.Font.draw(this.Batch, c, 10, 530);
//				this.Font.draw(this.Batch, d, 10, 542);
//				this.Font.draw(this.Batch, e, 10, 554);
//				this.Font.draw(this.Batch, f, 10, 566);
//				this.Font.draw(this.Batch, g, 10, 578);
//				this.Font.draw(this.Batch, h, 10, 590);
//				this.Font.draw(this.Batch, i, 10, 602);
//
			this.Batch.end();
		}

		// UI DRAWING CODE END
	}

//	private void ProcessInput()
//	{
//		if (skipClick == true)
//		{
//			this.isClicked = false;
//		}
//
//		if (Gdx.input.isTouched())
//		{
//			Vector3 t = new Vector3();
//			t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			this.Camera.unproject(t);
//
//			if (t.x >= this.Location.x && t.x <= this.Location.x + this.Size.getWidth())
//			{
//				if (t.y >= this.Location.y && t.y <= this.Location.y + this.Size.getHeight())
//				{
//					this.isPressed = true;
//
//					if (this.skipClick == false)
//					{
//						if (Clicked != null) Clicked.Fire();
//
//						this.onClick(t.x, t.y);
//
//						this.skipClick = true;
//						this.isClicked = true;
//						this.Focused = true;
//					}
//
//					this.onTouchDown(t.x, t.y);
//				}
//				else
//				{
//					this.isPressed = false;
//					this.skipClick = false;
//					this.Focused = false;
//					this.onTouchUp(t.x, t.y);
//				}
//			}
//			else
//			{
//				this.isPressed = false;
//				this.skipClick = false;
//				this.Focused = false;
//				this.onTouchUp(t.x, t.y);
//			}
//		}
//		else
//		{
//			this.isPressed = false;
//			this.skipClick = false;
//		}
//	}

	public void onClick(float x, float y)
	{

	}

	public void onTouchDown(float x, float y, int pointer, int button)
	{

	}

	public void onTouchUp(float x, float y, int pointer, int button)
	{
		if (this.Dragging == true) this.Dragging = false;
	}
	
	public void onTouchDragged(float x, float y, int pointer)
	{
		if (this.Dragging == false)
		{
			this.Dragging = true;
			this.DragStart = new Vector2(x, y);
		}
	}

	public void BackgroundRender()
	{
		this.ShapeRenderer.begin();
		this.ShapeRenderer.set(ShapeType.Filled);
		this.ShapeRenderer.setColor(this.BackgroundColor);
		
		this.ShapeRenderer.rect(0, 0, this.Size.getWidth(), this.Size.getHeight());
		
		this.ShapeRenderer.end();
	}

	public void Resize(float width, float height)
	{
		// resizes the scene
		Camera cam = null;
		try
		{
			cam = (OrthographicCamera)this.Camera;
		}
		catch (Exception ex)
		{}
		
		if (this.Camera != null && cam != null)((OrthographicCamera)this.Camera).setToOrtho(false, this.Size.getWidth(), this.Size.getHeight());

		this.Viewport = new FitViewport(this.Size.getWidth(), this.Size.getHeight(), this.Camera);
		this.Center = new Vector2(Size.getWidth() / 2, Size.getHeight() / 2);
	}

	public void Pause()
	{
		this.IsActive = false;

	}

	public void Resume()
	{
		this.IsActive = true;
		this.PostProcessor.rebind();
	}

	public void Dispose()
	{
		this.PostProcessor.dispose();
		if (this.UidInstalized == true && this.IsDisposed == false)
		{
			UidGenerator.ReleaseUid(this.UID);
			this.UidInstalized = false;
			this.IsDisposed = true;
		}
	}
}


