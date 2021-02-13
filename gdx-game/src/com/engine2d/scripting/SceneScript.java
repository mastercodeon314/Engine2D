package com.engine2d.scripting;

//IMPORTS
import com.engine2d.*;
import com.engine2d.scripting.lua.*;
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
import java.util.*;
import java.io.*;
import android.util.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
//END IMPORTS

public class SceneScript extends Script
{
	public boolean IsDrawScript = false;

	public SceneScript()
	{
		super();
		this.IsSceneScript = true;
	}

	public SceneScript(String name, String source)
	{
		super(name, source);
		this.IsSceneScript = true;
	}

	public SceneScript(String path, Object ext)
	{
		super(path, ext);
		this.IsSceneScript = true;
	}

	public void UpdateFunc(Scene sc)
	{
		this.Execute("update", new Object[] {sc});
	}

	public void RenderFunc(Scene sc)
	{
		this.Execute("render", new Object[] {sc});
	}

}
