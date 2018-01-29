package edu.erau.holdens.fouryearplanner.test;

import static edu.erau.holdens.fouryearplanner.model.Semester.SP;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.model.Grade;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;
import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;

public class StudentClassTest {

	/**
	 * @see StudentClass#compareTo(StudentClass)
	 */
	@Test
	public void testCompareTo() {
		
		// Create some SemYearTuples
		final SemesterYearTuple
		FA01 = new SemesterYearTuple(Semester.FA, 1),
		SP01 = new SemesterYearTuple(Semester.SP, 1),
		FA02 = new SemesterYearTuple(Semester.FA, 2),
		SP02 = new SemesterYearTuple(Semester.SP, 2);
		
		
		// Create some courses
		final StudentClass 
		sc01 = new StudentClass("BA 201", FA01),
		sc02 = new StudentClass("MA 241", SP01),
		sc03 = new StudentClass("CS 222", FA02),
		sc04 = new StudentClass("CS 225", FA02),
		sc05 = new StudentClass("MA 242", FA02),
		sc06 = new StudentClass("MA 242", SP02);
		
		// Add the courses to the list in an unsorted order
		List<StudentClass> classes = new ArrayList<StudentClass>();
		classes.add(sc04);
		classes.add(sc01);
		classes.add(sc03);
		classes.add(sc06);
		classes.add(sc05);
		classes.add(sc02);
		
		// Sort the list
		classes.sort(null);
		
		// See if the list is sorted
		assertEquals(sc01, classes.get(0));
		assertEquals(sc02, classes.get(1));
		assertEquals(sc03, classes.get(2));
		assertEquals(sc04, classes.get(3));
		assertEquals(sc05, classes.get(4));
		assertEquals(sc06, classes.get(5));
		
	}
	
	@Test
	public void testModability(){

		StudentClass sampleClass;
		
		// Explicitly true
		sampleClass = new StudentClass(MasterCourseMap.getInstance().get("CS 225"), new SemesterYearTuple(SP, 2), Grade.NULL, true);
		assertEquals(true, sampleClass.isModdable());

		// Explicitly false
		sampleClass = new StudentClass(MasterCourseMap.getInstance().get("CS 225"), new SemesterYearTuple(SP, 2), Grade.NULL, false);
		assertEquals(false, sampleClass.isModdable());

		// Implicitly false
		sampleClass = new StudentClass(MasterCourseMap.getInstance().get("CS 225"), new SemesterYearTuple(SP, 2), Grade.NULL);
		assertEquals(false, sampleClass.isModdable());
	}

}
