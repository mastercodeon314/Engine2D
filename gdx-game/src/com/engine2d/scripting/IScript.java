package com.engine2d.scripting;

//IMPORTS
import com.engine2d.*;
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

public interface IScript
{
	public ScriptSource ScriptSource;
	public boolean isCompiled;
	public boolean IsGameScript;
	public boolean IsSceneScript;
	
	
	public long UID;
	public String ErrorResults;
	
	// Returns a strig containing the compiler errors
	public String Compile();
	
	// For functionless scripts
	public void Execute();
	
	// For functioned scripts
	public Object Execute(String function);
	public Object Execute(String function, Object[] args);
	
	public void Dispose();
	
	public String getName();
	public String getSource();
}
