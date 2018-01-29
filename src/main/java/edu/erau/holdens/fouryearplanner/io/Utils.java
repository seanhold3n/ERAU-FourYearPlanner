package edu.erau.holdens.fouryearplanner.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

	// TODO protocol is unused
	private static String getURL(String urlToRead, String protocol) throws IOException {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";

		
		url = new URL(urlToRead);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while ((line = rd.readLine()) != null) {
			result += line;
		}
		rd.close();

		return result;
	}

	public static String getURL(String urlToRead) throws IOException{
		return getURL(urlToRead, "");
	}
	
	public static List<String> parse(String data, String regex){
		List<String> parsedList = new ArrayList<String>();
		Pattern patThreads2 = Pattern.compile(regex);
		Matcher match2 = patThreads2.matcher(data);
		while (match2.find()){
			// Add each match to the list
			try{
				parsedList.add(match2.group(1));
			} catch (IndexOutOfBoundsException ioobe){
				parsedList.add(match2.group(0));
			}
		}
		return parsedList;
	}
	
}
