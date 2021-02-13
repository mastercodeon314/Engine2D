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

public class SplashScene extends Scene
{
	Label LoadingLbl;
	public SplashScene(Game g)
	{
		super(g);
		Init();
	}
	
	private void Init()
	{
		LoadingLbl = new Label(this);
		LoadingLbl.setText("2D Engine Loading...");
		LoadingLbl.setLocation(new Vector2(this.Center.x - (LoadingLbl.Size.getWidth() / 2f), this.Center.y));
		this.Controls.add(LoadingLbl);
	}
}
