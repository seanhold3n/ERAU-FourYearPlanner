package edu.erau.holdens.fouryearplanner.gui;

import java.awt.image.BufferedImage;
import java.io.File;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import edu.erau.holdens.fouryearplanner.Constants;
import edu.erau.holdens.fouryearplanner.model.MasterCourseMap;
import edu.erau.holdens.fouryearplanner.model.Semester;
import edu.erau.holdens.fouryearplanner.model.SemesterYearTuple;
import edu.erau.holdens.fouryearplanner.model.StudentClass;
import edu.erau.holdens.fouryearplanner.model.StudentPlan;

/** The menu bar for the MainGUI.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public class MainMenuBar extends MenuBar {
	
	private MainGUI parentGUI;
	
	private Menu mFile;
	private MenuItem miCreatePlan;
	private MenuItem miOpenPlan;
	private MenuItem miSavePlan;
	private MenuItem miSavePlanAs;
	private MenuItem miExit;
	
	private Menu mFlowchart;
	private MenuItem miAddClasses;
	private MenuItem miAddSemester;
	
	private Menu mEx;
	private MenuItem miExportImage;
	
	private Menu mHelp;
	private MenuItem miAbout;
	
	/** Constructs a new MainMenuBar.
	 * @param parentGUI The MainGUI that will contain this menubar
	 */
	public MainMenuBar(MainGUI parentGUI){
		super();
		
		this.parentGUI = parentGUI;
		
		// Menu objects creation
		mFile = new Menu("File");
		miCreatePlan = new MenuItem("Create plan from existing program...");
		miOpenPlan = new MenuItem("Open student plan...");
		miSavePlan = new MenuItem("Save student plan");
		miSavePlanAs = new MenuItem("Save student plan as...");
		miExit = new MenuItem("Exit");

		mFlowchart = new Menu("Flowchart");
		miAddClasses = new MenuItem("Add classes...");
		miAddSemester = new MenuItem("Add semester...");
		
		mEx = new Menu("Experimental");
		miExportImage = new MenuItem("Export as Image");
		
		mHelp = new Menu("Help");
		miAbout = new MenuItem("About");
		
		// Add stuff
		mFile.getItems().addAll(miCreatePlan, miOpenPlan, miSavePlan, miSavePlanAs, miExit);
		mFlowchart.getItems().addAll(miAddClasses, miAddSemester);
		mEx.getItems().addAll(miExportImage);
		mHelp.getItems().addAll(miAbout);
		this.getMenus().addAll(mFile, mFlowchart, mEx, mHelp);
		
		
		/* File Events */
		// Create plan
		miCreatePlan.setOnAction(new CreatePlanHandler());
		// Open plan
		miOpenPlan.setOnAction(new OpenPlanHandler());
		// Save plan
		miSavePlan.setOnAction(e -> {
			try {
				parentGUI.getStudentPlan().writeAsXmlTo(parentGUI.getOpenPlan());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error saving XML!", "Save Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		// Save As
		miSavePlanAs.setOnAction(new SaveAsHandler());
		// Exit
		miExit.setOnAction(e -> Platform.exit());
		
		
		/** Flowchart Events*/
		miAddClasses.setOnAction(new AddClassHandler());
		miAddSemester.setOnAction(new AddSemesterHandler());
		
		/** Experimental section */
		miExportImage.setOnAction(new ExportImageHandler());
		
		/** Help section */
		miAbout.setOnAction(new ShowHelpHandler());
		
	}
	
	private class CreatePlanHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Degree Program");
			chooser.getExtensionFilters().add(
					new ExtensionFilter("Degree Program XML (*.xml)", "*.xml"));
			chooser.setInitialDirectory(new File(getClass().getClassLoader().getResource("").getFile()));
			File f = chooser.showOpenDialog(parentGUI.getStage());
			if (f != null){
				try{
					parentGUI.updateFlowchart(f);
					miSavePlan.setDisable(true);
				} catch (Exception ex){
					// TODO again, JOptionPane.. bleh
					JOptionPane.showMessageDialog(null, "Error opening the file!", "Open Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		}
	}
	
	public class OpenPlanHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Student Plan");
			chooser.getExtensionFilters().add(new ExtensionFilter("Student Plan XML (*.xml)", "*.xml"));
			try{
//				chooser.setInitialDirectory(new File(getClass().getClassLoader().getResource("/..").getFile()));
				// Source: http://stackoverflow.com/a/21132382
				chooser.setInitialDirectory(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsoluteFile());
			} catch (Exception e){
				System.err.println("[MainMenuBar] Error setting custom initial directory");
			}
			File f = chooser.showOpenDialog(parentGUI.getStage());
			if (f != null){
				try{
					parentGUI.updateFlowchart(f);
				} catch (Exception ex){
					// TODO again, JOptionPane.. bleh
					JOptionPane.showMessageDialog(null, "Error opening the file!", "Open Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class SaveAsHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Student Plan");
			chooser.getExtensionFilters().addAll(
					new ExtensionFilter("Student Plan XML (*.xml)", "*.xml"),
					new ExtensionFilter("Microsoft Excel Spreadsheet (*.xlsx)", "*.xlsx"));
			chooser.setInitialDirectory(parentGUI.getOpenPlan().getParentFile());
			File f = chooser.showSaveDialog(parentGUI.getStage());
			if (f != null){
				try {
					parentGUI.getStudentPlan().writeAsXmlTo(f);
					miSavePlan.setDisable(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error saving XML!", "Save Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
	
	private class ExportImageHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			BorderPane flowchart = parentGUI.getFlowchartPane().getContentPane();
			WritableImage image = flowchart.snapshot(null, null);
			BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
			try {
				String filename = "flowchart.png";
				ImageIO.write(bImage, "png", new File(filename));
				JOptionPane.showMessageDialog(null, "Flowchart written to " + filename);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Error while writing to file");
				e1.printStackTrace();
			}
		}
	}


	private class AddClassHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// Java Swing, because I'm lazy
			String courseID = JOptionPane.showInputDialog("Enter class ID", "CS 225");
			if (courseID != null){
				if (MasterCourseMap.getInstance().containsKey(courseID)){
					// Add it to the chart
					StudentPlan sp = parentGUI.getFlowchartPane().getStudentPlan();
					sp.add(new StudentClass(courseID, sp.getSemYearTuples().last()));
					parentGUI.updateFlowchart(sp);
				}
				else {
					JOptionPane.showMessageDialog(null, "ID not found in the course map!");
				}
			}
		}
	}	


	private class AddSemesterHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			// AddSemesterPane
			// TODO make "supported semester codes" dynamically update based on enum (or add a note somewhere else about it)
			String semesterID = JOptionPane.showInputDialog("Enter a semester to add (e.g. \"FA01\", or \"Transfer\").\nSupported semester codes are FA (Fall), SP (Spring), SA (Summer A), and SB (Summer B)", "FA01");
			if (semesterID.equalsIgnoreCase("transfer")){
				// Update plan with FA 00
				StudentPlan sp = parentGUI.getFlowchartPane().getStudentPlan();
				sp.getSemYearTuples().add(new SemesterYearTuple(Semester.FA, 0));
				parentGUI.updateFlowchart(sp);
			}
			else{
				try{
					// Split the user string
					String semSubStr = semesterID.substring(0, 2);
					String yearSubStr = semesterID.substring(2);
					
					// Parse components
					Semester sem = Semester.valueOf(semSubStr);
					int year = Integer.parseInt(yearSubStr);
					
					// Update plan
					StudentPlan sp = parentGUI.getFlowchartPane().getStudentPlan();
					sp.getSemYearTuples().add(new SemesterYearTuple(sem, year));
					parentGUI.updateFlowchart(sp);
				} catch (Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Something's wrong with your format, dawg!");
				}
			}			
		}
	}
	
	private class ShowHelpHandler implements EventHandler<ActionEvent>{

		@Override
		/** Lifted directly from my lab code */
		public void handle(ActionEvent arg0) {
			/** Shows information about the program in it's own window */
			final String aboutText = Constants.PROGRAM_TITLE + "\n\nThis program is for use in academic schedule planning for the students of "
					+ "Embry-Riddle Aeronautical University.  This program is not an official University product, and is provided \"as-is\".  "
					+ "The developer is not responsible for any damages caused by this program (ya know, whatever those might be).\n\n"
					+ "If you have any questions or feedback (especially since this is in beta), please email the developer, Sean Holden (holdens@my.erau.edu).  "
					+ "No rights reserved.  Void where prohibited.  Batteries not included.";

			// Create the text label
			Label aboutLabel = new Label();
			aboutLabel.setWrapText(true);
			aboutLabel.setTextAlignment(TextAlignment.LEFT);
			aboutLabel.setFont(Font.font("Comic Sans MS", 14));
			aboutLabel.setText(aboutText);

			// Add the label to a StackPane
			StackPane pane = new StackPane();
			pane.setPadding(new Insets(15));
			pane.getChildren().add(aboutLabel);

			// Create and display said the aforementioned pane in a new stage 	
			Scene scene = new Scene(pane, 600, 250);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("About this program");
			stage.setResizable(false);
			stage.show();

		}
		
	}

}
