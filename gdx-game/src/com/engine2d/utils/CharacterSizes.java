package com.engine2d.utils;

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
import android.util.*;
//END IMPORTS

public class CharacterSizes
{
	public static Hashtable<Character, SizeF> CharSizes;
	
	private static BitmapFont Font;
	private static BitmapFontCache c;
	
	public static void Init()
	{
		CharSizes = new Hashtable<Character, SizeF>();
		Font = new BitmapFont();
		c = Font.newFontCache();
		
		int i = Character.MIN_VALUE;

		for (; i < 255; i++)
		{
			//if (Character.isLetterOrDigit((char)i) == true)
			{
				String g = String.valueOf(i);
				GlyphLayout d = c.setText(g, 0, 0);
				CharSizes.put(Character.valueOf((char)i), new SizeF(d.width, d.height));
			}
		}
//		Hashtable<Character, SizeF> s = CharSizes;
//		int a = 0;
//		a++;
	}
}
