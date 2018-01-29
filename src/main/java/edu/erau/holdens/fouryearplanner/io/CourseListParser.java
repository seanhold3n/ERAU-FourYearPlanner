package edu.erau.holdens.fouryearplanner.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.CourseList;

/**
 * An XML parser for item lists
 * @author Adriana Strelow, Sean Holden<br>
 * {strelowa, holdens}@my.erau.edu
 */
public class CourseListParser
{
	//use document builder class to build 
	//parser for elements inside XML file
	private DocumentBuilder builder;

	/**
      Constructs a parser that can parse item lists.
	 */
	public CourseListParser() throws ParserConfigurationException {
		DocumentBuilderFactory dbfactory
		= DocumentBuilderFactory.newInstance();
		builder = dbfactory.newDocumentBuilder();
	}
	
	/**
      Parses an XML file containing an item list.
      @param fileName the name of the file
      @return an array list containing all items in the XML file
	 */
	public CourseList parse(String fileName) throws SAXException, IOException {
		CourseList items = new CourseList();
		File f = new File(fileName);
		Document doc = builder.parse(f);

		// get list of item elements in the document
		NodeList courses = doc.getElementsByTagName("course");

		// process each item element      
		//System.out.println(courses.getLength());
		for (int i = 0; i < courses.getLength(); i++) {   

			// get the only id element in each compscicourse element   
			Element courseElement = (Element) courses.item(i);
			String id = courseElement.getAttribute("id");
			//System.out.println("ID: " + id);

			if(!id.equals("")){

				// get the only title element in an course element    	
				Element titleElement = (Element) courseElement.getElementsByTagName("title").item(0);
				String title = titleElement.getTextContent();
				//System.out.println(title);

				// get the only credits element in a course element    	
				Element creditsElement = (Element) courseElement.getElementsByTagName("credits").item(0);
				int credits = Integer.parseInt(creditsElement.getTextContent());

				// get pre req element in a course element
				List<String> prereqs = new ArrayList<String>();

				try{
					Element prereqsElement = (Element) courseElement.getElementsByTagName("prereqs").item(0);
					
					// TODO For each courseid element in the prereqsElement
					NodeList prereqnodes = prereqsElement.getElementsByTagName("courseid");
					for (int j = 0; j < prereqnodes.getLength(); j++) {
						// Get the id as an element
						Element courseIdElement = (Element) prereqnodes.item(j);

						// Find the course corresponding to that ID in items
						Course c = items.getCourseByID(courseIdElement.getTextContent());
						if (c == null){
							throw new IOException("Thing not found, guy!");
						}
						// Add it to a temporary preReqs list
						prereqs.add(courseIdElement.getTextContent());
					}

//					System.out.println("pre requisites: " + prereqs);

				} catch (Exception e){}

				// get when offered element in course element
				String whenoffered = null;
				try{
					Element whenofferedElement = (Element) courseElement.getElementsByTagName("whenoffered").item(0);
					whenoffered = whenofferedElement.getTextContent();
				} catch (Exception e){}

				//construct LineItem object
				Course course = new Course(id, prereqs, title, whenoffered, credits, "");

				// add LineItem object to ArrayList that will be returned    	
				items.add(course);
			}
		}

		return items;
	}
}









