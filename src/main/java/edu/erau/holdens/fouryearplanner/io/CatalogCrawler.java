package edu.erau.holdens.fouryearplanner.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringEscapeUtils;

import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;

/** Contains methods for crawling the ERAU course catalog website for course IDs.  This is separate from the {@link CourseGetter} class, 
 * which gets the information given a course ID.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class CatalogCrawler {

	/** The server from which to crawl the pages */
	public static final String HOSTNAME = "http://catalog.erau.edu";
	/** The base location on the server for all of the programs*/
	public static final String MIDPOINT = "/daytona-beach/undergraduate-courses/";
	/** The file that stores all of the prefixes for the endpoints.  Foe example, if "cs" was in this list, one of the 
	 * URLs that would be crawled is {@value #HOSTNAME} + {@value #MIDPOINT} + "cs/" 
	 * @see #crawlAllPrograms()*/
	public static final String PROGRAMS_FILENAME = "courses/program-prefixes.csv";
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		crawlAllPrograms();
		
		System.out.println(MasterCourseMap.getInstance().get("AS 120").getDescription());
		if(!MasterCourseMap.getInstance().containsKey("CS 315")){
			System.err.println("CS 315 is not a key");
		}
//		System.out.println(MasterCourseMap.getInstance().get("CS 315").getDescription());

		MasterCourseMap.writeToFile();

	}
	
	/** This method builds all URLs of the form ({@link #HOSTNAME} + {@link #MIDPOINT} + (<i>program code</i>) + /), 
	 * where <i>program code</i> is a value specified in {@value #PROGRAMS_FILENAME}.
	 * @see #PROGRAMS_FILENAME
	 * @return The list of generated URLs
	 * @throws IOException If there is a File IO error with {@value #PROGRAMS_FILENAME}
	 */
	public static List<String> buildURLs() throws IOException {
		List<String> urls = new ArrayList<>();
		
		// Open a file reader for the csv
		BufferedReader bf = new BufferedReader(new FileReader(new File(PROGRAMS_FILENAME)));
		
		String line = "";
		String urlFrag = "";
		
		while ((line = bf.readLine()) != null){
			// Extract the url fragment
			urlFrag = line.substring(0, line.indexOf(","));
			// Build the full URL
			urls.add(HOSTNAME + MIDPOINT + urlFrag + "/");			
		}
		
		bf.close();
		
		return urls;
	}

	/** Crawls the pages of all degree programs for course information.  Specifically, it crawls all URLs of the list 
	 * returned from {@link #buildURLs()}.
	 * @throws IOException If something bad happens
	 * @throws InterruptedException If something bad happens
	 */
	public static void crawlAllPrograms() throws IOException, InterruptedException {
		
		List<String> urls = buildURLs();
		
		for (String s : urls){
			crawl(s);
		}
		
//		crawl(urls.get(12)); //CS
	}
	
	
	/** Crawls the given URL for Course IDs and adds them to the MasterCourseMap.
	 * <a href="http://google.com">test</a>
	 * @param url The url to crawl
	 * @throws IOException If something bad happens
	 * @throws InterruptedException If something bad happens
	 */
	public static void crawl(String url) throws IOException, InterruptedException{
		// Get the thing for a catalog
		String data = Utils.getURL(url);

		// Decode some of the HTML stuff
		data = StringEscapeUtils.unescapeHtml4(data);

		// Bitshift some of the spaces (seriously)
		// Convert string to byte array
		byte[] string = data.getBytes();
		final byte COPIED_BYTE = " ".getBytes()[0];
		for (int i = 0; i < string.length; i++) {
			if (string[i] == COPIED_BYTE){
				string[i] <<= 1;
				string[i] >>= 1;
			}
		}
		data = new String(string);
		
		System.out.println(data);
		
//		System.out.println(Arrays.toString(data.split("[A-Za-z]{1,3}")));

		// Find all course numbers - 2-4 letters (repeated 1-3), space, 3 numbers, 0-1 letters
		List<String> idsList = Utils.parse(data, "[A-Z]{1,4}\\s\\d{3}");//[A-Za-z]{0,1}");
		Set<String> ids = new HashSet<String>(idsList);

		// Print them
//		System.out.println(ids);

		System.out.println("Commensing uncalled-for barrage of requests to " + HOSTNAME + "...");
		ExecutorService exe = Executors.newFixedThreadPool(100);

		for (String id : ids){
			exe.execute(new CourseGetter(id));
		}

		exe.shutdown();
		exe.awaitTermination(15, TimeUnit.SECONDS);

		System.out.println("We made it!");

		// Print everything in the map
//		for (Course c : MasterCourseMap.getInstance().values()){
//			System.out.println(c.getId() + " - " + c.getName());
//		}

//		System.out.println("Done printing");
//		System.exit(0);
	}

}
