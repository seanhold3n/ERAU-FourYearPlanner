package edu.erau.holdens.fouryearplanner;

import edu.erau.holdens.fouryearplanner.gui.FlowchartPane;

/** Container class for all global constants.
 * @author Sean Holden (holdens@my.erau.edu)
 */
public final class Constants {
	
	public static final String PROGRAM_NAME = "Schedule Planner";
	public static final String PROGRAM_VERSION = "v1.0-beta";
	public static final String PROGRAM_DATE = "2015-11-01";
	public static final String PROGRAM_TITLE = PROGRAM_NAME + " " + PROGRAM_VERSION;
	
	/** Main website */
	public static final String PROGRAM_WEBSITE = "http://pages.erau.edu/~holdens/fyp/";
	public static final String UPDATE_ENDPOINT = PROGRAM_WEBSITE + "update.json";
		
	/** Option to show or hide the semester rectangles in the {@link FlowchartPane}. */
	public static final boolean SHOW_RECTS = false;
	
	/** The hex value of a soft red color. */
	public static final String COLOR_SOFT_RED = "#e06666";
	/** The hex value of a soft red color. */
	public static final String COLOR_SOFT_YELLOW = "#ffd966";
	/** The hex value of a soft red color. */
	public static final String COLOR_SOFT_GREEN = "#93c47d";
	
	/** Width of each semester column in pixels. */
	public static final int COL_WIDTH = 150;
	/** Height of each semester row in pixels. */
	public static final int ROW_HEIGHT = 90;
	/** The y-offset for a CourseIcon as a multiple of the row height.
	 * The purpose of this constant is to give an offset amount of room at the top
	 * of a column for the credit count.*/
	public static final double Y_OFFSET = 0.5;
	
}
