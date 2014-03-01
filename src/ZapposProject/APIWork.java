
package ZapposProject;

import java.io.*;
import java.net.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class APIWork {
	public static final String BASEURL = "http://api.zappos.com/Search?key=a73121520492f88dc3d33daf2103d7574f1a3166";


	public static String httpGet(String urlStr) throws IOException 
	{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) 
		{
		    throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) 
		{
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}

	public static JSONObject parseReply(String reply) throws ParseException
	{
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(reply);
		JSONObject object = (JSONObject)obj;
		return object;
	}

	public static JSONArray getResults(JSONObject reply)
	{
		Object resultObject = reply.get("results");
		JSONArray resultArray = (JSONArray)resultObject;
		return resultArray;
	}
}