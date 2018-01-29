package edu.erau.holdens.fouryearplanner.sandbox;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;


/** The exclusive purpose of this class is to correct errors with courses, i.e. SE 300 having 3 credit hours instead of 4.
 * @author Sean Holden (holdens@my.erau.edu)
 *
 */
public class ChangeCredits {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Course se300old = MasterCourseMap.getInstance().get("SE 300");
		Course se300new = new Course(se300old.getId(), se300old.getPreReqList(), se300old.getName(), se300old.getSemesterOffered(), 4, se300old.getDescription());
		MasterCourseMap.getInstance().put(se300new);
		MasterCourseMap.writeToFile();
	}
	
}
