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
import com.engine2d.utils.*;
import android.content.*;
import java.lang.*;
//END IMPORTS

public class Engine2d
{
	public static boolean IsSetup = false;
	
	public static void Setup(Context c, String appPackageName)
	{
		App.Context = c;
		
		// MISC ENGINE CODE
		ShaderLoader.BasePath = "";
		Gdx.graphics.setContinuousRendering(true);
		// END MISC ENGINE CODE
		
		EngineFiles.Instance = new EngineFiles(appPackageName);
		CharacterSizes.Init();
		Engine2d.IsSetup = true;
	}
}
