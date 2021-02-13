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

public class ScriptList
{
	private ArrayList<Script> Scripts;
	//public long Uid;
	public ScriptList()
	{
		Scripts = new ArrayList<Script>();
	}

	public void Add(Script o)
	{
		if (this.Contains(o) == -1)
		{
			Scripts.add(o);
		}
	}

	public void Add(Script o, int i)
	{
		if (i >= 0)
		{
			if (this.Contains(o) == -1)
			{
				Scripts.add(i, o);
			}
		}
	}

	public void Remove(Script o)
	{
		if (this.Contains(o) != -1)
		{
			this.Scripts.remove(o);
		}
	}

	public void Remove(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.Scripts.remove(uidPresent);
		}
	}

	public void Remove(String name)
	{
		int uidPresent = this.Contains(name);
		if (uidPresent != -1)
		{
			this.Scripts.remove(uidPresent);
		}
	}

	public int Contains(Script o)
	{
		if (this.Scripts.contains(o) == true)
		{
			return this.Scripts.indexOf(o);
		}
		return -1;
	}

	public int Contains(long uid)
	{
		Object[] objList = Scripts.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			Script obj = (Script)objList[i];
			if (obj.UID == uid)
			{
				return i;
			}
		}

		return -1;
	}


	public int Contains(String name)
	{
		Object[] objList = Scripts.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			Script obj = (Script)objList[i];
			//if (obj.ScriptSource != null)
			{
				//String oName = obj.ScriptSource.Name;
				if (obj.ScriptSource.Name.equals(name) == true)
				{
					return i;
				}
			}
		}

		return -1;
	}

	public int IndexOf(Script o)
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

	public Script[] ToArray()
	{
		Script[] Result = new Script[this.Scripts.size()];
		Object[] Objs = this.Scripts.toArray();

		for (int i = 0; i < Result.length; i++)
		{
			Result[i] = (Script)Objs[i];
		}

		Objs = null;

		return Result;
	}

	public void Clear(boolean disposeObjects)
	{
		if (disposeObjects == true)
		{
			Object[] objList = Scripts.toArray();

			for (int i = 0; i < objList.length; i++) ((Script)objList[i]).Dispose();
		}

		this.Scripts.clear();
	}

	public Script Get(int i)
	{
		if (i <= this.Scripts.size())
		{
			return this.Scripts.get(i);
		}
		return null;
	}

	public Script Get(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			return this.Scripts.get(uidPresent);
		}
		return null;
	}

	public Script Get(String name)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			return this.Scripts.get(namePresent);
		}
		return null;
	}

	public void Set(int i, Script s)
	{
		if (i <= this.Scripts.size())
		{
			this.Scripts.set(i, s);
		}
	}

	public void Set(long uid, Script s)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.Scripts.set(uidPresent, s);
		}
	}

	public void Set(String name, Script s)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			this.Scripts.set(namePresent, s);
		}
	}

	public int Size()
	{
		return this.Scripts.size();
	}
}
