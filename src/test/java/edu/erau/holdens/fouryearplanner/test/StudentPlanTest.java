package edu.erau.holdens.fouryearplanner.test;

import static edu.erau.holdens.fouryearplanner.model.Grade.A;
import static edu.erau.holdens.fouryearplanner.model.Grade.B;
import static edu.erau.holdens.fouryearplanner.model.Grade.C;
import static edu.erau.holdens.fouryearplanner.model.Grade.D;
import static edu.erau.holdens.fouryearplanner.model.Grade.F;
import static edu.erau.holdens.fouryearplanner.model.Grade.NULL;
import static edu.erau.holdens.fouryearplanner.model.Semester.FA;
import static edu.erau.holdens.fouryearplanner.model.Semester.SP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import edu.erau.holdens.fouryearplanner.io.StudentPlanParser;
import edu.erau.holdens.fouryearplanner.model.GPACalculator;
import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

/** Tests various methods of the {@link StudentPlan} and {@link GPACalculator} classes.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class StudentPlanTest {
	
	public static final String
	SAMPLE_FILE_PATH = "src/test/resources/sampleplan.xml",
	SAMPLE_AFTER_FILE_PATH = "src/test/resources/sampleplan-after.xml";

	@Test
	public void testFromSample() throws SAXException, IOException, ParserConfigurationException {

		/** The maximum delta between expected and actual for which both GPAs are still considered equal. */
		final double DELTA = 0.0005;

		// Parse a plan
		StudentPlan sp = new StudentPlanParser().parse(SAMPLE_FILE_PATH);

		// Test header info
		assertEquals("BS Computer Science - Standard Track", sp.getProgramName());
		assertEquals("Sample Student", sp.getStudentName());

		StudentClass studentClass;
		List<StudentClass> classes;

		classes = sp.getCoursesForSemester(FA, 2);
		studentClass = classes.get(0);
		assertEquals("CEC 220", studentClass.getId());

		classes = sp.getCoursesForSemester(FA, 4);
		//		System.out.println(classes);


		studentClass = sp.getCourse("CS 490", 4, FA);
		assertNotNull("Class not found!", studentClass);
		assertEquals("CS 490", studentClass.getId());


		// Reset all grades
		for (StudentClass sc : sp){
			sc.setGrade(NULL);
		}

		int workingYear;
		Semester workingSem;

		// Assign some grades
		workingYear = 4;
		workingSem = FA;
		sp.getCourse("CS 490", workingYear, workingSem).setGrade(A);
		sp.getCourse("BA 438", workingYear, workingSem).setGrade(A);
		sp.getCourse("CS 455", workingYear, workingSem).setGrade(B);

		// Test grade assignment
		assertEquals(A, sp.getCourse("CS 490", workingYear, workingSem).getGrade());
		assertEquals(A, sp.getCourse("BA 438", workingYear, workingSem).getGrade());
		assertEquals(B, sp.getCourse("CS 455", workingYear, workingSem).getGrade());

		// Test GPA calc
		assertEquals(4.0, GPACalculator.calculateGPA(sp.getCourse("CS 490", workingYear, workingSem)), DELTA);
		assertEquals(3.667, GPACalculator.calculateGPA(sp.getCoursesForSemester(workingSem, workingYear)), DELTA);
		assertEquals(3.667, GPACalculator.calculateGPA(sp), DELTA);

		// Let's test a really bad semester
		workingYear = 1;
		workingSem = SP;
		sp.getCourse("COM 219", workingYear, workingSem).setGrade(B);
		sp.getCourse("COM 221", workingYear, workingSem).setGrade(C);
		sp.getCourse("EGR 115", workingYear, workingSem).setGrade(C);
		sp.getCourse("PS 160", workingYear, workingSem).setGrade(F);
		sp.getCourse("MA 242", workingYear, workingSem).setGrade(D);
		assertEquals(1.563, GPACalculator.calculateGPA(sp.getCoursesForSemester(workingSem, workingYear)), DELTA);

		// CGPA so far
		assertEquals(2.320, GPACalculator.calculateGPA(sp), DELTA);

		// That PS 160 really sucked... let's re-take it
		sp.add(new StudentClass("PS 160", new SemesterYearTuple(FA, 2), B));

		// New CGPA so far with PS 160 overwritten
		sp.getCourse("PS 160", 1, SP).setGrade(NULL); // TODO do this automatically in StudentPlan
		assertEquals(2.680, GPACalculator.calculateGPA(sp), DELTA);

		// Write the updated data to a new XML file
		sp.writeAsXmlTo(new File(SAMPLE_AFTER_FILE_PATH));

	}

	@Test
	public void testFromScratch(){
		StudentPlan sp;

		// Create empty plan
		sp = new StudentPlan();

		// Make sure everything is showing as empty
		StudentClass sampleClass = new StudentClass("CS 225", new SemesterYearTuple(SP, 2));
		assertEquals(0, sp.size());
		assertEquals(false,  sp.contains(sampleClass));
		assertEquals(null, sp.getCourse(sampleClass.getId(), sampleClass.getYearTaken(), sampleClass.getSemesterTaken()));
		assertEquals(0, sp.getTotalCredits());

		// Now add it to the plan and test again
		sp.add(sampleClass);
		assertEquals(1, sp.size());
		assertEquals(true,  sp.contains(sampleClass));
		assertEquals(sampleClass, sp.getCourse(sampleClass.getId(), sampleClass.getYearTaken(), sampleClass.getSemesterTaken()));
		assertEquals(4, sp.getTotalCredits());
	}

	@Test
	public void testModability() throws SAXException, IOException, ParserConfigurationException{

		StudentPlan sp;

		sp = new StudentPlanParser().parse(SAMPLE_FILE_PATH);		
		assertEquals(false, sp.getCourse("CS 225", 2, FA).isModdable());
		assertEquals(true, sp.getCourse("SS 1XX", 3, FA).isModdable());

		// Write the updated data to a new XML file
		sp.writeAsXmlTo(new File(SAMPLE_AFTER_FILE_PATH));

		// Re-read to see if data is preserved
		sp = new StudentPlanParser().parse(SAMPLE_AFTER_FILE_PATH);
		assertEquals(false, sp.getCourse("CS 225", 2, FA).isModdable());
		assertEquals(true, sp.getCourse("SS 1XX", 3, FA).isModdable());
	}

	@Test
	public void testSemYearTuples() throws SAXException, IOException, ParserConfigurationException{
		StudentPlan sp = new StudentPlanParser().parse(SAMPLE_FILE_PATH);
		assertEquals(8, sp.getSemYearTuples().size());
	}
}
