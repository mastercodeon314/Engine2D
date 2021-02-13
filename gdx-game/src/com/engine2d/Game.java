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
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.engine2d.utils.*;
import android.content.*;
import com.badlogic.gdx.files.*;
import android.util.*;
//END IMPORTS

public class Game extends ApplicationAdapter
{
	public SceneList Scenes;
	public Scene PrimaryScene;
	public Renderer Renderer;
	public ShapeRenderer ShapeRenderer;
	public Profiler Profiler;
	public com.engine2d.Input Input;
	public SizeF ScreenSize;
	
	Context C;
	String appPackName;
	
	public Game(Context c, String appPackageName)
	{
		C = c;
		appPackName = appPackageName;
		
	}
	
	@Override
	public void create()
	{
//		Exception ex = null;
//		try
//		{
		// ENGINE STARTUP CALL
		Engine2d.Setup(this.C, this.appPackName);
		
		this.ScreenSize = Utils.getScreenSize();
		this.Profiler = new Profiler();
		this.Renderer = new Renderer();
		this.Scenes = new SceneList();
		this.PrimaryScene = new TestScene(this);
		this.ShapeRenderer = new ShapeRenderer();
		this.Input = new com.engine2d.Input(this);
		
//		}
//		catch (Exception e) { ex = e; }
//		
	}
	
	// LibGdx Engine loop
	@Override
	public void render()
	{
		// START DRAWING CODE
		Profiler.Start();
		Renderer.Render(this.Scenes, true, false);
		Profiler.End();
		// END DRAWING CODE
	}
	
	@Override
	public void dispose()
	{
		this.ShapeRenderer.dispose();
		super.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		Scene[] scenes = Scenes.ToArray();
		for (int i = 0; i < scenes.length; i++)
		{
			scenes[i].Resize(width, height);
		}
		
		super.resize(width, height);
        //viewport.update(width, height);
    }

	@Override
	public void pause()
	{
		Scene[] scenes = Scenes.ToArray();
		for (int i = 0; i < scenes.length; i++)
		{
			scenes[i].Pause();
		}
		super.pause();
	}

	@Override
	public void resume()
	{
		Scene[] scenes = Scenes.ToArray();
		for (int i = 0; i < scenes.length; i++)
		{
			scenes[i].Resume();
		}
		super.resume();
	}

	public int getObjectCount()
	{
		int Result = 0;
		for (int i = 0; i < this.Scenes.Size(); i++)
		{
			Result += this.Scenes.Get(i).Objects.Size();
		}

		return Result;
	}
	
	public int getSceneCount()
	{
		return this.Scenes.Size();
	}

	public int getLineCount()
	{
		int Result = 0;
		for (int i = 0; i < this.Scenes.Size(); i++)
		{
			for (int c = 0; c < this.Scenes.Get(i).Objects.Size(); c++)
			{
				if (this.Scenes.Get(i).Objects.Get(c).Mesh != null)
				{
					Result += this.Scenes.Get(i).Objects.Get(c).Mesh.getLineCount();
				}
			}
		}

		return Result;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		for (Scene sc : this.Scenes.ToArray())
		{
			sc.onTouchUp(screenX, screenY, pointer, button);
		}
		//if (Handler != null) Handler.Handle( new InputEvent(TOUCH_UP, new int[] { screenX, screenY, pointer, button }), Ref);
		return true;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		for (Scene sc : this.Scenes.ToArray())
		{
			sc.onTouchDown(screenX, screenY, pointer, button);
		}
		//if (Handler != null) Handler.Handle( new InputEvent(TOUCH_DOWN, new int[] { screenX, screenY, pointer, button }), Ref);
		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		for (Scene sc : this.Scenes.ToArray())
		{
			sc.onTouchDragged(screenX, screenY, pointer);
		}
		//if (Handler != null) Handler.Handle( new InputEvent(TOUCH_DRAGGED, new int[] { screenX, screenY, pointer }), Ref);
		return true;
	}
}
