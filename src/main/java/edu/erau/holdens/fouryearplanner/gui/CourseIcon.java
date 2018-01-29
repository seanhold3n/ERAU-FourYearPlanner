package edu.erau.holdens.fouryearplanner.gui;

import static edu.erau.holdens.fouryearplanner.Constants.COLOR_SOFT_GREEN;
import static edu.erau.holdens.fouryearplanner.Constants.COLOR_SOFT_YELLOW;
import static edu.erau.holdens.fouryearplanner.Constants.COL_WIDTH;
import static edu.erau.holdens.fouryearplanner.Constants.ROW_HEIGHT;
import static edu.erau.holdens.fouryearplanner.Constants.Y_OFFSET;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import edu.erau.holdens.fouryearplanner.model.Grade;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;
import edu.erau.holdens.fouryearplanner.model.StudentClass;

/** GUI representation of a Course object.
 * @author Adriana Strelow, Sean Holden<br>
 * {strelowa, holdens}@my.erau.edu
 */
public class CourseIcon extends StackPane { 

	/** Specifies the width and height of the CourseIcon */
	private static final int
	WIDTH = (int)(COL_WIDTH * .75),
	HEIGHT = (int)(ROW_HEIGHT * .75);
	
	/** The course represented by this CourseIcon */
	private StudentClass course;

	/** Background shape */
	private Rectangle rectBack;
	/** Course ID label */
	private Label lbCourseId;
	/** Credit hours label */
	private Label lbCredHr;
	/** Grade label */
	private Label lbGrade;
	
	/** Contains all content forward of the background rectangle */
	private BorderPane content;
	/** Contains the labels at the bottom */
	private GridPane labelsGrid;
	
	/** Context menu */	
	private CourseMenu menu;
	
	/** The great-great-great-grandparent {@linkplain FlowchartPane}. */
	private FlowchartPane flowchartPane;


	/** GUI representation of a StudentClass.
	 * @param c The StudentClass to represent
	 * @param flowchartPane The parent FlowChartPane in which this icon will be displayed
	 */
	public CourseIcon(StudentClass c, FlowchartPane flowchartPane){
		
		this.flowchartPane = flowchartPane;
		
		// Background
		rectBack = new Rectangle();
		rectBack.setWidth(WIDTH);
		rectBack.setHeight(HEIGHT);		
		rectBack.setArcHeight(HEIGHT*.2);
		rectBack.setArcWidth(WIDTH*.2);
		
		// Course ID label
		lbCourseId = new Label();
		lbCourseId.setMaxWidth(WIDTH);
		lbCourseId.setWrapText(true);
		lbCourseId.setAlignment(Pos.TOP_CENTER);
		
		// Bottom labels
		lbCredHr = new Label();
//		lbCredHr.setAlignment(Pos.BOTTOM_LEFT);
		lbGrade = new Label();
//		lbGrade.setAlignment(Pos.BOTTOM_RIGHT);
		labelsGrid = new GridPane();
		labelsGrid.setAlignment(Pos.BASELINE_CENTER);
		labelsGrid.add(lbCredHr, 0, 0);
		labelsGrid.add(lbGrade, 1, 0);
		labelsGrid.setMaxWidth(WIDTH);
		GridPane.setHalignment(lbCredHr, HPos.LEFT);
		GridPane.setHalignment(lbGrade, HPos.RIGHT);
		
		// Content pane
		content = new BorderPane();
		content.setCenter(lbCourseId);
		content.setBottom(labelsGrid);
		
		getChildren().addAll(rectBack, content);//, lbCredHr);
		
		
		// Set the course, and in turn, populate the label texts, set appropriate colors, etc
		setCourse(c);
		setClassGrade(c.getGrade());
		
		// Create context menu
		menu = new CourseMenu(this);
		
		setMouseHandlers();
	}

	/** Get the course object represented by this icon
	 * @return the course
	 */
	public StudentClass getCourse() {
		return course;
	}

	/** Set the course, and as a result, populate the label texts, set appropriate colors, etc.
	 * @param c The StudentClass represented by this CourseIcon
	 */
	private void setCourse(StudentClass c){
		// Only replace the old course if there is an old course to remove
		if (course != null){
			flowchartPane.getStudentPlan().remove(course);
			flowchartPane.getStudentPlan().add(c);
		}
		course = c;
		lbCourseId.setText(c.getId());
		lbCredHr.setText("  " + c.getCredits()+" CH, ");
	}
	
	public void setClassGrade(Grade g){
		course.setGrade(g);
		lbGrade.setText("Grade: " + g + "  ");
		rectBack.setFill(Color.web((g.getWeight() != Grade.NULL_GRADE && g != Grade.F) 
				? COLOR_SOFT_GREEN : COLOR_SOFT_YELLOW));
		// TODO update GPA
	}
	
	/** Shows information about the course in it's own window */
	public void showInfo(){
		// Add the info about this course to the side pane
		InfoStreamPane.getInstance().addCourseToStream(this.course);
	}
	
	/** Sets the actions to be performed when the CourseIcon is dragged
	 * @author <b>Sean Holden</b> (holdens@my.erau.edu), with some derivative code from <br>
	 * <b>jewelsea</b> on <a href="http://stackoverflow.com/questions/10682107/correct-way-to-move-a-node-by-dragging-in-javafx-2">StackOverflow</a> and <br>
	 * <b>ItachiUchiha</b> on <a href="http://stackoverflow.com/questions/22139615/dragging-buttons-in-javafx">StackOverflow</a>.
	 */
	private void setMouseHandlers(){
		
		/** Mouse delta */
		final Delta d = new Delta();
		
		setOnMouseEntered(e -> setCursor(Cursor.HAND));
		
		setOnMousePressed(e -> {
			
			if (e.isControlDown() || e.isSecondaryButtonDown()) { 
				menu.show(content, e.getScreenX(), e.getScreenY());
			}
			
			// Store the original x and y location
			d.x = getLayoutX() - e.getSceneX();
		    d.y = getLayoutY() - e.getSceneY();
		    
			setCursor(Cursor.MOVE);
		});
		
		setOnMouseDragged(e -> {
			setLayoutX(e.getSceneX() + d.x);
			setLayoutY(e.getSceneY() + d.y);
		});
				
		setOnMouseReleased(e -> {
			if (!e.isControlDown() && !e.isSecondaryButtonDown() && !e.isStillSincePress()) {
				// Snap the icon to the row and column
//				System.out.printf("Mouse released.  X = %.0f; Y = %.0f\n", e.getSceneX(), e.getSceneY());
				
				/* TODO this is basically a step function - way to improve?
				 * And this is getting into Indicator functions and set theory...
				 * treating rows and columns as sets and the courses as members of those
				 * sets might be a useful framework... */
				setLayoutX(COL_WIDTH * (int)(Math.max(e.getSceneX() + d.x, 0)  / COL_WIDTH));
				setLayoutY(ROW_HEIGHT * ((int)(Math.max(e.getSceneY() + d.y, 0) / ROW_HEIGHT) + Y_OFFSET));
				// Change the cursor back to the hand
				setCursor(Cursor.HAND);

				// Update the credit count and GPA
				flowchartPane.updateCreditCount();
				flowchartPane.updateGPAs();
			}			
		});
		
		setOnMouseClicked(e -> {
			if (e.isControlDown() || e.isSecondaryButtonDown() || e.isPopupTrigger()) { 
				menu.show(content, e.getScreenX(), e.getScreenY());
			}
		});
		
	}

	/** Records relative x and y coordinates for use in tracking drags. */
	private final class Delta{ double x, y;	}
	
	/** Context menu that appears for a course icon.
	 * @author Sean Holden (holdens@my.erau.edu)
	 */
	private class CourseMenu extends ContextMenu{
		
		private MenuItem miShowInfo;
		private MenuItem miRenameCourse;
		private MenuItem miSetGrade;
		
		public CourseMenu(CourseIcon courseIcon){
			miShowInfo = new MenuItem("Show more info");
			miRenameCourse = new MenuItem("Rename...");
			miSetGrade = new MenuItem("Change grade...");
			
			
			miShowInfo.setOnAction(e -> showInfo());
			
			miRenameCourse.setOnAction( e -> {
				/* TODO rework this sometime in the future */
				/* TODO make menu go away as soon as "Rename..." is clicked */
				String courseName = "";
				try{
					courseName = JOptionPane.showInputDialog("Enter new course name...");
					if (courseName != null){
						course = new StudentClass(MasterCourseMap.getInstance().get(courseName), course.getSemYearTuple());
						setCourse(course);
						setClassGrade(course.getGrade());
					}
				} catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Couldn't update the course!  Does " + courseName + " exist?", "Thing not found, guy!", JOptionPane.ERROR_MESSAGE);
				}
			});
			
			miSetGrade.setOnAction(e -> {
				GradeChangePane gcp = new GradeChangePane(courseIcon);
				Scene scene = new Scene(gcp, 550, 100);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Grade Change");
				stage.setResizable(false);
				stage.show();
			});
			
			miRenameCourse.setDisable(!course.isModdable());
			
			getItems().addAll(miShowInfo, miRenameCourse, miSetGrade);
		}
		
	}

}
