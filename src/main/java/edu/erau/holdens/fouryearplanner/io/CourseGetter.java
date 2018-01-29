package edu.erau.holdens.fouryearplanner.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;


/** A {@link java.lang.Runnable Runnable} that attempts to retrieve the information for a course with a specified ID, 
 * and adds it to the {@link edu.erau.holdens.fouryearplanner.model.MasterCourseMap MasterCourseMap}.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class CourseGetter implements Runnable{
	
	/** The catalog server */
	public static final String HOSTNAME = "http://catalog.erau.edu";
	/** I'm not sure what ribbit is exactly, but this is the page on the server */
	public static final String RIBBIT_URL = "/ribbit/index.cgi";
	/** Endpoint for requesting course info from ribbit */
	public static final String ENDPOINT = "?page=getcourse.rjs&code=";
	/** The fully-composed course data endpoint, i.e. {@link #HOSTNAME} + {@link #RIBBIT_URL} + {@link #ENDPOINT}. */
	public static final String FULL_ENDPOINT = HOSTNAME + RIBBIT_URL + ENDPOINT;
	
	/** The ID of the course to fetch */
	private String id;

	
	/** Creates a new {@link CourseGetter} object.
	 * @param id The ID of the course to get
	 */
	public CourseGetter(String id){
		this.id = id;
	}

	@Override
	public void run() {
		try {
			// See if it already exists in the master map
			if (MasterCourseMap.getInstance().containsKey(id)){
				System.out.println(id + " already exists in the map; skipping");
			}
			else{
				// Add it to the map
				System.out.println("Getting " + id);
				Course c = getCourse(id);
				MasterCourseMap.getInstance().put(c);
			}
		} catch (Exception e) {
			System.err.println("Get/put of " + id + " failed!");
		}
	}
	
	// TODO make the static invocation create a new thread for this?
	/** Attempts to retrieve information for a course from the ERAU catalog server
	 * and parse it into a Course object.
	 * @param id The course ID to get (e.g. "CS 225DB")
	 * @return The Course object representative of the given ID
	 * @throws IOException If an IO error occurs while contacting the server
	 */
	public static Course getCourse(String id) throws IOException{
		// Clean the input by replacing all whitespace characters with a +
		String cleanId = id.replaceAll("\\s", "+");
		
		// Get the request page
		String webData = Utils.getURL(FULL_ENDPOINT + cleanId);
		
		// Parse info
//		String[] data1 = webData.split();
		
		// Get course title info
		String[] stuff = Utils.parse(webData, "<p class=\"courseblocktitle\"><strong>(.+?)</strong></p>").get(0).split("&#160;&#160;");
		
		
		// Get course description
		String stuff2 = Utils.parse(webData, "<p class=\"courseblockdesc\">(.+?)(</p></div>|<br />)").get(0);
		
		
		// Get pre-reqs
		List<String> prereqs = new ArrayList<String>();
		Set<String> set = new HashSet<String>(Utils.parse(webData, "this, '(.+?)'"));
		
		
		for (String prereqid : set){
			prereqs.add(prereqid);
		}
		
		// Create the course object
		Course c = new Course(id.replaceAll("DB", ""), prereqs, stuff[1], "", Integer.parseInt(stuff[2].substring(0, 1)), stuff2);
		
		// CDATA is in ISO Latin-1 format
		
//		System.out.println(webData);
		
		return c;
	}

}