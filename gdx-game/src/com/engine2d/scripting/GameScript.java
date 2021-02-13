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

public class GameScript extends Script
{
	public boolean IsDrawScript = false;
	
	public GameScript()
	{
		super();
		this.IsGameScript = true;
	}
	
	public GameScript(String name, String source)
	{
		super(name, source);
		this.IsGameScript = true;
	}
	
	public GameScript(String path, Object ext)
	{
		super(path, ext);
		this.IsGameScript = true;
	}
	
	public void UpdateFunc(GameObject obj)
	{
		this.Execute("update", new Object[] {obj});
	}
	
	public void DrawFunc(ShapeRenderer sr)
	{
		this.Execute("draw", new Object[] {sr});
	}
	
}
