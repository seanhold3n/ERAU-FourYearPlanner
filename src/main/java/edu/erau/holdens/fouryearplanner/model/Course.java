package edu.erau.holdens.fouryearplanner.model;

import java.io.Serializable;
import java.util.List;

/** Represents a Course.
 * @author Adriana Strelow, Sean Holden
 * <br>{strelowa, holdens}@my.erau.edu
 */
public class Course implements Serializable{

	private static final long serialVersionUID = -1965627000046103163L;
	
	//initialize the following elements inside the Course XML
	//document.
	private final String id;
	private final String title;
	private final String description;
	private final String whenoffered;
	private final int credits;
//	private CourseList preReqsList;
	private List<String> preReqsList;
	
//	private boolean isModdable = false;
	
	
	//Course method to get each element 
	public Course(String id, List<String> prereqs, String title,
			String whenoffered, int credits, String description) {
		super();
		this.id = id;
		this.title = title;
		this.preReqsList = prereqs;
		this.whenoffered = whenoffered;
		this.credits = credits;
		this.description = description;
	}
	
	/** Gets the description of a course element.
	 * @return The course description per the catalog
	 */
	public String getDescription() {
		return description;
	}
	
	/** Gets the id of a course element.
	 * @return The course ID (e.g. "CS 225")
	 */
	public String getId() {
		return id;
	}

	/** Gets the prerequisites of each course element as a List of Strings containing the course IDs.
	 * @return The prerequisites specific to a desired course
	 */
	public List<String> getPreReqList() {
		return preReqsList;
	}

	/** Gets the proper name/title of the course.
	 * @return The name of the course (e.g. "Computer Science II")
	 */
	public String getName() {
		return title;
	}
 
	/** Gets the semester that the course is offered.
	 * @return The semester offered (e.g. //TODO insert eg here)
	 */
	public String getSemesterOffered() {
		return whenoffered;
	}

	/** Gets the number of credit-hours that the course is worth.
	 * @return The number of credits
	 */
	public int getCredits() {
		return credits;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
//	public void setID(String id){
//		
//	}
//	
//	public void setTitle(String title){
//		
//	}
	
	
	@Override
	public String toString(){
		return id;
	}

	/** TODO the Following method is used for future functionality.
	//the method will determine if a course is slid into a column 
	//where the course is not officially offered. (i.e. courses only
	//offered in the spring must be taken in a spring semester).
	//the course must snap back to the default semester if the case is 
	//violated. 

	/*public void setSemesterTaken(String semesterTaken) {
		if(!doesViolatePrereqs(semesterTaken)){ this.semesterTaken = semesterTaken; }
	}


	//the Following method is used for future functionality.
	//the mehtod will determine if the user slides a course before
	//its specified pre-requisite. If the course slides before the 
	//the pre-requisite then it does violate the semester
	// if this is violated then the course icon must slide 
	//slide back to the default offering.  
	 *
	 * 
	private boolean doesViolatePrereqs(String semesterTaken){

		for (Course c : preReqsList){
			if (c.getSemesterNumber() <= getSemesterNumber(semesterTaken)){
				return true;
			}
		}

		return false;
	}*/

}
