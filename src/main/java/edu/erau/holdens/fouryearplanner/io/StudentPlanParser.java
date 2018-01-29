package edu.erau.holdens.fouryearplanner.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.Grade;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;
import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

/** An XML parser for a student plan
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class StudentPlanParser {
	
	// TODO have semester tags with classes inside
	// TODO this will probably require some rework of the model for efficiency
	
	//use document builder class to build 
	//parser for elements inside XML file
	private DocumentBuilder builder;

	/** Constructs a parser */
	public StudentPlanParser() throws ParserConfigurationException {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	/** Parses an XML file containing a student plan.
	 * @param fileName The name of the XML file to parse
	 * @return A StudentPlan built from the XML file
	 * @throws SAXException If any parse errors occur.
	 * @throws IOException If any IO errors occur.
	 */
	public StudentPlan parse(String fileName) throws SAXException, IOException {
		return parse(new File(fileName));
	}
	
	/** Parses an XML file containing a student plan.
	 * @param file The XML file to parse
	 * @return A StudentPlan built from the XML file
	 * @throws SAXException If any parse errors occur.
	 * @throws IOException If any IO errors occur.
	 */
	public StudentPlan parse(File file) throws SAXException, IOException {
		
		Document doc = builder.parse(file);
		
		StudentPlan plan = new StudentPlan();
		
		// Get header info
		String student_name;
		String program;
		
		Element planElement = (Element)doc.getElementsByTagName("studentplan").item(0);
		
		// Get student name
		try{ 	student_name = planElement.getAttribute("student_name"); }
		catch (NullPointerException | IllegalArgumentException ex){ 
				student_name = "(unspecified name)"; }	// TODO test
		
		// Get degree program name
		try{ 	program = planElement.getAttribute("program");	}
		catch (NullPointerException | IllegalArgumentException ex){	
				program = "(unspecified program)"; }	// TODO test
		
		plan.setStudentName(student_name);
		plan.setProgramName(program);

		
		NodeList semesters = doc.getElementsByTagName("semester");
				
		for (int i = 0; i < semesters.getLength(); i++){
			Element semElement = (Element) semesters.item(i);
			Semester semTaken = Enum.valueOf(Semester.class, semElement.getAttribute("type"));
			int yearTaken = Integer.parseInt(semElement.getAttribute("year"));
			

			// get list of item elements in the document
			NodeList classes = semElement.getElementsByTagName("class");

			// process each item element      
			for (int j = 0; j < classes.getLength(); j++) {   

				// get the only id element in each studentplan element   
				Element classElement = (Element) classes.item(j);
				String id = classElement.getAttribute("_id");
				Course c = MasterCourseMap.getInstance().get(id);

				Grade grade;
				try{
					grade = Enum.valueOf(Grade.class, classElement.getAttribute("grade"));
				}
				catch (NullPointerException | IllegalArgumentException ex){
					grade = Grade.NULL;
				}
				
				boolean moddable = false;
				try{
					moddable = Boolean.parseBoolean(classElement.getAttribute("moddable"));
				} catch (Exception e){
					// Do nothing - false by default
					System.out.println("[StudentPlanParser] Error parsing modibility for " + id);
					e.printStackTrace();
				}

				if (c == null){
					System.err.println("[StudentPlanParser] Error adding " + id + ": null object");
				}
				else{
					plan.add(new StudentClass(c, new SemesterYearTuple(semTaken, yearTaken), grade, moddable));
				}
			}

		}
		
		return plan;
	}
}

