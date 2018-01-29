package edu.erau.holdens.fouryearplanner.model;


/** Represents a StudentClass.  In addition to the everything inherited from {@linkplain Course},
 * this class tracks when a student took the course and what grade they received. 
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class StudentClass extends Course implements Comparable<StudentClass> {

	/** ID used for serialization */
	private static final long serialVersionUID = -6991279086565613924L;
	
	/** The grade that the student received in the class */
	private Grade grade;
	/** */
	private boolean isModdable;
	
	/** A 2-tuple representing the {@linkplain Semester semester} and year
	 * when this class was (or is scheduled to be) taken. 
	 * The year is relative (e.g. <code>2</code> for sophomore) */
	private SemesterYearTuple semYearTuple;
	
	
	/** Constructs a new StudentClass object with a default {@linkplain Grade#NULL null grade}.
	 * @param courseID The ID of the {@linkplain Course} object off of which the class will be based
	 * @param syt The SemesterYearTuple representing when the class was/will be taken
	 */
	public StudentClass(String courseID, SemesterYearTuple syt) {
		this(MasterCourseMap.getInstance().get(courseID), syt, Grade.NULL);
	}
	
	/** Constructs a new StudentClass object with a default {@linkplain Grade#NULL null grade}.
	 * @param c The Course object off of which the class will be based
	 * @param syt The SemesterYearTuple representing when the class was/will be taken
	 */
	public StudentClass(Course course, SemesterYearTuple semYearTuple) {
		this(course, semYearTuple, Grade.NULL);
	}
	
	/** Constructs a new StudentClass object.
	 * @param courseID The ID of the {@linkplain Course} object off of which the class will be based
	 * @param syt The SemesterYearTuple representing when the class was/will be taken
	 * @param grade The grade that the student received in the course
	 */
	public StudentClass(String courseID, SemesterYearTuple syt, Grade grade) {
		this(MasterCourseMap.getInstance().get(courseID), syt, grade);
	}
	
	/** Constructs a new StudentClass object.
	 * @param c The Course object off of which the class will be based
	 * @param syt The SemesterYearTuple representing when the class was/will be taken
	 * @param grade The grade that the student received in the course
	 */
	public StudentClass(Course c, SemesterYearTuple syt, Grade grade){
		this(c, syt, grade, false);
	}

	/** Constructs a new StudentClass object.
	 * @param c The Course object off of which the class will be based
	 * @param syt The SemesterYearTuple representing when the class was/will be taken
	 * @param grade The grade that the student received in the course
	 * @param moddable If the class is moddable (that is, if the user is allowed to change the 
	 */
	public StudentClass(Course c, SemesterYearTuple syt, Grade grade, boolean moddable) {
		// TODO is there a better way to do this?
		super(c.getId(), c.getPreReqList(), c.getName(), c.getSemesterOffered(), 
				c.getCredits(), c.getDescription());
		this
		.setSemesterYearTuple(syt)
		.setGrade(grade);
		this.isModdable = moddable;
	}
	
	
//	public static StudentClass newInstance(Course c, int yearTaken, Semester semesterTaken){
//		return newInstance(c, yearTaken, semesterTaken, Grade.NULL);
//	}
	

	// TODO doesn't work - ClassCastException
//	public static StudentClass newInstance(Course c, int yearTaken, Semester semesterTaken, Grade grade){
//		// Cast the Course as a StudentClass (I'd rather do this than mess with constructors)
//		return ((StudentClass) c)
//		.setYearTaken(yearTaken)
//		.setSemesterTaken(semesterTaken)
//		.setGrade(grade);		
//	}


	/** Compares two StudentClasses.  This method sorts classes first based on the {@link SemesterYearTuple}, 
	 * and then based on the ID of the Course as given by {@link Course#getId()}.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StudentClass o) {
		int semesterCompare = this.getSemYearTuple().compareTo(o.getSemYearTuple());
		if (semesterCompare != 0){
			return semesterCompare;
		}
		else {
			return this.getId().compareTo(o.getId());
		}
	}


	/**
	 * @return the grade
	 */
	public Grade getGrade() {
		return grade;
	}

	/**
	 * @return the semesterTaken
	 */
	public Semester getSemesterTaken() {
		return semYearTuple.semester;
	}
	
	/**
	 * @return the semYearTuple
	 */
	public SemesterYearTuple getSemYearTuple() {
		return semYearTuple;
	}

	/**
	 * @return when the class was taken
	 */
	public int getYearTaken() {
		return semYearTuple.year;
	}
	
	
	/**
	 * @return If this class is moddable
	 */
	public boolean isModdable(){
		return isModdable;
	}

	/**
	 * @param grade the grade to set
	 * @return This object, for use in the context of method chaining
	 */
	public StudentClass setGrade(Grade grade) {
		this.grade = grade;
		return this;
	}

	
	/** Set when the course was/will-be taken.
	 * @param semesterTaken The semester taken
	 * @param yearTaken The year taken
	 * @return This object, for use in the context of method chaining
	 */
	public StudentClass setSemesterYearTuple(Semester semesterTaken, int yearTaken) {
		return setSemesterYearTuple(new SemesterYearTuple(semesterTaken, yearTaken));
	}

	/** Set when the course was/will-be taken.
	 * @param syt The SemesterYearTuple representing when the course was/will-be taken
	 * @return This object, for use in the context of method chaining
	 */
	public StudentClass setSemesterYearTuple(SemesterYearTuple syt) {
		this.semYearTuple = syt;
		return this;
	}

}
