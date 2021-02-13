package com.engine2d.scripting.lua;

// IMPORTS
import com.engine2d.EngineFiles;
import com.engine2d.scripting.ScriptList;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.compiler.*;
import java.io.*;
// END IMPORTS

public class Lua
{
	public static volatile Globals Globals;
	public static volatile String Extension;
	public static volatile ScriptList EngineScripts;

	static
	{
		Globals = new Globals();
		Globals.load(new JseBaseLib());
		Globals.load(new PackageLib());
		Globals.load(new Bit32Lib());
		Globals.load(new TableLib());
		Globals.load(new StringLib());
		Globals.load(new CoroutineLib());
		Globals.load(new JseMathLib());
		Globals.load(new JseIoLib());
		Globals.load(new JseOsLib());
		LoadState.install(Globals);
		LuaC.install(Globals);
		
		
		//Lua.Globals = JsePlatform.standardGlobals();
		Lua.Extension = ".lua";

		Lua.EngineScripts = new ScriptList();

		init();
	}

	private static void init()
	{
		String[] scripts = EngineFiles.Instance.Scripts.list();
		for (String s : scripts)
		{ 
			File f = new File(s);
			//if (f.isFile() == true)
			{
				String path = EngineFiles.Instance.Scripts.getAbsolutePath() + f.getAbsolutePath();
				Script sc = new Script(path, (Object)Lua.Extension);
				String res = sc.Compile();

				
//				File save = new File("/storage/emulated/0/errors.txt");
//				FileOutputStream outputStream;
//
//				try
//				{
//					//save.mkdirs(); // makes dirs and all child dirs in the path
//					save.createNewFile(); // Creates the file if it dosent exist
//					outputStream = new FileOutputStream(save);
//					outputStream.write(res.toString().getBytes());
//					outputStream.close();
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();	
//				}

				Lua.EngineScripts.Add(sc);
			}
		}
	}
}
