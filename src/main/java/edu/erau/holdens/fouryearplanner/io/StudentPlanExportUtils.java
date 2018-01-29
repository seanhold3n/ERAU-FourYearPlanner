package edu.erau.holdens.fouryearplanner.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import edu.erau.holdens.fouryearplanner.model.StudentPlan;

public class StudentPlanExportUtils {
	
	
	/** Writes the plan to the specified Microsoft Excel file.
	 * @param plan The StudentPlan to write
	 * @param file The .XLSX file to which to write
	 */
	public static void writeToXLSX(StudentPlan plan, File file){
		// TODO unimplemented
	}
	
	/** Writes the plan to the specified XML file.
	 * @param plan The StudentPlan to serialize and write
	 * @param file The File to which to write
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
	 * @throws IOException if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason.
	 */
	public static void writeToXML(StudentPlan plan, File file) throws ParserConfigurationException, IOException{
		Document doc = new StudentPlanBuilder().build(plan);
		DOMImplementation impl = doc.getImplementation();
		DOMImplementationLS implLS 
		= (DOMImplementationLS) impl.getFeature("LS", "3.0");
		LSSerializer ser = implLS.createLSSerializer();
		ser.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
		ser.writeToURI(doc, file.toURI().toString());
	}

}
