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
import com.badlogic.gdx.backends.android.AndroidApplication;
import java.io.*;
//END IMPORTS

public class EngineFiles
{
	public static final String AndroidStorageDir = "/storage/emulated/0/Android/data/";
	public static EngineFiles Instance;
	private String EngineDirectoryName;
	private String EngineDirectoryPath;
	
	public java.io.File Meshes, Scripts, Sounds, Shaders;
	
	public EngineFiles(String appPackageName)
	{
		this.EngineDirectoryName = appPackageName;
		this.EngineDirectoryPath = EngineFiles.AndroidStorageDir + this.EngineDirectoryName;
		checkDirectories();
	}
	
	private void checkDirectories()
	{
		java.io.File engineDir = new java.io.File(this.EngineDirectoryPath);
		
		this.Meshes = new java.io.File(this.EngineDirectoryPath + "/files/meshes");
		this.Sounds = new java.io.File(this.EngineDirectoryPath + "/files/sounds");
		this.Scripts = new java.io.File(this.EngineDirectoryPath + "/files/scripts");
		this.Shaders = new java.io.File(this.EngineDirectoryPath + "/files/shaders");
		
		if (engineDir.exists() == false)
		{
			engineDir.mkdirs();
			Meshes.mkdirs();
			Sounds.mkdirs();
			Scripts.mkdirs();
			Shaders.mkdirs();
		}
		else
		{
			if (this.Meshes.exists() == false) Meshes.mkdirs();
			if (this.Sounds.exists() == false) Sounds.mkdirs();
			if (this.Scripts.exists() == false) Scripts.mkdirs();
			if (this.Shaders.exists() == false) Shaders.mkdirs();
		}
	}
	
	public void NewFolder(String name)
	{
		
	}
	
	public FileOutputStream NewFile(String dir, String name)
	{
		return null;
	}
}
