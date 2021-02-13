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

public class MeshFileReader
{
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
	 10. mesh Origin [x, data seperator, y]
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

	public static Mesh getMeshFromFile(String meshFileName, GameObject prnt)
	{
		Mesh Result = new Mesh(prnt);
		File meshFile = new File(EngineFiles.Instance.Meshes, meshFileName + MeshFile.FileExtension);
		FileInputStream meshFileRead;

		byte[] rawMeshData = null;
		String fileData = null;
		char[] rawMeshCharData = null;

		try
		{
			// List<String> str = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(URI.create(meshFile.getAbsolutePath())));
			meshFileRead = new FileInputStream(meshFile);
			int ava = meshFileRead.available();
			rawMeshData = new byte[ava];
			meshFileRead.read(rawMeshData, 0, rawMeshData.length);
			meshFileRead.close();

			fileData = new String(rawMeshData);
			rawMeshCharData = fileData.toCharArray();
		}
		catch (Exception ex)
		{}

		if (rawMeshData != null)
		{
			int charIdx = MeshFile.STR_HeaderSignature.length();

			// Check if this is a valid MshX file using the header signature
			// and if theres a DATA_SEPERATOR after the Header sig
			if (fileData.startsWith(MeshFile.STR_HeaderSignature) == true && fileData.charAt(charIdx) == '|')
			{
				charIdx += 1;
				String meshNameIndicator = fileData.substring(charIdx, charIdx + MeshFile.STR_STRING_DATA_INDICATOR.length());

				charIdx += MeshFile.STR_STRING_DATA_INDICATOR.length();

				if (meshNameIndicator.equals(MeshFile.STR_STRING_DATA_INDICATOR) == true)
				{
					Object[] nameLenStr = pullData(rawMeshCharData, charIdx);
					String nLen = (String)nameLenStr[0];
					charIdx = nameLenStr[1];

					String meshName = fileData.substring(charIdx, charIdx + Integer.parseInt(nLen));
					Result.Name = meshName;

					charIdx += Integer.parseInt(nLen);
					if (rawMeshCharData[charIdx] == MeshFile.STRING_TERMINATE[0] && rawMeshCharData[charIdx + 1] == MeshFile.STRING_TERMINATE[1])
					{
						charIdx += MeshFile.STRING_TERMINATE.length;
					}
				}
				else if (meshNameIndicator == MeshFile.STR_NULL_STRING_INDICATOR)
				{
					charIdx += MeshFile.NULL_STRING_INDICATOR.length;
				}

				int lineCount = -1, totalDataPayload = -1, meshHash = -1;
				float x = 0f, y = 0f;
				
				// Line count
				Object[] res = pullData(rawMeshCharData, charIdx);
				charIdx = (int) res[1];
				lineCount = Integer.parseInt((String)res[0]);
				
				// Total Data Payload
				res = pullData(rawMeshCharData, charIdx);
				charIdx = (int)res[1];
				totalDataPayload = Integer.parseInt((String)res[0]);
			
				// Mesh Hash
				res = pullData(rawMeshCharData, charIdx);
				charIdx = (int)res[1];
				meshHash = Integer.parseInt((String)res[0]);

				// Origin X
				res = pullData(rawMeshCharData, charIdx);
				charIdx = (int)res[1];
				x = NumberUtils.intBitsToFloat(Integer.parseInt((String)res[0]));
				
				// Origin Y
				String cut = "";
				while (true)
				{
					if (rawMeshCharData[charIdx] == MeshFile.HEADER_END[0] && rawMeshCharData[charIdx + 1] == MeshFile.HEADER_END[1])
					{
						break;
					}
					
					cut += rawMeshCharData[charIdx];
					charIdx += 1;
				}
				
				
				y = NumberUtils.intBitsToFloat(Integer.parseInt(cut));
				
				Result.Origin = new Vector2(x, y);
				charIdx += MeshFile.HEADER_END.length;

				// Data Read
				ArrayList<Integer> intsData = new ArrayList<Integer>();
				while (rawMeshCharData[charIdx] != MeshFile.DATA_PAYLOAD_TERMINATE[0] || rawMeshCharData[charIdx + 1] != MeshFile.DATA_PAYLOAD_TERMINATE[1])
				{
					String intStr = "";
					while (rawMeshCharData[charIdx] != MeshFile.DATA_SEPERATOR)
					{
						intStr += rawMeshCharData[charIdx];
						charIdx += 1;
					}
					intsData.add(Integer.parseInt(intStr));
					charIdx += 1;
				}

				Integer[] data = new Integer[totalDataPayload];
				data = intsData.toArray(data);
				int idx = 0;
				for (int i = 0; i < lineCount; i++)
				{
					Line l = new Line();

					float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
					x1 = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					x2 = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					y1 = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					y2 = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					float r = 0, g = 0, b = 0, a = 0;

					r = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					g = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					b = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					a = NumberUtils.intBitsToFloat(data[idx]);
					idx += 1;

					Color lineCol = new Color();
					l = new Line(x1, y1, x2, y2);

					Color.argb8888ToColor(lineCol, Color.argb8888(a, r, g, b));

					l.Color = lineCol;

					Result.Add(l);
				}
			}
			//Result.updateOrigin();
			return Result;
		}

		return null;
	}

	private static Object[] pullData(char[] data, int startIdx)
	{
		String Result = "";
		while (data[startIdx] != MeshFile.DATA_SEPERATOR)
		{
			Result += data[startIdx];
			startIdx += 1;
		}
		startIdx += 1;
		return new Object[] { Result, startIdx };
	}
}
