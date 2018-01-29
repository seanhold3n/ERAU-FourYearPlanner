package edu.erau.holdens.fouryearplanner.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.io.CourseGetter;
import edu.erau.holdens.fouryearplanner.model.Course;

/** Tests the {@link CourseGetter} class.
 * @author Sean Holden (holdens@my.erau.edu)
 */
@org.junit.Ignore
public class CourseGetterTest {

	/** Tests the {@link CourseGetter#getCourse(String)} method.
	 * @throws IOException If an IO error occurs while contacting the server
	 */
	@Test
	public void testGetCourse() throws IOException {
		Course c = CourseGetter.getCourse("CS 225DB");
		assertEquals("CS 225", c.getId());
		assertEquals(4, c.getCredits());
		assertEquals("Computer Science II", c.getName());
	}

}
