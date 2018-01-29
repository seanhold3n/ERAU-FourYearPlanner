package edu.erau.holdens.fouryearplanner.sandbox;

import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;

/** Select a course and see what classes are required to get to it
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class CourseExplorer {

	public static void main(String[] args) throws Exception {




		// Populate the master course list
		// Should be taken care of already in CatalogCrawler
//		CourseListParser parser = new CourseListParser();
//		CourseList courses = parser.parse("courses/courses.xml");
//
//		for (Course c : courses){			
//			MasterCourseMap.getInstance().put(c);
//		}
		
		// Get the course
//		Course mainCourse = MasterCourseMap.getInstance().get("CS 490");
//		printPreReqs(mainCourse.getId());
		
		Course cec320 = MasterCourseMap.getInstance().get("CEC 320");
		System.out.println(cec320.getName() + " - " + cec320.getDescription());

	}
	
	public static void printPreReqs(String s){
		// Get the course
		Course c = MasterCourseMap.getInstance().get(s);
		
		// Print pre-reqs		
		for (String prereq : c.getPreReqList()){
			System.out.println(c.getPreReqList().toString());
			printPreReqs(prereq);
		}
	}

}
