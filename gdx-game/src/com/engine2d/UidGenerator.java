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

public class UidGenerator
{
	private static ArrayList<Long> Uids = new ArrayList<Long>();
	
	public static int UidExists(long uid)
	{
		if (Uids.size() > 0)
		{
			for (int i = 0; i < Uids.size(); i++)
			{
				if (Uids.get(i) == uid)
				{
					return i;
				}
			}
			
			return -1;
		}
		
		return -1;
	}
	
	public static long GetUid()
	{
		long val = MathUtils.random(Long.MAX_VALUE);
		boolean isValTaken = false;
		
		while (isValTaken == false)
		{
			for (int i = 0; i < Uids.size(); i++)
			{
				if (Uids.get(i) == val)
				{
					isValTaken = true;
					break;
				}
			}
			
			if (isValTaken == true)
			{
				isValTaken = false;
				val = MathUtils.random(Long.MAX_VALUE);
				continue;
			}
			else
			{
				return val;
			}
		}
		
		return -1L;
	}
	
	public static void ReleaseUid(long uid)
	{
		int uidIndex = UidGenerator.UidExists(uid);
		if (uidIndex != -1)
		{
			Uids.remove(uidIndex);
		}
	}
}
