package com.engine2d.dsp.musical;

import java.lang.*;
import java.util.*;
import com.badlogic.gdx.math.WindowedMean;

public class Utils
{
	public static float noteToHz(Note n)
	{
		ArrayList<String> notes = new ArrayList<String>();

		notes.add("A");
		notes.add("A#");
		notes.add("B");
		notes.add("C");
		notes.add("C#");
		notes.add("D");
		notes.add("D#");
		notes.add("E");
		notes.add("F");
		notes.add("F#");
		notes.add("G");
		notes.add("G#");

		int keyNumber = notes.indexOf(n.Name);


		if (keyNumber < 3)
		{
			keyNumber = keyNumber + 12 + ((n.Octave - 1) * 12) + 1; 
		}
		else
		{
			keyNumber = keyNumber + ((n.Octave - 1) * 12) + 1; 
		}

		// Return frequency of note
		return 440f * (float)Math.pow(2, (keyNumber - 69) / 12);

		//return noteToHz(n.toString());
	}

	public static float noteToHz(String note)
	{
		ArrayList<String> notes = new ArrayList<String>();

		notes.add("A");
		notes.add("A#");
		notes.add("B");
		notes.add("C");
		notes.add("C#");
		notes.add("D");
		notes.add("D#");
		notes.add("E");
		notes.add("F");
		notes.add("F#");
		notes.add("G");
		notes.add("G#");

		int octave = 0;
		int keyNumber = 0;

		if (note.length() == 3)
		{
			octave = Integer.parseInt(Character.toString(note.charAt(2)));
			keyNumber = notes.indexOf(note.substring(0, note.length() - 1));
		}
		else
		{
			octave = Integer.parseInt(Character.toString(note.charAt(1)));
			keyNumber = notes.indexOf(Character.toString(note.charAt(0)));

		}



		if (keyNumber < 3)
		{
			keyNumber = keyNumber + 12 + ((octave - 1) * 12) + 1; 
		}
		else
		{
			keyNumber = keyNumber + ((octave - 1) * 12) + 1; 
		}

		// Return frequency of note
		return 440f * (float)Math.pow(2, (keyNumber - 69) / 12);

	}


}
