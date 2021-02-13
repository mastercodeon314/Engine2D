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
import java.io.*;
//END IMPORTS

public class Shader
{
	public File VertexFile;
	public File FragmentFile;
	
	public Shader(String vertPath, String fragPath)
	{
		this.VertexFile = new File(vertPath);
		this.FragmentFile = new File(fragPath);
	}
	
	public boolean FilesExist()
	{
		return this.VertexFile.exists() == true && this.FragmentFile.exists();
	}
	
	public ShaderProgram getShaderProgram()
	{
		ShaderProgram Result = null;
		if (this.FilesExist() == true)
		{
			Result = ShaderLoader.fromFile(this.VertexFile.getAbsolutePath(), this.FragmentFile.getAbsolutePath());
		}
		return Result;
	}
}
