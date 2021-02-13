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
import java.util.*;
import org.apache.commons.codec.language.bm.*;
import java.lang.*;
import com.badlogic.gdx.audio.*;
import com.engine2d.UI.*;
import com.engine2d.events.IEvent;
import com.badlogic.gdx.backends.android.AndroidApplication;
import android.content.*;
import com.engine2d.dsp.*;
import com.engine2d.dsp.synth.*;
import com.engine2d.dsp.effects.*;
import com.engine2d.dsp.musical.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import android.location.*;
import android.os.Bundle;
//import be.tarsos.dsp.AudioGenerator;


//END IMPORTS
public class TestScene3d extends Scene
{
	private ArrayList<Vector3> lines;
	
	private float angle = 0;
	private float height = 10f;
	
	private float z = 0f;
	
	public TestScene3d(Game en)
	{
		super(en);
		
		this.Debug = true;
		
		lines = new ArrayList<Vector3>();
		
		// top
		lines.add(new Vector3(-10,0,0));
		lines.add(new Vector3(10,0,0));
		
		lines.add(new Vector3(-10,0,0));
		lines.add(new Vector3(-10,0,-10));
		
		lines.add(new Vector3(10,0,0));
		lines.add(new Vector3(10,0,-10));
		
		lines.add(new Vector3(-10,0,-10));
		lines.add(new Vector3(10,0,-10));
		
		
		// middle
		lines.add(new Vector3(-10,0,0));
		lines.add(new Vector3(-10,20,0));
		
		lines.add(new Vector3(10,0,0));
		lines.add(new Vector3(10,20,0));
		
		lines.add(new Vector3(-10,0,-10));
		lines.add(new Vector3(-10,20,-10));
		
		lines.add(new Vector3(10,0,-10));
		lines.add(new Vector3(10,20,-10));
		
		// bottom
		lines.add(new Vector3(-10,20,0));
		lines.add(new Vector3(10,20,0));

		lines.add(new Vector3(-10,20,0));
		lines.add(new Vector3(-10,20,-10));

		lines.add(new Vector3(10,0,0));
		lines.add(new Vector3(10,0,-10));

		lines.add(new Vector3(-10,20,-10));
		lines.add(new Vector3(10,20,-10));
		
		
		this.updateCam();
	}
	
	public void Render(boolean update, boolean updatePost)
	{
		//Vector3 lnSt = new Vector3(-10, 0, 0);
		//Vector3 lnEn = new Vector3(10, 0, 0);
		
		super.Render(update, updatePost);
		
		
		this.PostProcessor.capture();
		//this.Camera.update();
		
		this.ShapeRenderer.setColor(Color.RED);
		this.ShapeRenderer.setProjectionMatrix(this.Camera.combined);
		this.ShapeRenderer.begin();
		
		for (int i = 0; i < this.lines.size() && this.lines.size() % 2 == 0; i += 2)
		{
			Vector3 a = this.lines.get(i);
			Vector3 b = this.lines.get(i + 1);
			
			a.set(a.x, a.y, a.z + z);
			b.set(b.x, b.y, b.z + z);
			
			this.ShapeRenderer.line(a, b);
		}
		
		this.ShapeRenderer.end();
		
		this.PostProcessor.render();
	}
	
	public static Vector3 returnPosArroundObj(Vector3 posObject, float angleDegrees, float radius, float height) {
		Float angleRadians = angleDegrees * MathUtils.degreesToRadians;
		Vector3 position = new Vector3();
		position.set(radius * MathUtils.sin(angleRadians), height, radius * MathUtils.cos(angleRadians));
		//position.add(posObject); //add the position so it would be arround object
		return position;
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
		
		this.z += 1;
		
		// TODO: Implement this method
		super.onTouchDown(x, y, pointer, button);
	}

	float oAngle = angle;
	float oHeight = height;
	
	public void onTouchDragged(float x, float y, int pointer)
	{
		if (this.Dragging == false)
		{
			oAngle = angle;
			oHeight = height;
		}
		
		super.onTouchDragged(x, y, pointer);
	
		
		
		if (this.Dragging == true)
		{
			Vector2 res = this.getDragDir(new Vector2(x, y));
			
			float up = res.y;
			float left = res.x;
			if (left != 0)
			{
			if (angle + left > 360)
			{
				angle = (oAngle + left) - 360;
			}
			else
			{
				angle = oAngle + left;
			}
				
				this.DebugStrings.set(11, "up: " + up);
				this.DebugStrings.set(12, "height: " + height);
				this.DebugStrings.set(13, "oheight: " + oHeight);
			this.DebugStrings.set(14, "angle: " + angle);
			this.DebugStrings.set(15, "oAngle: " + oAngle);
				
			float a = 0;
			}
			
			if (up != 0)
			{
				height = oHeight + (up * 0.1f);
			}
			
			this.updateCam();
		}
	}
	
	private void updateCam()
	{
		Vector3 camPos = this.returnPosArroundObj(new Vector3(0, 5, 0), angle, 5, height);
		this.Camera.position.set(camPos);
		this.Camera.lookAt(0, 5, 0);
		this.Camera.update();
	}
	
	private Vector2 getDragDir(Vector2 cur)
	{
		return new Vector2(this.DragStart.x - cur.x, this.DragStart.y - cur.y);
	}
}
