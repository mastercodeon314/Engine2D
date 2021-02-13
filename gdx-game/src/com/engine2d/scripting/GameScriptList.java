package com.engine2d.scripting;

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
import java.util.*;
import com.engine2d.scripting.lua.*;
import com.engine2d.scripting.*;

//END IMPORTS

public class GameScriptList
{
	private ArrayList<IScript> IScripts;
	//public long Uid;
	public GameScriptList()
	{
		IScripts = new ArrayList<IScript>();
	}

	public void Add(IScript o)
	{
		if (this.Contains(o) == -1)
		{
			IScripts.add(o);
		}
	}

	public void Add(IScript o, int i)
	{
		if (i >= 0)
		{
			if (this.Contains(o) == -1)
			{
				IScripts.add(i, o);
			}
		}
	}

	public void Remove(IScript o)
	{
		if (this.Contains(o) != -1)
		{
			this.IScripts.remove(o);
		}
	}
	

	public void Remove(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.IScripts.remove(uidPresent);
		}
	}

	public void Remove(String name)
	{
		int uidPresent = this.Contains(name);
		if (uidPresent != -1)
		{
			this.IScripts.remove(uidPresent);
		}
	}

	public int Contains(IScript o)
	{
		if (this.IScripts.contains(o) == true)
		{
			return this.IScripts.indexOf(o);
		}
		return -1;
	}

	public int Contains(long uid)
	{
		Object[] objList = IScripts.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			IScript obj = (IScript)objList[i];
			if (obj.UID == uid)
			{
				return i;
			}
		}

		return -1;
	}


	public int Contains(String name)
	{
		Object[] objList = IScripts.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			IScript obj = (IScript)objList[i];
			//if (obj.IScriptSource != null)
			{
				//String oName = obj.IScriptSource.Name;
				if (obj.ScriptSource.Name.equals(name) == true)
				{
					return i;
				}
			}
		}

		return -1;
	}

	public int IndexOf(IScript o)
	{
		return this.Contains(o);
	}

	public int IndexOf(long uid)
	{
		return this.Contains(uid);
	}

	public int IndexOf(String name)
	{
		return this.Contains(name);
	}

	public IScript[] ToArray()
	{
		IScript[] Result = new IScript[this.IScripts.size()];
		Object[] Objs = this.IScripts.toArray();

		for (int i = 0; i < Result.length; i++)
		{
			Result[i] = (IScript)Objs[i];
		}

		Objs = null;

		return Result;
	}

	public void Clear(boolean disposeObjects)
	{
		if (disposeObjects == true)
		{
			Object[] objList = IScripts.toArray();

			for (int i = 0; i < objList.length; i++) ((IScript)objList[i]).Dispose();
		}

		this.IScripts.clear();
	}

	public IScript Get(int i)
	{
		if (i <= this.IScripts.size())
		{
			return this.IScripts.get(i);
		}
		return null;
	}

	public IScript Get(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			return this.IScripts.get(uidPresent);
		}
		return null;
	}

	public IScript Get(String name)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			return this.IScripts.get(namePresent);
		}
		return null;
	}

	public void Set(int i, IScript s)
	{
		if (i <= this.IScripts.size())
		{
			this.IScripts.set(i, s);
		}
	}

	public void Set(long uid, IScript s)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.IScripts.set(uidPresent, s);
		}
	}

	public void Set(String name, IScript s)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			this.IScripts.set(namePresent, s);
		}
	}

	public int Size()
	{
		return this.IScripts.size();
	}
}
