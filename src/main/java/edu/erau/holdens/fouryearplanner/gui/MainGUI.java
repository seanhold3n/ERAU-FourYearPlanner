package edu.erau.holdens.fouryearplanner.gui;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import edu.erau.holdens.fouryearplanner.Constants;
import edu.erau.holdens.fouryearplanner.io.CourseListParser;
import edu.erau.holdens.fouryearplanner.io.StudentPlanParser;
import edu.erau.holdens.fouryearplanner.model.CourseList;
import edu.erau.holdens.fouryearplanner.model.GPACalculator;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

/** Creates a Pane inside of a BorderPane with nodes that are organized into
 * unofficial columns. The header of each column is the name of each semester. 
 * Within the rows are course icons placed in each semester column. 
 * The bottom of each column contains the amount of credits within the column.
 * @author Adriana Strelow, Sean Holden<br>
 * {strelowa, holdens}@my.erau.edu
**/
public class MainGUI extends Application {
	
	/** Main menu bar */
	private MainMenuBar menuBar;
	/** Contains everything relating to the courseicons */
	private FlowchartPane flowchartPane;
	/** Contains info about courses */
	private InfoStreamPane infoStream;
	/** Contains everything within the stage */
	private BorderPane contentPane;
	/** Contains name, degree program, total credits, and CGPA */
	private Label bottomLabel;
	
	/** The currently open plan */
	private File openPlanFile = new File("src/main/resources/students/newStudent.xml");
	
	/** The stage of the Application */
	private Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {

		
		// Populate the master course list
		/* Note: This is already done on the first invocation of MasterCourseMap.getInstance,
		 * but this ensures that all courses are loaded. */
		CourseListParser parser = new CourseListParser();
		CourseList courses;
		
		final String[] courseFiles = new String[]{
				"courses.xml", "courses_abstract.xml", "courses_af.xml", 
				"courses_special.xml"
		};
		
		for (final String file : courseFiles){
			try{
				System.out.println("[MainGUI] Parsing " + file + "...");
				courses = parser.parse("src/main/resources/courses/" + file);
				MasterCourseMap.getInstance().putIfAbsent(courses);
			} catch (Exception e){
				System.err.println("[MainGUI] Unable to add courses from " + file);
			}
		}
		
		// Update the map
		MasterCourseMap.writeToFile();
		
		
		// Create BorderPane
		contentPane = new BorderPane();
		
		// Create stuff at the bottom
		bottomLabel = new Label();
		bottomLabel.setFont(Font.font("Arial", 24));
		HBox bottomLabelBox = new HBox();
		bottomLabelBox.setPadding(new Insets(15, 12, 15, 12));
		bottomLabelBox.setSpacing(10);
		bottomLabelBox.setStyle("-fx-background-color: #e0ffff;"); //light blue ish 
		bottomLabelBox.getChildren().addAll(bottomLabel); //add the credit label to the hbox
		contentPane.setBottom(bottomLabelBox);
		
		
		// Set menubar and infostream
		menuBar = new MainMenuBar(this);		
		infoStream = InfoStreamPane.getInstance();
		contentPane.setTop(menuBar);
		contentPane.setRight(infoStream);
		
		// Create scene
		Scene scene = new Scene(contentPane);
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.setTitle(Constants.PROGRAM_TITLE);
		stage.show();

		// Ask the user to open a plan
		menuBar.new OpenPlanHandler().handle(null);
		
	}
	
	
	public static void main(String[] args) { launch(args); }
	
	
	/** Get the FlowchartPane node of the GUI
	 * @return the flowchartPane
	 */
	public FlowchartPane getFlowchartPane() {
		return flowchartPane;
	}


	public File getOpenPlan() {
		return openPlanFile;
	}

	public Stage getStage() {
		return stage;
	}

	public StudentPlan getStudentPlan(){
		return flowchartPane.getStudentPlan();
	}

	public void setOpenPlan(File openPlan) {
		this.openPlanFile = openPlan;
	}

	public void updateFlowchart(String s) throws Exception {
		updateFlowchart(new File(s));
	}
	
	public void updateFlowchart(File f) throws Exception {
		openPlanFile = f;
		updateFlowchart(new StudentPlanParser().parse(f));
	}
	
	public void updateFlowchart(StudentPlan plan){
		flowchartPane = new FlowchartPane(this, plan);
		contentPane.setCenter(flowchartPane);
		updateLabel(plan);
	}
	
	public void updateLabel(StudentPlan plan){
		bottomLabel.setText(String.format("  %s    |    %s    |    Total Credits: %d    |    CGPA: %1.3f", 
				plan.getStudentName(), plan.getProgramName(), plan.getTotalCredits(), GPACalculator.calculateGPA(plan)));
	}

}
