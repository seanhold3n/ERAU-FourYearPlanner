# ERAU-FourYearPlanner
A utility for students and advisors alike to track and plan a student's academic progress.

## Program Call Hierarchy
- Program begins execution in the `MainGUI` class.  This class starts by loading courses into memory, both by reading `coursesmap.dat` (serialized form of `MasterCourseMap`, which maps course ID to `Course` objects) and by importing any additional courses from various XML files.  Note: loading of additional courses only needs to be done once, because they are immediately added to the `MasterCourseMap` for use in future launches of the program.

- `MainGUI` then constructs GUI components.  One of those is the `FlowchartPane`, which is populated from a predefined file (`MainGUI.openPlanFile` field) using the `updateFlowchart` method.  This method parses the XML file into a `StudentPlan` object and creates the Flowchart based on that.

- The `FlowchartPane` class contains a few items.  In addition to the `CourseIcons` (which represent a single `StudentClass`), the flowchart also draws lines to connect prerequisite courses and rectangles to track semesters.  These rectangles are linked to corresponding `SemesterYearTuple` objects using the `userData` methods.  When a course is moved, the program determines the new semester for a course by seeing which rectangle it intersects.  The current version of the code does this check for all rectangles and all `CourseIcon` objects when one is dragged; naturally, this can be optimized if so desired.  You may view the semester rectangles by changing the value of `Constants.SHOW_RECTS` to `true`.

## Task List
### Fixes to current code
- Fix bug where credit counts are incorrect when flowchart is first loaded
- First-time open uses the students directory, not the one containing the source code

### New features - high priority
- Create XML files for other degree programs.
- Make CourseIcons non-stackable
- Pre-req lines only drawn to most recent taking of course
- User can delete classes using the GUI
- User can delete semesters using the GUI
- Prevent course from being dragged behind a pre-requisite
- Make CourseIcons more aesthetic
- Make course info stream collapsable
- Failed but retaken courses should appear as green
- Change student name from GUI
- Update CGPA automatically
- Anything else represented by a TODO tag in the code

### New features - low priority
- Export to Excel (and import existing plans?)
- Enforce course offerings (only FA, for example) -> make color red for error?  Have little caution sign thing?
- Degree progress auditor
- Create DTD for various XML files
- Implement proper logging framework
- Documentation: User's Guide
- Code clean-up: identify and remove deprecated classes, methods, and other files
- Map/course explorer
- Drop shadow preview when moving courses

### Complete
- Shorten semester rectangles to stop scrollpane from getting really large
- Relocate semester credit counts
- Remove ability to toggle if a course is taken (deprecated - replaced by entering a grade)
- Display GPA information
- Export flowchart as picture
- Fixed: WHY ARE STUDENT CLASSES BEING DUPLICATED UPON SAVE?!
- Add user information (username, degree program, other?) to bottom information bar
- Add Cumulative GPA to bottom information bar
- Add Student Name and name of plan to XML
- Add ability to insert grade for courses taken.
- User can add classes to the plan via the GUI
- User can add semesters using the GUI
- Add flag to allow some course names to be editable, while others (required courses) cannot be edited.
- When the program starts, ask the user which XML plan to open.
- Make right-click context menus work instead of having to use my bizzare keyboard shortcut
- Add "About" message
- Make dragging less derpy
- Move tests into proper test directory (src\test\java)

## Pre-deployment checklist
- All fixes fixed
- All features featured
- All tests tested
