package edu.erau.holdens.fouryearplanner.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;

/** Tests the {@link MasterCourseMap}.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class MapTest {

	/** Test to see if the map contains certain desired courses. */
	@Test
	public void testContents() {

		// Check to see that all comp sci courses are in the map
		assertNotNull(MasterCourseMap.getInstance().get("CS 225"));

		String[] courses = new String[]{
				"COM 122",
//				"HU 14X",
				"UNIV 101",
				"PS 150",
				"MA 241",
				"COM 219",
				"COM 221",
				"EGR 115",
				"PS 160",
				"MA 242",
				"CEC 220",
				"CS 222",
				"CS 225",
				"PS 250",
				"PS 253",
//				"MA 245", // No longer exists
				"MA 348",
				"SE 300",
				"CS 317",
				"CS 315",
				"CS 332",
				"CEC 470",
				"CS 344",
				"CS 420",
				"CS 490",
				"CS 491",
				"SE 420",
				"SE 320"
		};

		System.out.println("[MapTest] Checking course map for " + courses.length + " BSCS courses...");
		
		for (String s : courses){
			assertNotNull("[MapTest] Course " + s + " not present!", MasterCourseMap.getInstance().get(s));
		}
		
		System.out.println("[MapTest] Complete!");

	}
	
}
