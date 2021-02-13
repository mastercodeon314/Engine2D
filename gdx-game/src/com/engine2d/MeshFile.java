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
import org.apache.commons.codec.binary.*;
import android.util.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
//END IMPORTS

public class MeshFile
{
	public Mesh Mesh;
	public static String STR_DATA_PAYLOAD_TERMINATE;
	public static String STR_HEADER_END;
	public static String STR_DATA_SEPERATOR = "|";
	public static String STR_STRING_DATA_INDICATOR;
	public static String STR_STRING_TERMINATE;
	public static String STR_NULL_STRING_INDICATOR;
	public static String STR_HeaderSignature;
	
	public static final char[] DATA_PAYLOAD_TERMINATE = new char[]
	{
		42,
		41
	};
	
	public static final char[] HEADER_END = new char[]
	{
		42,
		42
	};
	public static final char DATA_SEPERATOR = '|';
	public static final char[] STRING_DATA_INDICATOR = new char[]
	{
		119,
		125
	};
	
	public static final char[] STRING_TERMINATE = new char[]
	{
		115,
		125
	};
	
	public static final char[] NULL_STRING_INDICATOR = new char[]
	{
		125,
		115
	};
	
	public static byte[] HeaderSignature = new byte[]
	{
		26,
		89,
		123,
		1,
		42,
		38,
		127,
		4
	};
	
	static
	{
		java.lang.StringBuilder m = new java.lang.StringBuilder();

		m.append(DATA_PAYLOAD_TERMINATE);
		STR_DATA_PAYLOAD_TERMINATE = String.copyValueOf(DATA_PAYLOAD_TERMINATE);
		m = new java.lang.StringBuilder();
		
		m.append(HEADER_END);
		STR_HEADER_END = String.copyValueOf(HEADER_END);
		m = new java.lang.StringBuilder();
		
		m.append(DATA_SEPERATOR);
		STR_DATA_SEPERATOR = String.valueOf(DATA_SEPERATOR);
		m = new java.lang.StringBuilder();
		
		m.append(STRING_DATA_INDICATOR);
		STR_STRING_DATA_INDICATOR = String.copyValueOf(STRING_DATA_INDICATOR);
		m = new java.lang.StringBuilder();
		
		m.append(STRING_TERMINATE);
		STR_STRING_TERMINATE = String.copyValueOf(STRING_TERMINATE);
		m = new java.lang.StringBuilder();
		
		m.append(NULL_STRING_INDICATOR);
		STR_NULL_STRING_INDICATOR = String.copyValueOf(NULL_STRING_INDICATOR);
		m = new java.lang.StringBuilder();
		
		m.append(HeaderSignature);
		STR_HeaderSignature = m.toString();
	}
	
	public static String FileExtension = ".MshX";
	
	private int lineCount;
	private int totalDataPayload;
	
	public java.lang.StringBuilder fileData;
	
	/*  FILE FORMAT OUTLINE

	 !!!KEEP   THIS  FORMAT!!!
	 !!!CARFULLY DOCUMENTED!!!

	 PRE FILE READ:
	 1. 

	 1. Header Sig
	 2. data seperator
	 3. Name string
	 	STRING FORMAT for non null string
	 	A. String indicator chars [250, 242]
	 	B. String Length
	 	C. Data seperator
	 	D. String data...
	 	E. String terminate

	 	STRING FORMAT for null strings
	 	A. Null string indicator chars [242, 242]

	 4. mesh line count
	 5. data seperator
	 6. mesh total data payload count
	 7. data seperator
	 8. mesh hash
	 9. data seperator
	 10. mesh Origin [x, y]
	 11. Header end
	 12. Data payload
	 	DATA FORMAT
	 	A. Data part
	 	B. DATA_SEPERATOR...
	 	...
	 	C. Data payload terminate

	 13. tail?

	 NOTES: This format is liable 
	 to change based on how
	 the reader is written, and
	 what needs to be stored.

	 I'm guessing this format
	 will be updated rapidly
	 since the drawing system
	 is evolving and new
	 properties for objects
	 are being added.



	 !!!KEEP   THIS  FORMAT!!!
	 !!!CARFULLY DOCUMENTED!!!
	 */
	
	public MeshFile(Mesh mesh)
	{
		this.Mesh = mesh;
		fileData = new java.lang.StringBuilder();
	
		lineCount = this.Mesh.Lines.size();
		// Payload data total = lineCount * 8 floats per each line [2 for start x, y, 2 for end x, y], 4 for color
		totalDataPayload = lineCount * 8;
	}
	
	public void Build()
	{
		Data(Header());
		//Tail();
		
		// TODO: Implement file writer
	}
	
	private String[][] Header()
	{
		String[][] stringRawData = new String[this.Mesh.Lines.size()][];

		// Get Mesh raw data
		for (int i = 0; i < lineCount; i++)
		{
			int[] data = ((Line)this.Mesh.Lines.get(i)).ToRawData();
			stringRawData[i] = new String[data.length];
			for (int c = 0; c < data.length; c++)
			{
				stringRawData[i][c] = Integer.toString(data[c]);
			}
		}

		int meshHash = stringRawData.hashCode();
		
		fileData.append(MeshFile.STR_HeaderSignature);
		fileData.append(STR_DATA_SEPERATOR);
		this.compileString(this.Mesh.Name);
		
		fileData.append(lineCount);
		fileData.append(STR_DATA_SEPERATOR);
		fileData.append(totalDataPayload);
		fileData.append(STR_DATA_SEPERATOR);
		fileData.append(meshHash);
		fileData.append(STR_DATA_SEPERATOR);
		
		// Origin x
		int bits = NumberUtils.floatToIntBits(this.Mesh.Origin.x);
		fileData.append(Integer.toString(bits));
		fileData.append(STR_DATA_SEPERATOR);
		
		// Origin y
		bits = NumberUtils.floatToIntBits(this.Mesh.Origin.y);
		float y = NumberUtils.intBitsToFloat(bits);
		fileData.append(Integer.toString(bits));
		
		fileData.append(MeshFile.STR_HEADER_END);
		
		return stringRawData;
	}
	
	private void Data(String[][] stringRawData)
	{
		for (int i = 0; i < stringRawData.length; i++)
		{
			for (int c = 0; c < stringRawData[i].length; c++)
			{
				fileData.append(stringRawData[i][c]);
				
				if (i <= stringRawData.length - 1 && c <= stringRawData[i].length - 1) fileData.append(STR_DATA_SEPERATOR);
			}
		}
		
		fileData.append(STR_DATA_PAYLOAD_TERMINATE);
	}
	
	private void compileString(String stringToCompile)
	{
		if (stringToCompile != "")
		{
			fileData.append(MeshFile.STR_STRING_DATA_INDICATOR);
		
			fileData.append(stringToCompile.length());
		
			fileData.append(MeshFile.STR_DATA_SEPERATOR);
		
			fileData.append(stringToCompile);
		
			fileData.append(MeshFile.STR_STRING_TERMINATE);
		}
		else
		{
			fileData.append(MeshFile.STR_NULL_STRING_INDICATOR);
		}
	}
	
//	private void Tail()
//	{
//		
//	}
	
	public void Save(String fileName)
	{
		File save = new File(EngineFiles.Instance.Meshes, fileName + MeshFile.FileExtension);
		FileOutputStream outputStream;
		
		try
		{
			//save.mkdirs(); // makes dirs and all child dirs in the path
			save.createNewFile(); // Creates the file if it dosent exist
			outputStream = new FileOutputStream(save);
			outputStream.write(fileData.toString().getBytes());
			outputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();	
		}
		
		
		/* Read function
		 int length = (int) file.length();

		 byte[] bytes = new byte[length];

		 FileInputStream in = new FileInputStream(file);
		 try {
		 in.read(bytes);
		 } finally {
		 in.close();
		 }

		 String contents = new String(bytes);
		*/
	}
}
