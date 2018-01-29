package edu.erau.holdens.fouryearplanner.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import edu.erau.holdens.fouryearplanner.model.Grade;


/** A pane containing the GUI elements to change the grade of a course.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class GradeChangePane extends BorderPane {

//	private final ComboBox<Grade> gradesComboBox;
	
	private GridPane radioGrid;
	
	/** Creates a new GradeChangePane
	 * @param ci The CourseIcon containing the course of which this will change the grade
	 */
	public GradeChangePane(CourseIcon ci){
//		gradesComboBox = new ComboBox<Grade>(FXCollections.observableArrayList(Grade.values()));
		
//		getChildren().add(gradesComboBox);
		
		ToggleGroup buttGroup = new ToggleGroup();
		radioGrid = new GridPane();
			
		// For each Grade value, create a button
		for (int i = 0; i < Grade.values().length; i++) {
			RadioButton rButt = new RadioButton();
			rButt.setText(Grade.values()[i].name() + ((Grade.values()[i].getDescription() != null) ? String.format(" (%s)", Grade.values()[i].getDescription()) : ""));
			rButt.setToggleGroup(buttGroup);
			rButt.setUserData(Grade.values()[i]);
			radioGrid.add(rButt, i / 5, i % 5);
			
			if (ci.getCourse().getGrade() == Grade.values()[i]){
				rButt.setSelected(true);
			}
			
		}
		
		// Add it to the grid
		
//		radioGrid.add(child, 0, 0);
		
		radioGrid.setAlignment(Pos.CENTER);
		setCenter(radioGrid);
		
		/* The following listener code is derived from 
		 * https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm */
		buttGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle old_toggle, Toggle new_toggle) {
				if (buttGroup.getSelectedToggle() != null) {
					ci.setClassGrade((Grade) buttGroup.getSelectedToggle().getUserData());
				}                
			}
		});

	}

}
