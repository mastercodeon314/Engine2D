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

package com.bitfire.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.bitfire.postprocessing.PostProcessorEffect;
import com.bitfire.postprocessing.filters.RadialBlur;
import com.bitfire.postprocessing.filters.CustomFilter;
import java.io.*;

/** Implements a custom effect with specified vertex and fragment shaders. */
public final class CustomEffect extends PostProcessorEffect
{
	private CustomFilter customFilter = null;
	
	public CustomEffect(File vert, File frag)
	{
		customFilter = new CustomFilter(vert, frag);
	}
	
	public CustomEffect(String vSource, String fSource, String vName, String fName)
	{
		customFilter = new CustomFilter(vSource, fSource, vName, fName);
	}
	
	public CustomEffect(String[] vert, String[] frag)
	{
		this(vert[1], frag[1], vert[0], frag[0]);
	}

	@Override
	public void dispose()
	{
		if (customFilter != null)
		{
			customFilter.dispose();
			customFilter = null;
		}
	}

	@Override
	public void rebind()
	{
		customFilter.rebind();
	}

	@Override
	public void render(FrameBuffer src, FrameBuffer dest)
	{
		restoreViewport(dest);
		customFilter.setInput(src).setOutput(dest).render();
	}
}
