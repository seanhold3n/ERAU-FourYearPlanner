package edu.erau.holdens.fouryearplanner.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/** Maps all Course IDs (e.g. "CS 225") to a {@link Course} object
 * @author Sean Holden (holdens@my.erau.edu)
 */
public final class MasterCourseMap extends HashMap<String, Course> {

	private static final long serialVersionUID = 469936432262574885L;
	
	/** The file from which to perform I/O operations.  Current value is {@value #FILENAME}. */
	private static final String FILENAME = "src/main/resources/courses/coursemap.dat";

	/** Someone call Chesney Hawkes, because <i>I am the one and only! <sup>Nobody I'd rather be...</sup></i> */
	private static MasterCourseMap map;
	
	// Make it uninstantiable beyond this class
	private MasterCourseMap(){
		// Initialize the capacity to 100, because why not?
		super(100);
	}
	
	/** Get the MasterCourseMap object for this class.  Because this is the <i>master</i> map, all
	 * program resources should be referencing the singular map object through this static method;
	 * that is, having multiple master maps defeats the purpose of this design choice.
	 * @return The MasterCourseMap object
	 */
	public static synchronized MasterCourseMap getInstance(){
		// Yay, lazy object creation!
		if (map == null){
			try {
				populateFromFile();
			}
			catch (Exception e){
				// File IO error - create new empty map object
				map = new MasterCourseMap();
			}
		}
		return map;
	}
	
	/** Populates the MasterCourseMap contained within this class from a file.  The name of the file is specified by the {@link #FILENAME} field ({@value #FILENAME}).
	 * @throws IOException If something goes terribly wrong
	 * @throws FileNotFoundException If the file doesn't exist
	 * @throws ClassNotFoundException If, for some dumb reason, the JVM can't find this exact class
	 * @see #writeToFile()
	 */
	private static void populateFromFile() throws FileNotFoundException, IOException, ClassNotFoundException  {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(FILENAME)));
		map = (MasterCourseMap) ois.readObject();
		ois.close();
		System.out.println("[MasterCourseMap] Course map populated from file!");
	}
	
	// TODO which is more efficient: put, or putIfAbsent?
	
	/** Add a course to the master map
	 * @param c The Course to add
	 */
	public void put(Course c){
		put(c.getId(), c);
	}
	
	/** Add all of the courses in a given list to the master map
	 * @param list The list containing the courses to add
	 */
	public void put(CourseList list){
		for (Course c : list){
			put(c);
		}
	}

	/** Add a course to the master map if they do not already exist
	 * @param c The Course to add
	 */
	public void putIfAbsent(Course c){
		putIfAbsent(c.getId(), c);
	}

	/** Add all of the courses in a given list to the master map if they do not already exist
	 * @param list The list containing the courses to add
	 */
	public void putIfAbsent(CourseList list){
		for (Course c : list){
			putIfAbsent(c);
		}
	}
	
	/** Writes the MasterCourseMap contained within this class to a file.  The name of the file is specified by the {@link #FILENAME} field ({@value #FILENAME}).
	 * @see #populateFromFile()
	 * @throws FileNotFoundException If the file doesn't exist for some reason
	 * @throws IOException Any exception thrown by the underlying OutputStream
	 */
	public static void writeToFile() throws FileNotFoundException, IOException{
		// Write the coursemap to a file
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILENAME)));
		oos.writeObject(MasterCourseMap.getInstance());
		oos.close();
	}
	
}
