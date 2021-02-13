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
import com.badlogic.gdx.Input.Keys;
import com.engine2d.utils.*;
import com.engine2d.events.*;
//END IMPORTS

class Cursor
{
	private boolean Visible = true;
	public int FrameTimeNotVisible = 15;
	public boolean Enabled = false;
	private int counter = 1;
	private Textfield Parent;
	public Vector2 CursorLoc;
	public int charPos = 0;

	public Cursor(Textfield t)
	{
		this.Parent = t;
		this.CursorLoc = new Vector2(Parent.Location.x + 6, Parent.Location.y);
	}

	public void Render(ShapeRenderer sr)
	{
		if (this.Enabled == true)
		{
			if (this.Visible == true)
			{
				sr.begin();
				sr.setColor(Parent.BackColor);
				sr.line(Parent.Location.x + this.CursorLoc.x, Parent.Location.y + 3, Parent.Location.x + this.CursorLoc.x, Parent.Location.y + (Parent.Size.getHeight() - 3));
				sr.end();
			}

			if (counter == this.FrameTimeNotVisible)
			{
				counter = 1;
				this.Visible = ! this.Visible;
			}
			else
			{
				counter += 1;
			}
		}
		else
		{
			if (counter != 1) counter = 1;
		}
	}
}

public class Textfield extends Control
{
	public String Text = "";
	private static int BACKSPACE = 8;
	private Cursor cur;
	private Label textLbl;
	//private CharacterSizes CharSizes;

	public Textfield(Scene sc)
	{
		super(sc);
		this.Size = new SizeF(125f, 24f);
		cur = new Cursor(this);
		textLbl = new Label(sc);
		textLbl.Location = new Vector2(this.Location.x + 6, this.Location.y);
		textLbl.ForeColor = Color.BLACK;
		textLbl.BackColor = Color.WHITE;
		this.Scene.EngineInstance.Input.Handler = new IEventHandler()
		{
			@Override
			public void Handle(InputEvent evt, com.engine2d.Input in)
			{
				keyPressedHandler(evt);
			}
		};
		//this.Scene.Controls.add(textLbl);
		//CharSizes = new CharacterSizes();
		
	}

	public String getText()
	{
		return this.Text;
	}

	public void setText(String text)
	{
		this.Text = text;
	}
	
	public void keyPressedHandler(InputEvent iEvent)
	{
		if (iEvent.EventType == com.engine2d.Input.KEY_TYPED && iEvent.Used == false)
		{
			char c = (char)iEvent.Data[0];
			iEvent.Used = true;

			if (c == BACKSPACE && this.cur.charPos > 0)
			{
				this.cur.charPos -= 1;
				this.Text = this.Text.substring(0, this.Text.length() - 1);
			}
			else
			{
				this.Text += c;
				this.cur.charPos += 1;
			}

			//Scene.Font.setUseIntegerPositions(true);
			textLbl.Font.setFixedWidthGlyphs(this.Text);
			BitmapFontCache cc = textLbl.Font.newFontCache();
			GlyphLayout d = cc.setText(this.Text, this.Location.x, this.Location.y);
			//this.Size = new SizeF(d.width, d.height);

			textLbl.setText(this.Text);

			this.Scene.t = this.Text;
			this.Scene.t = "Char size: " + d.width + ", " + d.height; //Char Name: " + Keys.toString((int)c);
		}
	}
	

	@Override
	public void Update()
	{
		super.Update();
		
//		if (this.Scene.EngineInstance.Input.LatestEvent != null)
//		{
//			com.engine2d.InputEvent iEvent = this.Scene.EngineInstance.Input.LatestEvent;
//		}
	}
	
	public void setLocation(Vector2 loc)
	{
		this.Location = loc;
		textLbl.Location = new Vector2(this.Location.x + 6, this.Location.y + 6);
	}

	@Override
	public void onFocused()
	{
		this.cur.Enabled = true;
		Gdx.input.setOnscreenKeyboardVisible(true);
	}

	@Override
	public void onLostFocus()
	{
		this.cur.Enabled = false;
		Gdx.input.setOnscreenKeyboardVisible(false);
	}

	@Override
	public void onClick(float x, float y)
	{

	}

	@Override
	public void Render(ShapeRenderer sr)
	{
		sr.begin(ShapeType.Filled);
		sr.setColor(this.ForeColor);
		sr.rect(this.Location.x, this.Location.y, this.Size.getWidth(), this.Size.getHeight());
		
		sr.end();

//		this.Scene.Batch.begin();
//		this.Scene.Font.setColor(this.BackColor);
//		this.Scene.Font.draw(this.Scene.Batch, this.Text, this.cur.CursorLoc.x, this.cur.CursorLoc.y);
//		this.Scene.Font.setColor(Color.WHITE);
//		this.Scene.Batch.end();
		cur.Render(sr);
		textLbl.Render(sr);
		
	}
}
