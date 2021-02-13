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
import com.engine2d.events.*;
import java.util.*;
import android.util.*;
import com.engine2d.utils.*;
//END IMPORTS

public class Input
{
	public static int KEY_UP = 0;
	public static int KEY_DOWN = 1;
	public static int KEY_TYPED = 2;
	public static int TOUCH_UP= 3;
	public static int TOUCH_DOWN = 4;
	public static int TOUCH_DRAGGED = 5;
	public static int MOUSE_MOVED = 6;
	public static int SCROLLED = 7;
	
	public InputEvent LatestEvent;
	public IEventHandler Handler;
	private Input Ref;
	public Input(final Game engine)
	{
		// Allow the engine to capture the back key events
		Gdx.input.setCatchBackKey(true);
		
		Handler = new IEventHandler()
		{
			@Override
			public void Handle(InputEvent evt, Input in)
			{
				in.LatestEvent = evt;
				switch (evt.EventType)
				{
					case 3: // TOUCH_UP
					{
						engine.touchUp(evt.Data[0], evt.Data[1], evt.Data[2], evt.Data[3]);
						break;
					}
					
					case 4: // TOUCH_DOWN
					{
						engine.touchDown(evt.Data[0], evt.Data[1], evt.Data[2], evt.Data[3]);
						break;
					}
					
					case 5: // TOUCH_DRAGGED
					{
						engine.touchDragged(evt.Data[0], evt.Data[1], evt.Data[2]);
						break;
					}
				}
			}
		};
		
		Ref = this;
		
		Gdx.input.setInputProcessor(new InputProcessor()
		{
			@Override
			public boolean keyUp(int keycode)
			{
				
				if (Handler != null) Handler.Handle( new InputEvent(KEY_UP, new int[] { keycode }), Ref);
				return true;
			}
			
			@Override
			public boolean keyDown(int keycode)
			{
				if (Handler != null) Handler.Handle( new InputEvent(KEY_DOWN, new int[] { keycode }), Ref);
				return true;
			}
			
			@Override
			public boolean keyTyped(char character)
			{
				if (Handler != null) Handler.Handle( new InputEvent(KEY_TYPED, new int[] { character }), Ref);
				return true;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
				if (Handler != null) Handler.Handle( new InputEvent(MOUSE_MOVED, new int[] { screenX, screenY }), Ref);
				return true;
			}
			
			@Override
			public boolean scrolled(int p1)
			{
				if (Handler != null) Handler.Handle( new InputEvent(SCROLLED, new int[] { p1 }), Ref);
				return true;
			}
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				if (Handler != null) Handler.Handle( new InputEvent(TOUCH_UP, new int[] { screenX, screenY, pointer, button }), Ref);
				return true;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				if (Handler != null) Handler.Handle( new InputEvent(TOUCH_DOWN, new int[] { screenX, screenY, pointer, button }), Ref);
				return true;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				if (Handler != null) Handler.Handle( new InputEvent(TOUCH_DRAGGED, new int[] { screenX, screenY, pointer }), Ref);
				return true;
			}
		});
	}
}


