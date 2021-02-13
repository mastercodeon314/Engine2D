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

/*				TODOS
	TODO: Make button auto size to fit text
	TODO: Make label draw from top left
	to draw and size the button correctly.
*/

public class Button extends Control
{
	public boolean Fill = true;
	public int UnfilledLineWidth = 3;
	public String Text = "Button1";
	
	private Label BtnTextLbl;

	public Button(Scene sc)
	{
		super(sc);
		this.Size = new SizeF(75 *2.4f, 23 *1.5f);
		this.BtnTextLbl = new Label(sc);
		//this.BtnTextLbl.Debug = true;
		this.BackColor = Color.MAROON;
		setText(this.Text);
	}
	
	public void setText(String text)
	{
		this.Text = text;
		BtnTextLbl.setText(this.Text);
		BtnTextLbl.setLocation(new Vector2(this.Location.x + (this.Size.getWidth() / 2f), this.Location.y + (this.Size.getHeight() / 2f)));
		BtnTextLbl.setLocation(new Vector2(BtnTextLbl.Location.x - (BtnTextLbl.Size.getWidth() / 2f), BtnTextLbl.Location.y - (BtnTextLbl.Size.getHeight() / 2f)));
		//AutoSize();
	}
	
	private void AutoSize()
	{
		float tW = this.Size.getWidth();
		float tH = this.Size.getHeight();
		
		float bW = BtnTextLbl.Size.getWidth();
		float bH = BtnTextLbl.Size.getHeight();
		
		if (tW <= bW)
		{
			float diff = bW - tW;
			diff += 6;
			this.Size = new SizeF(tW + diff, tH);
		}
		
		if (tH <= bH)
		{
			float diff = bH - tH;
			diff += 6;
			this.Size = new SizeF(this.Size.getWidth(), this.Size.getHeight() + diff);
		}
	}

	@Override
	public void Update()
	{
		super.Update();
	}

	@Override
	public void Render(ShapeRenderer sr)
	{
		sr.begin();
		
		if (this.isPressed == true)
		{
			sr.setColor(Color.MAGENTA);
		}
		else
		{
			sr.setColor(this.BackColor);
		}
		
		if (this.Fill == true)
		{
			sr.set(ShapeRenderer.ShapeType.Filled);
			sr.rect(this.Location.x, this.Location.y, this.Size.getWidth(), this.Size.getHeight());
		}
		else
		{
			sr.set(ShapeRenderer.ShapeType.Line);
			sr.rect(this.Location.x, this.Location.y, this.Size.getWidth(), this.Size.getHeight());
			
			for (int i = 1; i <= UnfilledLineWidth; i++)
			{
				//sr.rect(this.Location.x - i, this.Location.y, this.Size.getWidth() + i, this.Size.getHeight() + i);
				sr.rect(this.Location.x + i, this.Location.y, this.Size.getWidth() - i, this.Size.getHeight() + i);
			}
		}
		
		sr.end();
		
		// Render the Text Label
		BtnTextLbl.Render(sr);
	}
}
