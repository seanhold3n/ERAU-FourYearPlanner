package edu.erau.holdens.fouryearplanner.model;

import java.io.Serializable;

/** Represents a semester-year pair.
 * @author Sean Holden (holdens@my.erau.edu)
 * @see Semester
 */
// TODO even though I like the word "tuple", calling this whole class a term might make more sense
public class SemesterYearTuple implements Comparable<SemesterYearTuple>, Serializable {

	/** */
	private static final long serialVersionUID = -5057245611044746027L;
	
	
	public final Semester semester;
	public final int year;
	
	
	/** Creates a semester-year tuple to specify a specific semester
	 * @param semester The semester
	 * @param year The year
	 */
	public SemesterYearTuple(Semester semester, int year) {
		this.semester = semester;
		// TODO year < 0, or other extreme inputs?
		this.year = year;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SemesterYearTuple o) {
		
		// Yes, this method has multiple exit points.  Blah.
		
		if (this.year < o.year){
			return -1;
		}
		else if (this.year > o.year){
			return 1;
		}
		else{
			// Years are the same; base it on semester
			return this.semester.ordinal() - o.semester.ordinal();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SemesterYearTuple){
			SemesterYearTuple obj2 = (SemesterYearTuple) obj;
			return (this.semester == obj2.semester && this.year == obj2.year);
		}
		else{
			return false;
		}
	};
	
	@Override
	public String toString(){
		return (year == 0) ? "Transfer" : semester.toString() + " " + year;
	}
	
}
