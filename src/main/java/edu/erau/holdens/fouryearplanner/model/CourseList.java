package edu.erau.holdens.fouryearplanner.model;

import java.util.ArrayList;
import java.util.List;


/** A list of {@link Course} objects.  This class is a wrapper for {@link ArrayList}{@code <Course>} and {@link List}{@code <Course>}.
 * Additionally, it provides operations specific to a list of courses, such as
 * {@linkplain #getCourseByID(String) getting a course on the list given the course ID} and
 * {@linkplain #getTotalCredits() getting the total number of credits}.
 * @author Adriana Strelow, Sean Holden<br>
 * {strelowa, holdens}@my.erau.edu
 */
public class CourseList extends ArrayList<Course> implements List<Course> {

	private static final long serialVersionUID = 6015027410732682765L;

	/** Returns a {@link Course} for the given course ID, or {@code null} if no such course is found.
	 * @param id The course ID (e.g. "CS 225")
	 * @return The course
	 */
	public Course getCourseByID(String id){
		for (Course c : this){
			if (c.getId().equals(id)){
				return c;
			}
		}
		return null;
	}
	
	
	/** Get the total number of credit hours for all courses in this list.
	 * @return The number of credits of all courses in the list.
	 */
	public int getTotalCredits(){
		int credits = 0;
		for(Course c : this){
			credits += c.getCredits();
		}
		return credits; 
	}

}
