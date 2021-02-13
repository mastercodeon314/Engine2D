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
import com.badlogic.gdx.files.*;
import android.util.*;
//END IMPORTS

public class SpriteObject extends GameObject
{
	public Texture Sprite;
	
	private SpriteBatch batch;
	
	public SpriteObject(Scene s, FileHandle sprite)
	{
		super(s);
		this.Sprite = new Texture(sprite);
		this.Size = SpriteObject.getSpriteSize(sprite);
		batch = new SpriteBatch();
	}
	
	public static SizeF getSpriteSize(FileHandle spriteFile)
	{
		TextureData sprite = TextureData.Factory.loadFromFile(spriteFile, false);
		SizeF Result = new SizeF(sprite.getWidth(), sprite.getHeight());
		
		sprite.disposePixmap();
		return Result;
	}

	@Override
	public void Draw(ShapeRenderer sr)
	{
		Vector2 screenLoc = this.Location.ScreenCoords;
		
		batch.begin();
		batch.draw(this.Sprite, screenLoc.x, screenLoc.y);
		batch.end();
	}
}
