package edu.erau.holdens.fouryearplanner.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

/** This is an old test class used to figure out why certain course IDs were not loading.
 * As it turns out, there is sometimes a one-bit difference in the space between the course names,
 * and because it didn't exactly match a traditional space, the course ID was skipped.
 * @author Sean Holden (holdens@my.erau.edu)
 */
@Ignore
public class StringEqualityTest {

	@Test
	public void testControl(){
		// This is the control - it will pass
		assertEquals("CS 315", "CS 315");
	}
	
	/** Tests the equality of a typed string and a copied string.  Even though they appear to be the same, 
	 * they are different on the byte level, causing this test to fail. */
	@Test
	public void testExperimental() {
		final String TYPED = "CS 315";
		final String COPIED = "CS 315";
		
//		assertEquals(TYPED, COPIED);
		
		printBytes(TYPED);
		printBytes(COPIED);

		final String TYPED_SPACE = " ";
		final String COPIED_SPACE = " ";
		
		printBytes(TYPED_SPACE);
		printBytes(COPIED_SPACE);
		
		assertEquals(TYPED_SPACE, COPIED_SPACE);
	}
	
	private void printBytes(String s){
		System.out.println(Arrays.toString(s.getBytes()));
	}

	@Test
	public void testShift() {

		final String TYPED_SPACE = " ";
		final String COPIED_SPACE = " ";
	
		// Get byte
		byte COPIED_BYTE = COPIED_SPACE.getBytes()[0];
//		System.out.println(COPIED_BYTE);
		COPIED_BYTE <<= 1;
		COPIED_BYTE >>= 1;
//		System.out.println(COPIED_BYTE);
		
		assertEquals(COPIED_BYTE, TYPED_SPACE.getBytes()[0]);
		
	}
	
}
