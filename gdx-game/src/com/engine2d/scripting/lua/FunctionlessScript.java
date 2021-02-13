package com.engine2d.scripting.lua;

//IMPORTS
import com.engine2d.*;
import com.engine2d.scripting.*;
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

public class FunctionlessScript implements IScript
{
	public volatile long UID;
	private volatile  boolean UidInstalized = false;
	private volatile boolean ErrorOccured = false;
	private volatile boolean IsDisposed = false;
	
	public String ErrorResults;
	public ScriptSource ScriptSource;
	
	public LuaValue Script = null;
	
	protected FunctionlessScript()
	{
		this.Instalize();
	}
	
	public FunctionlessScript(String name, String source)
	{
		this();
		this.ScriptSource = new ScriptSource(name, source);
		this.isCompiled = false;
	}
	
	public FunctionlessScript(String path, Object ext)
	{
		this();
		this.ScriptSource = new ScriptSource(path, ext);
		this.isCompiled = false;
	}
	
	public String Compile()
	{
		Exception e = null;
		try
		{
			this.Script = Lua.Globals.load(this.ScriptSource.Source);
			this.isCompiled = true;
		}
		catch (Exception ex)
		{
			e = ex;
			this.ErrorResults = ex.getMessage();
			return ex.getMessage();
		}
		return "";
	}
	
	public void Execute()
	{
		this.Script.invoke();
	}
	
	// For functioned scripts
	public Object Execute(String function) { return null; }
	public Object Execute(String function, Object[] args) { return null; }
	
	private void Instalize()
	{
		if (this.UidInstalized == false && this.IsDisposed == false)
		{
			this.UID = UidGenerator.GetUid();

			if (this.UID != -1)
			{
				this.UidInstalized = true;
			}
			else
			{
				this.ErrorOccured = true;
			}
		}
	}

	public void Dispose()
	{
		if (this.UidInstalized == true && this.IsDisposed == false)
		{
			UidGenerator.ReleaseUid(this.UID);
			this.UidInstalized = false;
			this.IsDisposed = true;
		}
	}
	
	public String getName()
	{
		return this.ScriptSource.getName();
	}

	public String getSource()
	{
		return this.ScriptSource.getSource();
	}
}
