# ERAU-FourYearPlanner
A utility for students and advisors alike to track and plan a student's academic progress.

## Program Call Hierarchy
- Program begins execution in the `MainGUI` class.  This class starts by loading courses into memory, both by reading `coursesmap.dat` (serialized form of `MasterCourseMap`, which maps course ID to `Course` objects) and by importing any additional courses from various XML files.  Note: loading of additional courses only needs to be done once, because they are immediately added to the `MasterCourseMap` for use in future launches of the program.

- `MainGUI` then constructs GUI components.  One of those is the `FlowchartPane`, which is populated from a predefined file (`MainGUI.openPlanFile` field) using the `updateFlowchart` method.  This method parses the XML file into a `StudentPlan` object and creates the Flowchart based on that.

- The `FlowchartPane` class contains a few items.  In addition to the `CourseIcons` (which represent a single `StudentClass`), the flowchart also draws lines to connect prerequisite courses and rectangles to track semesters.  These rectangles are linked to corresponding `SemesterYearTuple` objects using the `userData` methods.  When a course is moved, the program determines the new semester for a course by seeing which rectangle it intersects.  The current version of the code does this check for all rectangles and all `CourseIcon` objects when one is dragged; naturally, this can be optimized if so desired.  You may view the semester rectangles by changing the value of `Constants.SHOW_RECTS` to `true`.
