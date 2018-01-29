package edu.erau.holdens.fouryearplanner.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import edu.erau.holdens.fouryearplanner.io.StudentPlanExportUtils;

/** Represents the academic plan for a student.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class StudentPlan extends ArrayList<StudentClass> implements	List<StudentClass> {

	/** */
	private static final long serialVersionUID = 5404121892387946543L;
	
	/** Name of the student */
	private String studentName;
	/** Name of the degree program */
	private String programName;
	
	/** All unique SemesterYearTuples */
	private TreeSet<SemesterYearTuple> semYearTuples = new TreeSet<>();
	
	@Override
	public boolean add(StudentClass sc){
		
		// Add the SemesterYearTuple to the set
		semYearTuples.add(sc.getSemYearTuple());
		
		// TODO If this course is a retake, fix the old grade

		return super.add(sc);
	}

	/** Get the {@link StudentClass} of a given, id, semester, and year.
	 * @param id The ID of the class (e.g. "CS 225")
	 * @param year The year (relative; e.g. 2 = sophomore)
	 * @param semester The semester
	 * @return The class, or <code>null</code> if it cannot be found
	 */
	public StudentClass getCourse(String id, int year, Semester semester){

		// Get the semester
		List<StudentClass> semeseterCourses = getCoursesForSemester(semester, year);
		
		// Find the course in that semester
		for (StudentClass sc : semeseterCourses){
			if (sc.getId().equals(id)){
				return sc;
			}
		}
		
		return null;
	}
	
	
	/** Get the {@link StudentClass}s for a given semester and year.
	 * @param semester The semester
	 * @param year The year (relative; e.g. 2 = sophomore)
	 * @return A list of classes
	 */
	public List<StudentClass> getCoursesForSemester(Semester semester, int year){
		return getCoursesForSemester(new SemesterYearTuple(semester, year));
	}
	
	
	/** Get the {@link StudentClass}s for a given semester and year.
	 * @param syt the SemesterYearTuple
	 * @return A list of classes
	 */
	public List<StudentClass> getCoursesForSemester(SemesterYearTuple syt){
		// Create the list
		List<StudentClass> semeseterCourses = new ArrayList<StudentClass>();
		
		// Check all classes
		for(StudentClass c: this){
			if(c.getSemYearTuple().equals(syt)){
				semeseterCourses.add(c);
			}
		}
		// Return
		return semeseterCourses;	
	}
	
	
	/**
	 * @return The name of the degree program represented by this plan
	 */
	public String getProgramName() {
		return programName;
	}
	
	
	/**
	 * @return the semYearTuples
	 */
	public TreeSet<SemesterYearTuple> getSemYearTuples() {
		return semYearTuples;
	}
	
	
	/**
	 * @return The name of the student represented by this plan
	 */
	public String getStudentName() {
		return studentName;
	}

	/** Get the total number of credit hours for all classes in this plan.
	 * @return The number of credits of all classes in the list.
	 */
	public int getTotalCredits(){
		int credits = 0;
		for(Course c : this){
			credits += c.getCredits();
		}
		return credits; 
	}

	/**
	 * @param programName the name of the program to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/** Exports the plan to the specified Microsoft Excel file.  This method is a wrapper for 
	 * <code>{@link StudentPlanExportUtils}.{@link StudentPlanExportUtils#writeToXLSX(StudentPlan, File) writeToXLSX}(this, f);</code>
	 * @param f The Microsoft Excel file to which to write
	 */
	public void writeAsExcelTo(File f) {
		StudentPlanExportUtils.writeToXLSX(this, f);
	}

	
	/** Writes the plan to the specified file.  This method is a wrapper for 
	 * <code>{@link StudentPlanExportUtils}.{@link StudentPlanExportUtils#writeToXML(StudentPlan, File) writeToXML}(this, f);</code>
	 * @param f The File to which to write
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws IOException if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason.
	 */
	public void writeAsXmlTo(File f) throws ParserConfigurationException, IOException {
		StudentPlanExportUtils.writeToXML(this, f);
	}

}
