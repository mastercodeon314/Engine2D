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
import java.util.*;
import org.apache.commons.codec.language.bm.*;
import java.lang.*;
import com.badlogic.gdx.audio.*;
import com.engine2d.UI.*;
import com.engine2d.events.IEvent;
import com.badlogic.gdx.backends.android.AndroidApplication;
import android.content.*;
import com.engine2d.dsp.*;
import com.engine2d.dsp.synth.*;
import com.engine2d.dsp.effects.*;
import com.engine2d.dsp.musical.*;
import com.engine2d.utils.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import android.location.*;
import android.os.Bundle;
import android.util.SizeF;
//import be.tarsos.dsp.AudioGenerator;


//END IMPORTS

public class TestScene extends Scene
{
	private Button b;
	private Button b2;

//	private Label bearingLbl;
//	private Label altLbl;
//	private Label latLbl;
//	private Label longLbl;
//  private Label speedLbl;
//	private Label timeLbl;
//	private Label timeDiffLbl;
//	private Label arrcLbl;

	private long gpsTime = -1;
	private long gpsOldTime = -1;
	private GameObject ship;
	// Thread 1
//	AudioGenerator generator;
//	Oscillator wave;
//	SineWave wave2;
//	
//	
//	AudioGenerator generator2;
//	PulseWave wave3;
//	SineWave wave4;
	LocationManager man;
	android.location.Location gpsLoc;

	boolean run = false;
	Random rnd = new Random();
	private Vector3 accel = new Vector3();
	boolean gpsRecived = false;
	//private Compile c;
	ShipMovement s;
	
	public TestScene(Game en)
	{
		super(en);
		this.FXEnabled = false;
		
		//this.Size = new android.util.SizeF(300f, 300f);
		//this.Location.WorldCoords.add(0, 100);
		//Mesh objM = ObjFileReader.get2dMeshFromObj("Test", null);
		
		//ship = new SpriteObject(this, Gdx.files.internal("android.jpg"));
		
		// Tests rendering large amounts of basic Objects.
//		for (int i = 0; i < 200; i++)
//		{
//			this.Objects.Add(new TriangleObject(this));
//		}
		
		ship = new ShipObject(this);
		ship.Location = new WorldLocation(this.Center, 0, 0);
		this.Objects.Add(ship);
		
		s = new ShipMovement(ship);
		ship.MovementHandler = s;
		// Cornflower RGB / HEX value
		// 100
		// 149
		// 237
		// #6495ed
		
		//int cornflower = Integer.valueOf("6495ED", 16);
		//this.BackgroundColor = new Color(cornflower);
		
//		wave = new SquareWave(440f, 0.7f, 44100f);
//		wave.Enabled = false;
//		//wave2 = new SineWave(220f, 0.9f, 44100f);
//		
//		generator = new AudioGenerator(1024, 0);
//		generator.addAudioProcessor(wave);
		//generator.addAudioProcessor(new DelayEffect(0.4, 0.45, 44100));
		//generator.addAudioProcessor(wave2);
//
//		Note n = new Note("F", 5, null);
//
//
//		bearingLbl = new Label(this);
//		bearingLbl.Location = new Vector2(12, this.Size.getHeight() - 48);
//		bearingLbl.setText("Note Name: " + n.Name);
//
//		altLbl = new Label(this);
//		altLbl.Location = new Vector2(12, bearingLbl.Location.y - 18);
//		altLbl.setText("Note Octave: " + n.Octave);
//
//		latLbl = new Label(this);
//		latLbl.Location = new Vector2(12, altLbl.Location.y - 18);
//		latLbl.setText("Note Frequency: " + n.Frequency + "Hz");
//
//		longLbl = new Label(this);
//		longLbl.Location = new Vector2(12, latLbl.Location.y - 18);
//		longLbl.setText("GPS Longitude: LOADING...");
//
//		speedLbl = new Label(this);
//		speedLbl.Location = new Vector2(12, longLbl.Location.y - 18);
//		speedLbl.setText("GPS Speed: LOADING...");
//
//		timeLbl = new Label(this);
//		timeLbl.Location = new Vector2(12, speedLbl.Location.y - 18);
//		timeLbl.setText("GPS Time: LOADING...");
//
//		timeDiffLbl = new Label(this);
//		timeDiffLbl.Location = new Vector2(12, timeLbl.Location.y - 18);
//		timeDiffLbl.setText("GPS Interval: LOADING...");
//
//		arrcLbl = new Label(this);
//		arrcLbl.Location = new Vector2(12, timeDiffLbl.Location.y - 18);
//		arrcLbl.setText("GPS Accuracy: LOADING...");
//
//		man = (LocationManager)App.Context.getSystemService(App.Context.LOCATION_SERVICE);
//
		// [[GPS TESTING]] \\
//		// Define a listener that responds to location updates
//		LocationListener locationListener = new LocationListener() 
//		{
//			@Override
//			public void onLocationChanged(android.location.Location location)
//			{
//				
//				gpsLoc = location;
//
//				gpsRecived = true;
//				
//				if (gpsTime == -1)
//				{
//					gpsTime = gpsLoc.getTime();
//					gpsOldTime = 0;
//				}
//				else
//				{
//					gpsOldTime = gpsTime;
//					gpsTime = gpsLoc.getTime();
//				}
//			}
//
//			@Override
//			public void onStatusChanged(String provider, int status, Bundle extras)
//			{
//				gpsRecived = true;
//			}
//
//			@Override
//			public void onProviderEnabled(String provider)
//			{
//				gpsRecived = true;
//			}
//
//			@Override
//			public void onProviderDisabled(String provider)
//			{}
//		};
//
//		App.Context.getMainLooper().prepare();
//
//	
//		// Register the listener with the Location Manager to receive location updates
//		man.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener, App.Context.getMainLooper());
//
//
//		man.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, App.Context.getMainLooper());
		// [[GPS TESTING]] \\
		
//		generator2 = new AudioGenerator(1024, 0);
//		generator2.addAudioProcessor(wave3);
//		generator2.addAudioProcessor(wave4);
//
//		generator2.addAudioProcessor(new AudioPlayer( new AudioFormat(44100, 16, 1, true, false)));
//
		this.Name = "TestScene";

//		ShipMesh m = new ShipMesh(this);
//		m.Location = new Location(100, 100);

		//c =new Compile();
		b = new Button(this);
		b.Location = new Vector2(100, 400);
		b.setText("Hello World");
		b.Fill = true;
		b.AutoFireEnabled = false;
		b.Clicked = new IEvent()
		{
			@Override
			public void Fire(int EventType, Object data) {}
			
			@Override
			public void Fire()
			{
				try
				{
					//toggleFX();
					//newCreatorScene();
					//ship.Mesh.Rotate(90f);
					//ship.Mesh.UpdateFromPolygon();
//					if (generator.RunThread.getState() == Thread.State.NEW)
//					{
//						generator.RunThread.start();
//						//generator2.RunThread.start();
//					}
//					else
//					{
//						wave.Enabled = !wave.Enabled;
////						int n = rnd.nextInt(880);
////						while (n < 110) n = rnd.nextInt(880);
////						wave.setFreq((float)n);
//					}
				}
				catch (Exception ex)
				{
					return;
				}
			}
		};

		b2 = new Button(this);
		b2.Location = new Vector2(96, 300);
		b2.setText("force y");
		b2.AutoFireEnabled = true;
		b2.Clicked = new IEvent()
		{
			@Override
			public void Fire(int EventType, Object data) {}
			
			@Override
			public void Fire()
			{
				//clear();
				//rndBackground();
				force(4f);
			}
		};
		b2.JustClicked = new IEvent()
		{
			@Override
			public void Fire(int EventType, Object data) {}

			@Override
			public void Fire()
			{
				//clear();
				//rndBackground();
				force(0f);
			}
		};
		
		Button b3 = new Button(this);
		b3.Location = new Vector2(96, 200);
		b3.setText("tween obj right");
		b3.AutoFireEnabled = true;
		b3.Clicked = new IEvent()
		{
			@Override
			public void Fire(int EventType, Object data) {}

			@Override
			public void Fire()
			{
				//clear();
				//rndBackground();
				force(-4);
			}
		};
		b3.JustClicked = new IEvent()
		{
			@Override
			public void Fire(int EventType, Object data) {}

			@Override
			public void Fire()
			{
				//clear();
				//rndBackground();
				force(0f);
			}
		};

		this.Controls.add(b);
		this.Controls.add(b2);
		this.Controls.add(b3);
		
		//p = new ParticleSystem(this, 5000);
		
		
		
	}
	ParticleSystem p;
	
	void force(float f)
	{
		s.ForceY(f);
	}
	
	void tweenShipL()
	{
		//Vector2 loc = ship.Location.Get();
		//ship.Location.Set(new Vector2(loc.x + 20, loc.y));
	}
	
	void tweenShipR()
	{
		Vector2 loc = ship.Location.Get();
		ship.Location.Set(new Vector2(loc.x - 20, loc.y));
	}
	
	void toggleFX()
	{
		this.FXEnabled = !this.FXEnabled;
	}
	
	void rndBackground()
	{
		this.BackgroundColor = com.engine2d.utils.Utils.getRandomColor();
	}
	
	private void setUpPostFX()
	{
		this.PostProcessor = new PostProcessor(false, false, false);
		Bloom bloom = new Bloom((int)(Gdx.graphics.getWidth() * 0.25f), (int)(Gdx.graphics.getHeight() * 0.25f));
        this.PostProcessor.addEffect(bloom);
		//this.PostProcessor.addEffect(fxaa);

		bloom.setEnabled(true);
		bloom.setBaseIntesity(6);
		bloom.setBloomIntesity(6);
		bloom.setBlurPasses(16);
		bloom.setBlurAmount(80.5f);
		bloom.setBlurType(com.bitfire.postprocessing.filters.Blur.BlurType.Gaussian3x3);
		bloom.setThreshold(.1f);
		
	}
	
	Vector2 calcOrigin(Rectangle bounds)
	{
		Vector2 Result = new Vector2();
		
		return bounds.getCenter(Result);
	}

	private void newCreatorScene()
	{
		msh = new MeshCreatorScene(this.EngineInstance);
		float sceneWidth = this.Size.getWidth();
		msh.Location.Set(new Vector2(sceneWidth, 0));
		mshDoSlide = true;
		msh.sliding = true;
	}

	boolean mshDoSlide = false;
	ArrayList<Vector2> pts;
	MeshCreatorScene msh;
	Vector2 Lastt = new Vector2(0,0);
	Vector2 WLast = new Vector2(0, 0);

	@Override
	public void onTouchUp(float x, float y, int pointer, int button)
	{
		//pts.clear();
		// TODO: Implement this method
		super.onTouchUp(x, y, pointer, button);
	}

	@Override
	public void onTouchDown(float x, float y, int pointer, int button)
	{
	
		//WLast = WorldLocation.toWorldCoordinates(this.Center, new Vector2(x, y));
		//ship.Location.Set(WLast);
		//if (this.isClicked == true)
		{
			//pts = new ArrayList<Vector2>();
		}

		//pts.add(new Vector2(x, y));
		//Lastt.set(x, y);

		// TODO: Implement this method
		super.onTouchDown(x, y, pointer, button);
	}
	
	public void onTouchDragged(float x, float y, int pointer)
	{
		super.onTouchDragged(x, y, pointer);
	}

	private float[] arr()
	{
		float[] Result = new float[pts.size() * 2];

		int i = 0;
		for (Vector2 v : pts)
		{
			Result[i] = v.x;
			Result[i + 1] = v.y;
			i += 2;
		}

		return Result;
	}
	
	@Override
	public void CapturePostFX()
	{
		this.PostProcessor.setClearColor(this.BackgroundColor);
		this.PostProcessor.capture();
	}

	@Override
	public void RenderPostFX()
	{
		this.PostProcessor.render();
	}
	
	float[] cali = null;
	
	private void axis(Vector2 pos)
	{
		this.ShapeRenderer.begin();
		this.ShapeRenderer.setColor(Color.GREEN);
		
		int length = 100;
		int padding = 12;
		
		//X Axis
		this.ShapeRenderer.line(padding + pos.x, padding + pos.y, length + pos.x, padding + pos.y);
		
		//Y Axis
		this.ShapeRenderer.line(padding + pos.x, padding + pos.y, padding + pos.x, length + pos.y);
		
		this.ShapeRenderer.end();
		
	    BitmapFont f = new BitmapFont();
	 	Vector2 loc;
	    SizeF sz;
		String t = "X";
		
		// X
		loc = new Vector2(pos.x + (length + padding), padding + pos.y);
		BitmapFontCache c = f.newFontCache();
		GlyphLayout d = c.setText(t, loc.x, loc.y);
		sz = new SizeF(d.width, d.height);
		
		f.setColor(Color.WHITE);

		this.Batch.begin();
		this.Font.draw(this.Batch, t, loc.x, loc.y + sz.getHeight(),
					   sz.getWidth(), 0, false);

		this.Batch.end();
		
		// Y
		t = "Y";
		loc = new Vector2(padding + pos.x, pos.y + (length + padding));
		c = f.newFontCache();
		d = c.setText(t, loc.x, loc.y);
		sz = new SizeF(d.width, d.height);

		f.setColor(Color.WHITE);

		this.Batch.begin();
		this.Font.draw(this.Batch, t, loc.x, loc.y + sz.getHeight(),
					   sz.getWidth(), 0, false);

		this.Batch.end();
	}

	// Note: if any scene rendering is done,
	//    it must be done before super.render is called.
	//    This way, the scene rendering is done first,
	//    then the objects are rendered. 
	public void Render(boolean update, boolean updatePost)
	{
		if (this.FXEnabled == false)
		{
			// BACKGROUND RENDER CALL
			this.BackgroundRender();
		}
		else
		{
			this.CapturePostFX();
		}
		
//		if (this.p.Particles.size() > 0 && this.p.Enabled == true)
//		{
//			this.p.Update();
//			this.p.Render();
//		}
		
		d = "Last Touched: {" + Lastt.x + ", " + Lastt.y + "}";
		this.DebugStrings.set(12, d);
		
		
		d = "Last Touched World: {" + WLast.x + ", " + WLast.y + "}";
		this.DebugStrings.set(13, d);
		
		d = "CurrentForce: " + s.CurrentForce;
		this.DebugStrings.set(14, d);
		
		d = "Momentum: " + s.Momentum;
		this.DebugStrings.set(15, d);
		
		super.Render(update, updatePost);
		
//		float sceneWidth = this.Size.getWidth();
//		float sceneHeight = this.Size.getHeight();

		//wave.Enabled = b.isPressed;

//		float freq = wave.frequency();
//		freq = 440 * ((Gdx.input.getAccelerometerX() + 20) / 10f);
//		wave.setFreq(freq);
		
		//this.CapturePostFX();
		
		float[] accel = new float[3];
		com.engine2d.utils.Utils.getAccelerometerReading(accel);
		
		if (cali == null)
		{
			cali = new float[3];
			cali[0] = -accel[0];
			cali[1] = -accel[1];
			cali[2] = -accel[2];
		}
		
		accel[0] += cali[0];
		accel[1] += cali[1];
		accel[2] += cali[2];
		
		this.ship.setRotation(Math.round(accel[2]) * 360);
		
		float trX = 250;
		float trY = 700;
		
		float si = 13;
		
		float x1 = (accel[0] * si) + trX, y1 = (accel[1] * si) + trY;
		float x2 = (accel[1] * si) + trX, y2 = (accel[2] * si) + trY;
		float x3 = (accel[2] * si) + trX, y3 = (accel[2] * si) + trY;
		
		x1 = (float)Math.round(x1 * 100.0) / 100.0f;
		x2 = (float)Math.round(x2 * 100.0) / 100.0f;
		x3 = (float)Math.round(x3 * 100.0) / 100.0f;
		
		y1 = (float)Math.round(y1 * 100.0) / 100.0f;
		y2 = (float)Math.round(y2 * 100.0) / 100.0f;
		y3 = (float)Math.round(y3 * 100.0) / 100.0f;
		
		
		
	
		if (this.gpsRecived == true)
		{
			this.ShapeRenderer.setColor(Color.GREEN);
		}
		else
		{
			this.ShapeRenderer.setColor(Color.RED);
		}
		this.ShapeRenderer.begin();
		//this.ShapeRenderer.line(0f, 24f, this.Size.getWidth(), 24f);

		
		
		this.ShapeRenderer.point(x1, y1, 0);
		this.ShapeRenderer.point(x2, y2, 0);
		this.ShapeRenderer.point(x3, y3, 0);
		
		
		this.ShapeRenderer.line(x1, y1, x2, y2);
		this.ShapeRenderer.line(x2, y2, x3, y3);
		this.ShapeRenderer.line(x1, y1, x3, y3);
		
		
		
		
//		if (Math.abs(accel[0]) >= 1f)
//		{
//			float x = ((this.Size.getWidth() / 2f) / (Math.abs(accel[0]) * 10));
//			this.ShapeRenderer.line(x, 100, x, 0f);
//			
//			ship.Rotate((accel[0]) + (accel[0] * 0.45f));
//		}
		

		this.ShapeRenderer.end();
		
		this.axis(this.Center);
		
		
		if (this.FXEnabled == true) this.RenderPostFX();
		this.RenderUI();
		
		//this.PostProcessor.render();
	
	//
//		if (b.isPressed)
//		{
//			ship.Rotate(7f);
//		}
//		
//		if (this.isPressed == true && pts.size() > 1)
//		{
//			float[] verts = arr();
//			
//			this.ShapeRenderer.begin();
//			
//			for (int i = 0; i < verts.length; i += 4)
//			{
//				if (i + 4 < verts.length)
//				{
//					Color c = new Color();
//					Color.argb8888ToColor(c, Color.argb8888(1f, verts[i], verts[i + 1], verts[i + 2]));
//					this.ShapeRenderer.setColor(c);
//					
//					this.ShapeRenderer.polyline(verts);//[i], verts[i + 1], verts[i + 2], verts[i + 3]);
//				}
//			}
//
//			this.ShapeRenderer.end();
//		}
//
//		if (gpsLoc != null)
//		{
//			bearingLbl.setText("GPS Bearing: " + gpsLoc.getBearing());
//			altLbl.setText("GPS Altitude: " + gpsLoc.getAltitude());
//			latLbl.setText("GPS Latitude: " + gpsLoc.getLatitude());
//			longLbl.setText("GPS Longitude: " + gpsLoc.getLongitude());
//			speedLbl.setText("GPS Speed: " + gpsLoc.getSpeed());
//			timeLbl.setText("GPS Time: " + gpsTime);
//			timeDiffLbl.setText("GPS Interval: " + gpsTimeDif);
//			arrcLbl.setText("GPS Accuracy: " + gpsLoc.getAccuracy());
		// }

		if (mshDoSlide == true)
		{
			if (msh.Location.x % 20 != 0) msh.Location.Set(new Vector2(msh.Location.x - 1, 0));
			else
			{
				msh.Location.Set(new Vector2(msh.Location.x - 20, 0));
			}

			if (msh.Location.Get().x == 0)
			{
				//msh.Location.set(new Vector2(msh.Location.x, msh.Location.y));
				mshDoSlide = false;
				msh.sliding = false;
				this.IsActive = false;
				this.Dispose();
			}
		}
	}

	@Override
	public void Dispose()
	{
		//generator.clearAudioProcessors();
		this.Controls.clear();
		this.clear();
		super.Dispose();

	}

	private void clear()
	{
		this.Objects.Clear(true);
	}
}
