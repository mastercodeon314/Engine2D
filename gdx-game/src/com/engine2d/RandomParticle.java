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

public class RandomParticle extends GameObject
{
	public Color Color;
	
	public ParticleSystem pSys;

	public RandomParticle(ParticleSystem s)
	{
		super(s.Scene);

		this.pSys = s;

		this.Visible = false;
		//this.Location = Location.getRandomLocation(s.Scene).toWorldCoordinates(s.Scene.Center, new Vector2(s.Scene.Location.x, s.Scene.Location.y));
		this.Color = Utils.getRandomColor();
	}

	public RandomParticle(ParticleSystem s, boolean addToSystem)
	{
		this(s);

		//Adds this Particle to the particle system's particle list
		if (addToSystem == true) this.pSys.Particles.add(this);
	}

	@Override
	public void Draw(ShapeRenderer sr)
	{
		if (this.Visible == true)
		{
			sr.begin();
			Scene.ShapeRenderer.setColor(this.Color);

			Scene.ShapeRenderer.point((this.Location.x + Scene.Center.x) + Scene.Location.x, (this.Location.y + Scene.Center.y) + Scene.Location.y, 0);

			sr.end();
			//sr.rectLine(0, 0, 100, 100, 1, Color.BLUE, Color.RED);
			//sr.line(start.x, start.y, 100, 100);

			//Scene.sr.end();
		}
	}
	
	@Override
	public void Update()
	{
		super.Update();
	}
}
