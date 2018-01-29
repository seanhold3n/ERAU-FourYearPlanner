package edu.erau.holdens.fouryearplanner.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.io.CourseListParser;
import edu.erau.holdens.fouryearplanner.model.CourseList;

/** Tests for the {@link CourseList} class
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class CourseListTest {

	/** Tests {@link CourseList#getCourseByID(String)} and {@link CourseList#getTotalCredits()} 
	 * @throws Exception If any number of things goes wrong during parsing
	 */
	@Test
	public void test() throws Exception{
		
		// Create a new CourseList
		CourseList list = new CourseListParser().parse("courses/test-courses.xml");
		
		// Test credit count
		assertEquals(115, list.getTotalCredits());
		
		// Check courses
		assertNotNull(list.getCourseByID("CS 225"));
		assertNull(list.getCourseByID("BA 201"));
		
		list.remove(list.getCourseByID("CS 225"));
		assertEquals(111, list.getTotalCredits());
		
	}


}
