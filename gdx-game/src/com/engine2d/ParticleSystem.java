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
import com.engine2d.utils.*;
import java.util.*;
//END IMPORTS

public class ParticleSystem
{
	public ArrayList<GameObject> Particles;
	public Scene Scene;
	public int Count = 0;
	public boolean Enabled = true;
	
	public ParticleSystem(Scene sc, int count)
	{
		this.Scene = sc;
		this.Count = count;
		
		this.Instalize();
	}
	
	private void Instalize()
	{
		this.Particles = new ArrayList<GameObject>();
		
		for (int i = 0; i < this.Count; i++)
		{
			this.Particles.add(new RandomParticle(this));
		}
	}
	
	public void Render()
	{
		for (int i = 0; i < this.Count; i++)
		{
			this.Particles.get(i).Draw(this.Scene.ShapeRenderer);
		}
	}
	
	public void Update()
	{
		for (int i = 0; i < this.Count; i++)
		{
			this.Particles.get(i).Update();
		}
	}
}
