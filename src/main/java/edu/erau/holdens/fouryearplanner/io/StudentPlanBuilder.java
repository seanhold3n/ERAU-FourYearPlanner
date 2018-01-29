package edu.erau.holdens.fouryearplanner.io;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;


/** Builds a DOM document for a program of study */
public class StudentPlanBuilder {
	
	// TODO have semester tags with classes inside
	// TODO this will probably require some rework of the model for efficiency

	private DocumentBuilder builder;
	private Document doc;

	/** Constructs a StudentPlan builder.
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 */
	public StudentPlanBuilder() throws ParserConfigurationException {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	/** Builds a DOM document for a program.
	 * @param plan the program
	 * @return a DOM document describing the program
	 */
	public Document build(StudentPlan plan) {
		doc = builder.newDocument();
		doc.appendChild(createItems(plan));
		return doc;
	}

	/** Builds a DOM element for a program.
	 * @param plan the program
	 * @return a DOM element describing the program
	 */      
	private Element createItems(StudentPlan plan) {      
		Element e = doc.createElement("studentplan");
		e.setAttribute("student_name", plan.getStudentName());
		e.setAttribute("program", plan.getProgramName());

		for (SemesterYearTuple syt : plan.getSemYearTuples()){
			
			Element se = createSemester(syt);
			
			for (StudentClass sc : plan){
				// TODO O(n^2!!!)
				if (sc.getSemYearTuple().equals(syt)){
					se.appendChild(createClass(sc));
				}
			}
			
			e.appendChild(se);
		}
		
		return e;
	}
	
	/** Builds a DOM element for a semester-year.
	 * @param syt the SemesterYearTuple
	 * @return a DOM element describing the StudentClass
	 */
	private Element createSemester(SemesterYearTuple syt) {
		Element e = doc.createElement("semester");
		e.setAttribute("type", syt.semester.name());
		e.setAttribute("year", syt.year + "");
		return e;
	}

	/** Builds a DOM element for a StudentClass.
	 * @param c the StudentClass
	 * @return a DOM element describing the StudentClass
	 */
	private Element createClass(StudentClass c) {
		Element e = doc.createElement("class");
		e.setAttribute("_id", c.getId());
		e.setAttribute("grade", c.getGrade().name());
		if (c.isModdable()){
			e.setAttribute("moddable", "true");
		}
		return e;
	}

}
