package edu.erau.holdens.fouryearplanner.model;

/** An enumeration of receivable grades, with data for their corresponding GPA weights.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public enum Grade {
	
	/** Standard grades */
	A(4), B(3), C(2), D(1),	F(0),
	/** Passing.  I've only ever seen this for a 0ch course, 
	 * so a weight of 1 should be okay. */
	P(1, "Passing"),
	/** Withdrawal */
	W(Grade.NULL_GRADE, "Withdrawal"),
	/** Null grade for a class that has not been taken yet */
	NULL(Grade.NULL_GRADE, "Not taken");
	
	/** Represents a null grade.  Null grades are for for classes where a grade has not
	 * been received, or a case where the course was taken but withdrawn. */
	public static final int NULL_GRADE = -1;
	
	/** The GPA weight of a grade */
	private int weight;
	/** The description of the grade, if needed */
	private String description;
	
	
	/** Creates a new Grade constant with a specified GPA weight and null description.
	 * @param weight THe GPA weight
	 */
	private Grade(int weight){
		this(weight, null);
	}
	
	/** Creates a new Grade constant with a specified GPA weight and description.
	 * @param weight THe GPA weight
	 * @param description The description of the grade code
	 */
	private Grade(int weight, String description){
		this.weight = weight;
		this.description = description;
	}
	
	
	/** Returns a string description of the grade code, or {@code null} if there
	 * is no description.
	 * @return The description of the grade
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * @return The weight of the grade for GPA calculations
	 */
	public int getWeight(){
		return weight;
	}
	
	
	/** A wrapper for {@link #name()}.
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return this.name();
	}
	
}
