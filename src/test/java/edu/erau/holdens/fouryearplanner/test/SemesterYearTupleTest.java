package edu.erau.holdens.fouryearplanner.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;

/** Tests various methods of the {@link SemesterYearTuple} class.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class SemesterYearTupleTest {

	/** Tests the {@link SemesterYearTuple#equals(Object)} method. */
	@Test
	public void testEquals() {
		SemesterYearTuple tuple1, tuple2;
		
		tuple1 = new SemesterYearTuple(Semester.FA, 3);
		tuple2 = new SemesterYearTuple(Semester.FA, 3);
		assertEquals(tuple1, tuple2);
		
		tuple2 = new SemesterYearTuple(Semester.SP, 3);
		assertNotEquals(tuple1, tuple2);
		
		assertNotEquals(tuple1, new Object());
	}
	
	/** Tests the {@link SemesterYearTuple#compareTo(SemesterYearTuple)} method. */
	@Test
	public void testCompare(){
		SemesterYearTuple tuple1, tuple2;
		
		tuple1 = new SemesterYearTuple(Semester.SP, 3);
		tuple2 = new SemesterYearTuple(Semester.SP, 3);
		assertEquals(0, tuple1.compareTo(tuple2));
		
		// Different years
		tuple1 = new SemesterYearTuple(Semester.SP, 2);
		assertEquals(-1, tuple1.compareTo(tuple2));
		
		tuple1 = new SemesterYearTuple(Semester.SP, 4);
		assertEquals(1, tuple1.compareTo(tuple2));
		
		// Same years, different semesters
		tuple1 = new SemesterYearTuple(Semester.FA, 3);
		assertEquals(-1, tuple1.compareTo(tuple2));
		
		tuple1 = new SemesterYearTuple(Semester.SA, 3);
		assertEquals(1, tuple1.compareTo(tuple2));
	}
	
	/** Tests the {@link SemesterYearTuple#toString()} method. */
	@Test
	public void testToString(){
		SemesterYearTuple tuple1;
		tuple1 = new SemesterYearTuple(Semester.FA, 3);
		assertEquals("Fall 3", tuple1.toString());

		tuple1 = new SemesterYearTuple(Semester.SP, 0);
		assertEquals("Transfer", tuple1.toString());
	}

}
