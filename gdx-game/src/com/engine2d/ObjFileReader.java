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
import java.net.*;
//END IMPORTS

public class ObjFileReader
{
	public static String FileExtension = ".obj";
	
	private static String[] LoadObjLines(String p)
	{
		File meshFile = new File(EngineFiles.Instance.Meshes, p + ObjFileReader.FileExtension);
		FileInputStream meshFileRead;

		byte[] rawMeshData = null;
		String fileData = null;

		try
		{
			// List<String> str = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(URI.create(meshFile.getAbsolutePath())));
			meshFileRead = new FileInputStream(meshFile);
			int ava = meshFileRead.available();
			rawMeshData = new byte[ava];
			meshFileRead.read(rawMeshData, 0, rawMeshData.length);
			meshFileRead.close();

			fileData = new String(rawMeshData);
		}
		catch (Exception ex)
		{}

		return fileData.split(Character.valueOf('\n').toString());
	}
	
	private static String getName(String[] data)
	{
		int i = 0;
		while (true)
		{
			if (data[i].startsWith("# object") == true)
			{
				break;
			}
			i++;
		}

		return data[i].replace("# object", "").trim();
	}
	
	private static int getVertexCount(String[] data)
	{
		int i = 0;
		while (true)
		{
			if (data[i].startsWith("#") == true && data[i].contains(" vertices") == true)
			{
				break;
			}
			i++;
		}

		String strVal = data[i].replace("# ", "").replace(" vertices", "");
		return Integer.valueOf(strVal);
	}
	
	public static Mesh get2dMeshFromObj(String meshFileName, GameObject prnt)
	{
		String[] data = ObjFileReader.LoadObjLines(meshFileName);
		String name = getName(data);
		int vCount = getVertexCount(data);
		float[] verts = getVertices_XZ(data, vCount);
		
		Vector2 origin = Vector2.Zero;
		origin = GeometryUtils.polygonCentroid(verts, 0, verts.length, origin);

		return new Mesh(prnt, verts, origin);
	}
	
	public static Mesh get3dMeshFromObj(String meshFileName, GameObject prnt)
	{
		String[] data = ObjFileReader.LoadObjLines(meshFileName);
		String name = getName(data);
		int vCount = getVertexCount(data);
		float[] verts = getVertices_XZY(data, vCount);
		return new Mesh(prnt, verts);
	}
	
	private static float[] getVertices_XZ(String[] data, int v)
	{
		return getVertices(data, new int[] { 0, 2 }, v);
	}
	
	private static float[] getVertices_XY(String[] data, int v)
	{
		return getVertices(data, new int[] { 0, 1 }, v);
	}
	
	private static float[] getVertices_XYZ(String[] data, int v)
	{
		return getVertices(data, new int[] { 0, 1, 2 }, v);
	}
	
	private static float[] getVertices_XZY(String[] data, int v)
	{
		return getVertices(data, new int[] { 0, 2, 1 }, v);
	}
	
	private static float[] getVertices(String[] data, int[] componentsToAdd, int v)
	{
		ArrayList<Float> arr = new ArrayList<Float>();
		
		int i = 0, count = 0;
		while (true)
		{
//			if (i > 1)
//			{
//				if (data[i].startsWith("# ") == true && data[i - 1].startsWith("v ") == true)
//				{
//					break;
//				}
//			}
			
			if (count == (v - 1))
			{
				break;
			}
			
			if (data[i].startsWith("v ") == true)
			{
				String[] parts = data[i].replace("v ", "").split(" ");
				
				for (int c = 0; c < componentsToAdd.length; c++)
				{
					arr.add(Float.valueOf(parts[componentsToAdd[c]]) * 3f);
				}
				
				count++;
			}
			i++;
		}
		
		float[] Result = new float[arr.size()];
		
		for (i = 0; i < Result.length; i++)
		{
			Result[i] = arr.get(i);
		}
		return Result;
	}
}
