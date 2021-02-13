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
import java.util.*;
//END IMPORTS

public class GameObjectList
{
	private ArrayList<GameObject> objs;

	public GameObjectList()
	{
		objs = new ArrayList<GameObject>();
	}

	public void Add(GameObject o)
	{
		if (this.Contains(o) == -1)
		{
			objs.add(o);
		}
	}

	public void Add(GameObject o, int i)
	{
		if (i >= 0)
		{
			if (this.Contains(o) == -1)
			{
				objs.add(i, o);
			}
		}
	}

	public void Remove(GameObject o)
	{
		if (this.Contains(o) != -1)
		{
			this.objs.remove(o);
		}
	}

	public void Remove(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.objs.remove(uidPresent);
		}
	}

	public void Remove(String name)
	{
		int uidPresent = this.Contains(name);
		if (uidPresent != -1)
		{
			this.objs.remove(uidPresent);
		}
	}

	public int Contains(GameObject o)
	{
		if (this.objs.contains(o) == true)
		{
			return this.objs.indexOf(o);
		}
		return -1;
	}

	public int Contains(long uid)
	{
		Object[] objList = objs.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			GameObject obj = (GameObject)objList[i];
			if (obj.UID == uid)
			{
				return i;
			}
		}

		return -1;
	}


	public int Contains(String name)
	{
		Object[] objList = objs.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			GameObject obj = (GameObject)objList[i];
			if (obj.Name == name)
			{
				return i;
			}
		}

		return -1;
	}

	public int IndexOf(GameObject o)
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

	public GameObject[] ToArray()
	{
		GameObject[] Result = new GameObject[this.objs.size()];
		Object[] Objs = this.objs.toArray();

		for (int i = 0; i < Result.length; i++)
		{
			Result[i] = (GameObject)Objs[i];
		}

		Objs = null;

		return Result;
	}

	public void Clear(boolean disposeObjects)
	{
		if (disposeObjects == true)
		{
			Object[] objList = objs.toArray();

			for (int i = 0; i < objList.length; i++) ((GameObject)objList[i]).Dispose();
		}

		this.objs.clear();
	}

	public GameObject Get(int i)
	{
		if (i <= this.objs.size())
		{
			return this.objs.get(i);
		}
		return null;
	}

	public GameObject Get(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			return this.objs.get(uidPresent);
		}
		return null;
	}

	public GameObject Get(String name)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			return this.objs.get(namePresent);
		}
		return null;
	}

	public void Set(int i, GameObject s)
	{
		if (i <= this.objs.size())
		{
			this.objs.set(i, s);
		}
	}

	public void Set(long uid, GameObject s)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.objs.set(uidPresent, s);
		}
	}

	public void Set(String name, GameObject s)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			this.objs.set(namePresent, s);
		}
	}
	
	public int Size()
	{
		return this.objs.size();
	}
}
