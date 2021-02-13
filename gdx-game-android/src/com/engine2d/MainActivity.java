package com.engine2d;

import android.os.Bundle;
import android.Manifest;
import android.Manifest.permission.*;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.app.*;

import java.util.*;
import java.io.*;

public class MainActivity extends AndroidApplication
{
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		// TODO: Implement this method
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		// Grant all permissions
		grantResults[0] = 1;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		
		this.initialize(new Game(this.getApplicationContext(), this.getClass().getPackage().getName()), cfg);
	}
}
