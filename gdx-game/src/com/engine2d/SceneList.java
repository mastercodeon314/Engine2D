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

public class SceneList
{
	private ArrayList<Scene> scenes;
	public long Uid;
	public SceneList()
	{
		scenes = new ArrayList<Scene>();
		
		
	}

	public void Add(Scene o)
	{
		if (this.Contains(o) == -1)
		{
			scenes.add(o);
		}
	}

	public void Add(Scene o, int i)
	{
		if (i >= 0)
		{
			if (this.Contains(o) == -1)
			{
				scenes.add(i, o);
			}
		}
	}

	public void Remove(Scene o)
	{
		if (this.Contains(o) != -1)
		{
			this.scenes.remove(o);
		}
	}

	public void Remove(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.scenes.remove(uidPresent);
		}
	}

	public void Remove(String name)
	{
		int uidPresent = this.Contains(name);
		if (uidPresent != -1)
		{
			this.scenes.remove(uidPresent);
		}
	}

	public int Contains(Scene o)
	{
		if (this.scenes.contains(o) == true)
		{
			return this.scenes.indexOf(o);
		}
		return -1;
	}

	public int Contains(long uid)
	{
		Object[] objList = scenes.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			Scene obj = (Scene)objList[i];
			if (obj.UID == uid)
			{
				return i;
			}
		}

		return -1;
	}


	public int Contains(String name)
	{
		Object[] objList = scenes.toArray();

		for (int i = 0; i < objList.length; i++)
		{
			Scene obj = (Scene)objList[i];
			if (obj.Name == name)
			{
				return i;
			}
		}

		return -1;
	}

	public int IndexOf(Scene o)
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

	public Scene[] ToArray()
	{
		Scene[] Result = new Scene[this.scenes.size()];
		Object[] Objs = this.scenes.toArray();
		
		for (int i = 0; i < Result.length; i++)
		{
			Result[i] = (Scene)Objs[i];
		}
		
		Objs = null;
		
		return Result;
	}

	public void Clear(boolean disposeObjects)
	{
		if (disposeObjects == true)
		{
			Object[] objList = scenes.toArray();

			for (int i = 0; i < objList.length; i++) ((Scene)objList[i]).Dispose();
		}

		this.scenes.clear();
	}

	public Scene Get(int i)
	{
		if (i <= this.scenes.size())
		{
			return this.scenes.get(i);
		}
		return null;
	}

	public Scene Get(long uid)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			return this.scenes.get(uidPresent);
		}
		return null;
	}

	public Scene Get(String name)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			return this.scenes.get(namePresent);
		}
		return null;
	}

	public void Set(int i, Scene s)
	{
		if (i <= this.scenes.size())
		{
			this.scenes.set(i, s);
		}
	}

	public void Set(long uid, Scene s)
	{
		int uidPresent = this.Contains(uid);
		if (uidPresent != -1)
		{
			this.scenes.set(uidPresent, s);
		}
	}

	public void Set(String name, Scene s)
	{
		int namePresent = this.Contains(name);
		if (namePresent != -1)
		{
			this.scenes.set(namePresent, s);
		}
	}
	
	public int Size()
	{
		return this.scenes.size();
	}
}
