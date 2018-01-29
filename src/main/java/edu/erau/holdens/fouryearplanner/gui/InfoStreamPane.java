package edu.erau.holdens.fouryearplanner.gui;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import edu.erau.holdens.fouryearplanner.model.Course;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;

/** Used to display course information upon request in the GUI.  THis class contains one
 * static instance of this class that all members of the project must use.  This uses the
 * same design philosophy as the {@link MasterCourseMap} class.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class InfoStreamPane extends BorderPane {
	
	private ScrollPane streamPane;
	// underlying vertical box
	private VBox streamContent;
	
	private Label header;
	
	/** Button to clear the contents of the stream */
	private Button btClearAll;
	
	/** Sets whether the pane is collapsed or not */
	private boolean isCollapsed;
	
	/** The singular InfoStreamPane used in the program */
	private static InfoStreamPane instance;
	
	private InfoStreamPane(){
		
		header = new Label("Course Information Stream");
		// TODO not aligned properly
		header.setAlignment(Pos.BASELINE_CENTER);
		
		streamContent = new VBox();
		streamContent.setPrefWidth(300);
		
		streamPane = new ScrollPane();		
		streamPane.setContent(streamContent);
		streamPane.setPrefWidth(300);
		streamPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		btClearAll = new Button("Clear all");
		btClearAll.setOnAction(e -> streamContent.getChildren().clear());
		
		setTop(header);
		setCenter(streamPane);
		setBottom(btClearAll);
		
		header.setOnMouseClicked(e -> {
			setCollapsed(!isCollapsed);
		});
		
	}
	
	public static InfoStreamPane getInstance(){
		if (instance == null){
			instance = new InfoStreamPane();
		}
		return instance;
	}
	
	/** Adds information about a course to the top of the stream.
	 * @param c The course to add to the stream.
	 */
	public void addCourseToStream(Course c){
		streamContent.getChildren().add(0, new CourseInfoPane(c));
	}
	
	/**
	 * @return the isCollapsed
	 */
	public boolean isCollapsed() {
		return isCollapsed;
	}

	/**
	 * @param isCollapsed the isCollapsed to set
	 */
	public void setCollapsed(boolean isCollapsed) {
		this.isCollapsed = isCollapsed;
		if (isCollapsed){
			// Rotate header
			header.getTransforms().add(new Rotate(90, 0, 0));
			header.getTransforms().add(new Translate(0, -header.getHeight()));
			setCenter(null);
			setBottom(null);
		}
		else {
			header.getTransforms().clear();
			setCenter(streamPane);
			setBottom(btClearAll);
		}
	}

	/** Contains the information for a single course.
	 * @author Sean Holden (holdens@my.erau.edu)
	 */
	private static class CourseInfoPane extends BorderPane{
	
		public CourseInfoPane(Course course){
			//description of each course icon contains the course name,
			//pre requisites and the amount of credits. 
			final String description = String.format("%s\n%s\n\n%s\n\nPre Requisites: %s\nCredits: %d", 
					course.getId(), course.getName(), course.getDescription(), course.getPreReqList().toString(), course.getCredits());

			// Create the text label
			Label aboutLabel = new Label();
			aboutLabel.setWrapText(true);
			aboutLabel.setTextAlignment(TextAlignment.CENTER);
			aboutLabel.setFont(Font.font("Arial", 14));
			aboutLabel.setText(description);
			aboutLabel.setPadding(new Insets(10));

			// Set the color to the next color generated from the ColorList
			setBackground(new Background(new BackgroundFill(ColorList.getNext(), null, null)));

			// Create and display said the aforementioned pane in a new stage 	
			setCenter(aboutLabel);
		
		}
		
		/** Class for generating various colors for each CourseInfoPane.
		 * @author Sean Holden (holdens@my.erau.edu)
		 */
		private static class ColorList{
			
			/** The color preferences */
			private static ArrayList<Color> colorPrefs = new ArrayList<Color>(
					Arrays.asList(Color.BEIGE, Color.BISQUE));
			
			private static int count = -1;
			
			public static Color getNext(){
				count++;
				return colorPrefs.get(count % colorPrefs.size());
			}
			
		}
		
	}

}
