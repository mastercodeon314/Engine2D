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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.Gdx;

public final class Test extends Filter<Test>
{
	private final Matrix4 projectionMatrix = new Matrix4();
	

	public enum Param implements Parameter {
		// @formatter:off
		Texture("u_texture0", 0), Projection("u_projModelView", 0);
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

	public Test ()
	{
		
		super(ShaderLoader.fromFile("test", "test"));
		
		projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		rebind();
	}

	@Override
	public void rebind () {
		// reimplement super to batch every parameter
		setParams(Param.Texture, u_texture0);
		setParam(Param.Projection, projectionMatrix);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
	}
}
