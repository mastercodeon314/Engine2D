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

import org.luaj.vm2.ast.*;
import org.luaj.vm2.ast.Exp.AnonFuncDef;
import org.luaj.vm2.ast.Stat.FuncDef;
import org.luaj.vm2.ast.Stat.LocalFuncDef;
//END IMPORTS

// This class compiles a script
// given name, source and executes a named functuon within the script.

public class Script implements IScript
{
	public volatile long UID;
	private volatile  boolean UidInstalized = false;
	private volatile boolean ErrorOccured = false;
	private volatile boolean IsDisposed = false;

	public String ErrorResults;
	public ScriptSource ScriptSource;

	public LuaValue Script = null;

	public String Name;

	public HashMap<String, LuaValue> functions = new HashMap<String, LuaValue>();

	public Script()
	{
		this.Instalize();
	}

	public Script(String name, String source)
	{
		this();
		this.ScriptSource = new ScriptSource(name, source);
		this.isCompiled = false;
		this.Name = this.ScriptSource.getName();
	}

	public Script(String path, Object ext)
	{
		this();
		this.ScriptSource = new ScriptSource(path, ext);
		this.isCompiled = false;
		this.Name = this.ScriptSource.getName();
	}

	public String Compile()
	{
		Exception e = null;
		try
		{
			this.Script = Lua.Globals.load(this.ScriptSource.Source);

			this.Script.call(); // Init the script
			
			// gets handles to all the functions
			// in this script.
			// Must dothis since mutiple scripts with the same
			// function name will overwrite the handles in Globals.
			LoadFunctions();
			
			this.isCompiled = true;
		}
		catch (Exception ex)
		{
			e = ex;
			this.isCompiled = false;
			this.ErrorResults = ex.getMessage();
			return ex.getMessage();
		}
		return "";
	}

	private void LoadFunctions()
	{
		String[] funcs = Utils.GetScriptFunctions(this.ScriptSource.Source);

		for (String f : funcs)
		{
			this.functions.put(f, Lua.Globals.get(f));
		}
	}

	private LuaValue getFunction(String name)
	{
		return this.functions.get(name);
	}

	// Executes any code thats not in a function
	public void Execute()
	{
		if (this.isCompiled == true)
		{
			Exception ex = null;
			Object res = null;
			try
			{
				res = this.Script.call();
			}
			catch (Exception e)
			{
				ex = e;
			}
		}
	} // Only for functionless scripts

	public Object Execute(String function)
	{
		if (this.isCompiled == true)
		{
			//if (Utils.HasFunction(function, this.Script) == true)
			{
				//LuaValue fun = this.Script.get(function);

				Exception ex = null;
				Object res = null;
				try
				{
					res = this.getFunction(function).invoke();
				}
				catch (Exception e)
				{
					ex = e;
				}

				return res;
			}
		}
		return null;
	}

	public Object Execute(String function, Object[] args)
	{
		if (this.isCompiled == true)
		{
			//if (Utils.HasFunction(function, this.Script) == true)
			{
				//LuaValue fun = this.Script.get(function);

				//String[] funcs = Utils.GetScriptFunctions("/storage/emulated/0/android/data/com.engine2d/files/scripts/Ship.lua");

				Exception ex = null;
				Varargs res = null;
				try
				{
					res = this.getFunction(function).invoke(CoerceJavaToLua.coerce(args[0]));
				}
				catch (Exception e)
				{
					ex = e;
				}

				return res;
			}
		}
		return null;
	}

	public void Instalize()
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
