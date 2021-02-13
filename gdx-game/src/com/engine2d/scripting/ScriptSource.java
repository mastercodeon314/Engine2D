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

public class ScriptSource
{
	public String Name = "";
	public String Source = "";
	public String Path = "";
	
	public ScriptSource(String name, String source)
	{
		this.Name = name;
		this.Source = source;
	}
	
	// WARNING: NEVER PASS ANYTHING TO EXT BUT A STRING!
	public ScriptSource(String path, Object ext)
	{
		this.Name = Utils.getFileNameWithoutExt(path, (String)ext);
		this.Source = Utils.readFileText(path);
		this.Path = path;
	}
	
	public String getName()
	{
		return this.Name;
	}
	
	public String getSource()
	{
		return this.Source;
	}
}
