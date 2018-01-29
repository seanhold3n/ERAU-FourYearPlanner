package edu.erau.holdens.fouryearplanner.gui;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

/** A pane containing the GUI elements to add a semester column.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class AddSemesterPane extends BorderPane{


	private final ComboBox<Semester> cbxSemesters;
	
	private Button btConfirm;
	private Button btCancel;
	
	public AddSemesterPane(StudentPlan sp){

		cbxSemesters = new ComboBox<Semester>(FXCollections.observableArrayList(Semester.values()));
		
		
	}
	
}
