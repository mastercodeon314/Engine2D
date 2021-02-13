package com.engine2d.scripting.lua;

// IMPORTS
import java.io.*;
import java.util.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.ast.*;
import org.luaj.vm2.ast.Exp.AnonFuncDef;
import org.luaj.vm2.ast.Stat.FuncDef;
import org.luaj.vm2.ast.Stat.LocalFuncDef;
import org.luaj.vm2.parser.*;
// END IMPORTS

public class Utils
{
	private static ArrayList<String> Funcs;
	private static Visitor Parser = new Visitor() {
		public void visit(AnonFuncDef exp)
		{
			System.out.println("Anonymous function definition at " 
							   + exp.beginLine + "." + exp.beginColumn + "," 
							   + exp.endLine + "." + exp.endColumn);
		}

		public void visit(FuncDef stat)
		{
			Funcs.add(stat.name.name.name);
			//GotFunction(stat.name.name.name);
//						System.out.println("Function definition '" + stat.name.name.name + "' at " 
//										   + stat.beginLine + "." + stat.beginColumn + "," 
//										   + stat.endLine + "." + stat.endColumn);
//
//						System.out.println("\tName location " 
//										   + stat.name.beginLine + "." + stat.name.beginColumn + "," 
//										   + stat.name.endLine + "." + stat.name.endColumn);
		}

		public void visit(LocalFuncDef stat)
		{
			System.out.println("Local function definition '" + stat.name.name + "' at " 
							   + stat.beginLine + "." + stat.beginColumn + "," 
							   + stat.endLine + "." + stat.endColumn);
		}
	};
	
	

	public static String[] GetScriptFunctions(String source)
	{
		Funcs = new ArrayList<String>();
		String[] Result = null;

		try
		{
			LuaParser parser = new LuaParser(new StringReader(source));
			
			// Perform the parsing.
			Chunk chunk = parser.Chunk();

			// Print out line info for all function definitions.
			chunk.accept(Parser);

			Result = new String[Funcs.size()];
			Result = Funcs.toArray(Result);
		}
		catch (Exception ex)
		{
			
		}
		
		return Result;
	}

	public static boolean HasFunction(String functionName, LuaValue script)
	{
		try
		{

			script.get(functionName);
			return true;
		}
		catch (Exception ex)
		{ }
		return false;
	}

	public static String getFileNameWithoutExt(String path, String ext)
	{
		String[] parts = path.split("/");
		String Result = "";
		if (parts.length > 0)
		{
			Result += parts[parts.length - 1].replace(ext, "");
		}

		return Result;
	}

	public static String readFileText(String fPath)
	{
		File f = new File(fPath);

		if (f.exists())
		{
			FileInputStream fRead;
			byte[] rawFData = null;

			try
			{
				fRead = new FileInputStream(f);
				rawFData = new byte[fRead.available()];
				fRead.read(rawFData, 0, rawFData.length);
				fRead.close();

				return new String(rawFData);
			}
			catch (Exception ex)
			{}
		}
		return "";
	}

	public static LuaValue argToLuaArg(Object v)
	{
//		if (v.getClass() == boolean.class)
//		{
//			return LuaValue.valueOf((boolean)v);
//		}
//		
//		if (v.getClass() == String.class)
//		{
//			return LuaValue.valueOf((String)v);
//		}

		return CoerceJavaToLua.coerce(v);
	}

	public static LuaValue argToLuaArg(Object v, Object v1)
	{
		return CoerceJavaToLua.coerce(new Object[] { v, v1 });
	}

	public static LuaValue argToLuaArg(Object v, Object v1, Object v2)
	{
		return CoerceJavaToLua.coerce(new Object[] { v, v1, v2 });
	}

	public static LuaValue argToLuaArg(Object v, Object v1, Object v2, Object v3)
	{
		return CoerceJavaToLua.coerce(new Object[] { v, v1, v2, v3 });
	}

	public static LuaValue argToLuaArg(Object v, Object v1, Object v2, Object v3, Object v4)
	{
		return CoerceJavaToLua.coerce(new Object[] { v, v1, v2, v3, v4 });
	}

	public static LuaValue argToLuaArg(Object v, Object v1, Object v2, Object v3, Object v4, Object v5)
	{
		return CoerceJavaToLua.coerce(new Object[] { v, v1, v2, v3, v4, v5 });
	}

	public static LuaValue argToLuaArg(Object[] v)
	{
		return CoerceJavaToLua.coerce(v);
	}
}
