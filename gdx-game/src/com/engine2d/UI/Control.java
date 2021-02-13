package com.engine2d.UI;

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
import android.util.*;
import android.location.*;
import com.engine2d.*;
import com.engine2d.events.*;
//END IMPORTS

public class Control
{
	public Vector2 Location;
	public Scene Scene;
	public SizeF Size;
	public Color BackColor;
	public Color ForeColor;
	public boolean Visible = true;
	public boolean Focused = false;
	public boolean isPressed = false;
	public boolean isClicked = false;
	private boolean skipClick = false;
	public IEvent Clicked;
	public IEvent JustClicked;
	public boolean AutoFireEnabled = false;

	public Control(Scene sc)
	{
		this.Scene = sc;
		this.Location = Vector2.Zero;
		this.Size = new SizeF(0, 0);
		this.BackColor = Color.BLACK;
		this.ForeColor = Color.WHITE;
	}

	public void Update()
	{
		if (skipClick == true)
		{
			this.isClicked = false;
		}

		
		
		if (Gdx.input.isTouched())
		{
			Vector3 t = new Vector3();
			t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			Scene.Camera.unproject(t);

			if (t.x >= this.Location.x && t.x <= this.Location.x + this.Size.getWidth())
			{
				if (t.y >= this.Location.y && t.y <= this.Location.y + this.Size.getHeight())
				{
					if (AutoFireEnabled == false)
					{
						this.isPressed = true;

						if (this.skipClick == false) // comment out to enable auto fire
						{
							if (Clicked != null) Clicked.Fire();
							
							this.onClick(t.x, t.y);
							this.onFocused();

							this.skipClick = true;
							this.isClicked = true;
							this.Focused = true;
						}
					}
					else
					{
						if (Clicked != null) Clicked.Fire();
						this.onClick(t.x, t.y);
						this.onFocused();

						this.skipClick = true;
						this.isClicked = true;
						this.Focused = true;
					}
				}
				else
				{
					if (JustClicked != null && this.isPressed == true) JustClicked.Fire();
					
					this.isPressed = false;
					this.skipClick = false;
					this.Focused = false;
					this.onLostFocus();
					
				}
			}
			else
			{
				if (JustClicked != null && this.isPressed == true) JustClicked.Fire();
				
				this.isPressed = false;
				this.skipClick = false;
				this.Focused = false;
				this.onLostFocus();
				
			}
		}
		else
		{
			if (JustClicked != null && this.isPressed == true) JustClicked.Fire();
			this.isPressed = false;
			
			this.skipClick = false;
		}
		
		if (Gdx.input.justTouched())
		{
			Vector3 t = new Vector3();
			t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			Scene.Camera.unproject(t);

			if (t.x >= this.Location.x && t.x <= this.Location.x + this.Size.getWidth())
			{
				if (t.y >= this.Location.y && t.y <= this.Location.y + this.Size.getHeight())
				{
					if (JustClicked != null) JustClicked.Fire();
				
				}
			}
		}
	}
	
	public void onFocused()
	{
		
	}
	
	public void onLostFocus()
	{
		
	}
	
	public void onClick(float x, float y)
	{
		
	}

	public void Render(ShapeRenderer sr)
	{

	}
}
