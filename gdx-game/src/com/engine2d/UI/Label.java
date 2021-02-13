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
//END IMPORTS

public class Label extends Control
{
	public String Text;
	public boolean Debug = false;
	public BitmapFont Font;
	
	//private Vector2 AbsLoc = Vector2.Zero;
	
	public Label(Scene sc)
	{
		super(sc);

		Font = new BitmapFont();
		
		//this.Batch = new SpriteBatch();
		//this.Font = new BitmapFont();
		
		this.setText("");

	}

	public void setText(String text)
	{
		this.Text = text;
		//Scene.Font.setFixedWidthGlyphs(text);
		BitmapFontCache c = this.Font.newFontCache();
		
		GlyphLayout d = c.setText(text, this.Location.x, this.Location.y);
		
		this.Size = new SizeF(d.width, d.height);
	}
	
	public void setLocation(Vector2 loc)
	{
		this.Location = loc;
		//AbsLoc = new Vector2(this.Location.x - (this.Size.getWidth() / 2f), this.Location.y + (this.Size.getHeight() / 2f));
	}

	@Override
	public void Render(ShapeRenderer sr)
	{
		super.Render(sr);
	
		this.Font.setColor(this.ForeColor);
		
		Scene.Batch.begin();
		this.Font.draw(Scene.Batch, this.Text, this.Location.x, this.Location.y + this.Size.getHeight(),
				  this.Size.getWidth(), 0, false);
				  
		Scene.Batch.end();
		
		if (this.Debug == true)
		{
			sr.begin();
			sr.set(ShapeRenderer.ShapeType.Line);
			sr.setColor(Color.YELLOW);
			sr.rect(this.Location.x, this.Location.y, this.Size.getWidth(), this.Size.getHeight());
			sr.end();
		}
	}
}
