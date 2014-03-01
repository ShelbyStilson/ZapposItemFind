import java.io.*;
import java.net.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class APIWork 
{

	public static String webstring(String passedURL) throws IOException 
	{
		URL url = new URL(passedURL);
		HttpURLConnection connectionURL = (HttpURLConnection) url.openConnection();

		if (connectionURL.getResponseCode() != 200) 
		{
		    throw new IOException(connectionURL.getResponseMessage());
		}

		BufferedReader buff = new BufferedReader(new InputStreamReader(connectionURL.getInputStream()));
		StringBuilder made = new StringBuilder();
		
		String Line;
		while ((Line = buff.readLine()) != null) 
		{
			made.append(Line);
		}
		buff.close();

		connectionURL.disconnect();
		return made.toString();
	}

	public static JSONArray theresults(JSONObject passedstring)
	{
		Object objresult = passedstring.get("results");
		JSONArray arrayjson = (JSONArray)objresult;
		return arrayjson;
	}

	public static JSONObject parsecall(String passedstring) throws ParseException
	{
		JSONParser parsed = new JSONParser();
		Object obj = parsed.parse(passedstring);
		JSONObject objectjson = (JSONObject)obj;
		return objectjson;
	}

}