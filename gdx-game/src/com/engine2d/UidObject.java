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

public class UidObject
{
	public volatile long UID;
	private volatile  boolean UidInstalized = false;
	private volatile boolean ErrorOccured = false;
	private volatile boolean IsDisposed = false;
	
	public UidObject()
	{
		this.Instalize();
	}
	
	public void Reinstalize()
	{
		this.Dispose();
		
		this.Instalize();
	}

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
}
