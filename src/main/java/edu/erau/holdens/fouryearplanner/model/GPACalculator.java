package edu.erau.holdens.fouryearplanner.model;

import java.util.HashMap;
import java.util.List;

public class GPACalculator {
	
	public static double calculateGPA(StudentClass sc){
		if (sc.getGrade().getWeight() == Grade.NULL_GRADE){
			return Double.NaN;
		}
		else{
			return sc.getGrade().getWeight();
		}
	}

	public static double calculateGPA(List<StudentClass> courses) {
		
		/** Credits attempted */
		int creditsAttempted = 0;
		/** Credits earned */
		int creditsEarned = 0;
		
		// Sort the list
		courses.sort(null);
		
		// Map each course to a grade.  This enforces using only the most recent grade for a course
		HashMap<String, StudentClass> grades = new HashMap<String, StudentClass>(courses.size()*2);
		for (StudentClass sc : courses){
			if (sc.getGrade().getWeight() != Grade.NULL_GRADE){
				grades.put(sc.getId(), sc);
			}
		}
		
		// Compute the GPA for all courses in the map		
		for (StudentClass sc : grades.values()){
				creditsAttempted += sc.getCredits();
				creditsEarned += sc.getGrade().getWeight() * sc.getCredits();
		}
		
		return (double)creditsEarned/creditsAttempted;
	}

}
