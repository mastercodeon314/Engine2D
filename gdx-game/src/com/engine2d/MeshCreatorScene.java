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
import android.content.*;
//END IMPORTS

public class MeshCreatorScene extends Scene
{
	public int gridHorizontal;
	public int gridVerticle;
	public boolean sliding = false;
	public Color gridColor;
	private Textfield t1;
	
	public MeshCreatorScene(Game en)
	{
		super(en);
		
		this.gridColor = Color.RED;
		this.gridHorizontal = 32;
		this.gridVerticle = 16;
		
		float sceneWidth = this.Size.getWidth();
		
		this.Location = new ScreenLocation(this.Center, sceneWidth, 0f);
		
		t1 = new Textfield(this);
		t1.setLocation(new Vector2(48f, 700f));
		this.Controls.add(t1);
		
	}
	 
	public void Render(boolean update, boolean updatePost)
	{
		if (this.sliding == false)
		{
			this.EngineInstance.PrimaryScene = this;
		}
		super.Render(update, updatePost);
	}
	
	@Override
	public void onTouchUp(float x, float y, int pointer, int button)
	{
		
		// TODO: Implement this method
		super.onTouchUp(x, y, pointer, button);
	}

	@Override
	public void onTouchDown(float x, float y, int pointer, int button)
	{
		// TODO: Implement this method
		super.onTouchDown(x, y, pointer, button);
	}

	public void onTouchDragged(float x, float y, int pointer)
	{
		super.onTouchDragged(x, y, pointer);
	}

	@Override
	public void BackgroundRender()
	{
		float sceneWidth = this.Size.getWidth();
		float sceneHeight = this.Size.getHeight();

		for (int i = 1; i < gridHorizontal; i++)
		{
			float yPos = (sceneHeight / gridHorizontal) * i;
			if (yPos == 0) yPos = 2;


			this.ShapeRenderer.begin();
			this.ShapeRenderer.rectLine(super.Location.x, super.Location.y + yPos, super.Location.x + sceneWidth, super.Location.y + yPos, 1f, this.gridColor, this.gridColor);
			this.ShapeRenderer.end();
		}

		for (int i = 1; i < gridVerticle; i++)
		{
			float xPos = (sceneWidth / gridVerticle) * i;

			this.ShapeRenderer.begin();
			this.ShapeRenderer.rectLine(super.Location.x + xPos, super.Location.y, super.Location.x + xPos, sceneHeight - super.Location.y, 1f, this.gridColor, this.gridColor);
			this.ShapeRenderer.end();
		}
	}
}
