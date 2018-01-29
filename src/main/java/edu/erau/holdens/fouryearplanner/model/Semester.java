package edu.erau.holdens.fouryearplanner.model;

/** Enumeration representing the four semesters at ERAU (Fall, Spring, Summer A, and Summer B).
 * Note that this just represents semesters in general,
 * and not a semester that is specific to any particular year.
 * @author Sean Holden (holdens@my.erau.edu)
 * @see SemesterYearTuple
 */
public enum Semester {

	FA("Fall"),
	SP("Spring"),
	SA("Summer A"),
	SB("Summer B");
	
	/** A human-friendly description of the semester enum constant */
	private String description;
	
	private Semester(String description){
		this.description = description;
	}
	
	/** Provides the full name of the semester represented by this constant.  For example,
	 * <code>SA.toString()</code> will return <code>"Summer A"</code>.
	 * @return 	A human-friendly description of the semester enum constant
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return description;
	}
	
}
