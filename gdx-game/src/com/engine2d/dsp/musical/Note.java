package com.engine2d.dsp.musical;

import com.engine2d.dsp.AudioProcessor;

public class Note
{
	public float Frequency;
	public int Octave;
	public String Name;
	public int Duration; // unit is miliseconds
	
	// For later DSP usage
	private AudioProcessor NoteGenerator;
	
	// takes a string of (note letter), and int of notes octave
	public Note(String name, int octave, AudioProcessor p)
	{
		this.Name = name;
		this.Octave = octave;
		this.Frequency = Utils.noteToHz(this.Name + this.Octave);
		this.Duration = 1000;
		this.NoteGenerator = p;
	}
	
	@Override
	public String toString()
	{
		return this.Name + this.Octave;
	}
}
