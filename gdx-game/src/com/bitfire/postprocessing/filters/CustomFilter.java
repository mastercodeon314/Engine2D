/*******************************************************************************
 * Copyright 2012 bmanuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bitfire.postprocessing.filters;

import com.bitfire.utils.ShaderLoader;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.Gdx;
import java.io.*;

public final class CustomFilter extends Filter<CustomFilter>
{
	private Matrix4 projection = new Matrix4();

	public enum Param implements Parameter
	{
		// @formatter:off
		Texture("u_texture0", 0);
		//Resolution("u_resolution", 0),
		//Time("u_time", 0);
		
		
		//Project("u_projTrans", 0);
		// @formatter:on

		private String mnemonic;
		private int elementSize;

		private Param (String mnemonic, int arrayElementSize) {
			this.mnemonic = mnemonic;
			this.elementSize = arrayElementSize;
		}

		@Override
		public String mnemonic () {
			return this.mnemonic;
		}

		@Override
		public int arrayElementSize () {
			return this.elementSize;
		}
	}
	
	public CustomFilter(String vSource, String fSource, String vName, String fName)
	{
		super();
		ShaderProgram l;
		Exception ex = null;
		try
		{

			l = ShaderLoader.fromString(vSource, fSource, vName, fName);
			super.LoadShader(l);
		}
		catch (Exception e) { ex = e; }
		rebind();
	}

	public CustomFilter(File vertexShader, File fragmentShader)
	{
		super();
		
		projection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		ShaderProgram l;
		Exception ex = null;
		try
		{
			
			l = this.LoadShader(vertexShader, fragmentShader);
			super.LoadShader(l);
		}
		catch (Exception e) { ex = e; }
		rebind();
	}
	
	private ShaderProgram LoadShader(File vert, File frag)
	{
		String vertName = vert.getName().replace(".vertex", "");
		String fragName = frag.getName().replace(".fragment", "");
		String vertSource = this.readShaderSource(vert);
		String fragSource = this.readShaderSource(frag);
		ShaderProgram Result = null;
		Exception ex = null;
		try
		{
			Result = ShaderLoader.fromString(vertSource, fragSource, vertName, fragName);
		}
		catch (Exception e) { ex = e; }
		return Result;
	}
	
	private String readShaderSource(File shader)
	{
		String Result = "";
		try
		{
			FileInputStream fs = new FileInputStream(shader);
			byte[] fsData = new byte[fs.available()];
			fs.read(fsData, 0, fsData.length);
			Result = new String(fsData);
		}
		catch (Exception ex) {}
		
		return Result;
	}
	
	@Override
	public void rebind () {
		// reimplement super to batch every parameter
		setParams(Param.Texture, u_texture0);
		//float time = Gdx.graphics.getDeltaTime();
		//Vector2 t = new Vector2(time, time);
		//setParam(Param.Time, time);
		//setParam(Param.Resolution, new Vector2((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()));
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
		//setParam(Param.Time, (float)Gdx.graphics.getDeltaTime());
		//setParam(Param.Resolution, new Vector2((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()));
	}
}
