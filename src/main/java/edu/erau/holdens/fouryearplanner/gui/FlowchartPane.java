package edu.erau.holdens.fouryearplanner.gui;

import static edu.erau.holdens.fouryearplanner.Constants.COL_WIDTH;
import static edu.erau.holdens.fouryearplanner.Constants.ROW_HEIGHT;
import static edu.erau.holdens.fouryearplanner.Constants.SHOW_RECTS;
import static edu.erau.holdens.fouryearplanner.Constants.Y_OFFSET;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import edu.erau.holdens.fouryearplanner.model.GPACalculator;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

public class FlowchartPane extends ScrollPane {
	
	/** The MainGUI containing this pane*/
	private MainGUI parentGUI;
	
	/** Contains all of the content of this ScrollPane */
	private BorderPane contentPane;
	/** Contains the semester labels that are displayed at the top */
	private Pane semesterLabelsBox;
	/** Contains all of the CourseIcons, semester rectangles, and credits labels */
	private Pane coursesPane;
	
	/** A list of all of the course icons in the pane */
	private List<CourseIcon> courseIconList;
	
	/** HashMap of all SemesterYearTupes to semester labels */
	private HashMap<SemesterYearTuple, Label> termXheader = new HashMap<>();
	/** HashMap of all SemesterYearTupes to credits labels */
	private HashMap<SemesterYearTuple, Label> termXcredits = new HashMap<>();
	/** HashMap of all SemesterYearTupes to GPA labels */
	private HashMap<SemesterYearTuple, Label> termXgpas = new HashMap<>();
	/** HashMap of all SemesterYearTupes to rectangles */
	private HashMap<SemesterYearTuple, Rectangle> termXrects = new HashMap<>();
	
	/** The StudentPlan off of which this flowchart is made */
	private StudentPlan studentPlan;
	
	public FlowchartPane(MainGUI parentGUI, StudentPlan plan){
		super();
		
		this.parentGUI = parentGUI;
		this.studentPlan = plan;
		
		contentPane = new BorderPane();
		semesterLabelsBox = new Pane();
		coursesPane = new Pane();

		courseIconList = new ArrayList<CourseIcon>();


		//for each semester, add the headers and courses
		int i = 0;
		for (SemesterYearTuple syt : plan.getSemYearTuples()) {

			// Add headers
			Label header = new Label(syt.toString());
			header.setFont(Font.font("Arial Bold", FontWeight.BOLD , 20));
			//align the headers inside the column. 
			header.setLayoutX(i*COL_WIDTH);
			header.setLayoutY(0);
			semesterLabelsBox.getChildren().add(header);
			termXheader.put(syt, header);
			

			// Get the courses
			List<StudentClass> cList = plan.getCoursesForSemester(syt);

			// Add them to the grid
			for (int j = 0; j < cList.size(); j++) {
				CourseIcon sci = new CourseIcon(cList.get(j), this);
				sci.setLayoutX(i*COL_WIDTH);
				sci.setLayoutY((j+Y_OFFSET)*ROW_HEIGHT);
				courseIconList.add(sci);
			}
			
			// Invisible semester tracking rectangle
			Rectangle semester = new Rectangle();
			semester.setWidth(COL_WIDTH/5);
			semester.heightProperty().bind(coursesPane.heightProperty());
			semester.setX((i+.3)*COL_WIDTH);
			semester.setY(0);
			semester.setFill(SHOW_RECTS ? Color.AQUAMARINE : Color.TRANSPARENT);
			semester.setUserData(syt);
			coursesPane.getChildren().add(semester);
			termXrects.put(syt, semester);
			
			
			// Add total credit hours for each semester
			//place at the bottom of each column
//			Label creditsLabel = new Label("Credits: " + cList.getTotalCredits());
			Label creditsLabel = new Label();
			creditsLabel.setLayoutX(i*COL_WIDTH);
//			creditsLabel.layoutYProperty().bind(this.viewportBoundsProperty().);
			creditsLabel.setLayoutY(0*ROW_HEIGHT);
			coursesPane.getChildren().add(creditsLabel);
//			GridPane.setHalignment(header, HPos.CENTER);
			termXcredits.put(syt, creditsLabel);
			
			// And the term GPA
			Label gpaLabel = new Label();
			gpaLabel.setLayoutX(i*COL_WIDTH);
//			gpaLabel.layoutYProperty().bind(this.viewportBoundsProperty().);
			gpaLabel.setLayoutY(0.25*ROW_HEIGHT);
			coursesPane.getChildren().add(gpaLabel);
//			GridPane.setHalignment(gpaLabel, HPos.CENTER);
			termXgpas.put(syt, gpaLabel);
			
			i++;
		}
		
		
		createPreReqLines();
	
		// Now add the course icons to be on top of everything
		coursesPane.getChildren().addAll(courseIconList);
		
		contentPane.setTop(semesterLabelsBox);
		contentPane.setCenter(coursesPane);
		
		// Add the contentPane to this
		setContent(contentPane);
		
		updateCreditCount();
		updateGPAs();
		
	}
	
	private void createPreReqLines(){
		
		/** Used to offset the line from the top-left of the CourseIcons
		 * to land the endpoints approximately in the center */
		final int LINE_OFFSET_X = 50, LINE_OFFSET_Y = 20;
		
		// Draw lines for prereqs
		// TODO Pretty much making a graph
		// Get list of all courseicons
		for (CourseIcon c : courseIconList){

				// Get pre-reqs
				List<String> prereqs = c.getCourse().getPreReqList();

				// Get corresponding CourseIcons
				for (String prereq : prereqs){
					// TODO O(n^2+!!)
					// TODO map courseicons to Terms; start at a given term and go backwards
					
					for (int i = courseIconList.size() - 1; i > 0; i--){
//					for (CourseIcon ci : courseIconList){
						CourseIcon ci = courseIconList.get(i);
						if (prereq.equals(ci.getCourse().getId())){
							// Draw lines between them
							Line l = new Line();
							l.startXProperty().bind(ci.layoutXProperty().add(LINE_OFFSET_X));
							l.startYProperty().bind(ci.layoutYProperty().add(LINE_OFFSET_Y));
							l.endXProperty().bind(c.layoutXProperty().add(LINE_OFFSET_X));
							l.endYProperty().bind(c.layoutYProperty().add(LINE_OFFSET_Y));
							
							// Add the line to the pane
							coursesPane.getChildren().add(l);	
							
							// Only do it once
							break;
					}
				}
			}
		}
	}
	
	/**
	 * @return the contentPane
	 */
	public BorderPane getContentPane() {
		return contentPane;
	}

	/**
	 * @return The StudentPlan off of which this flowchart is made 
	 */
	public StudentPlan getStudentPlan() {
		return studentPlan;
	}

	/** Update the credit count shown on all of the labels */
	public void updateCreditCount(){
		
		/** Reference to the current rectangle */
		Rectangle semBoundsRect;
		
		for (SemesterYearTuple syt : studentPlan.getSemYearTuples()){
			int totalCredits = 0;
			semBoundsRect = termXrects.get(syt);
			
			for (CourseIcon c : courseIconList){
				if (semBoundsRect.getBoundsInParent().intersects(c.getBoundsInParent())){
					totalCredits += c.getCourse().getCredits();
					c.getCourse().setSemesterYearTuple(syt);
				}
			}
			
			termXcredits.get(syt).setText("Credits: " + totalCredits);
		}
	}
	
	/** Updates the GPA labels */
	public void updateGPAs(){
		
		List<StudentClass> classes;
		double gpa;
		
		// For each semester...
		for (SemesterYearTuple syt : studentPlan.getSemYearTuples()){
			// Get the courses
			classes = studentPlan.getCoursesForSemester(syt);
			// Calculate the GPA
			gpa = GPACalculator.calculateGPA(classes);
			// Get the label and update the text
			termXgpas.get(syt).setText(String.format("GPA: %1.3f", gpa));
		}
		
		parentGUI.updateLabel(studentPlan);
		
	}

}
