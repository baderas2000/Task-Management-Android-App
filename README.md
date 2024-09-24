# Task Management Android App

The goal of this project is to create a task management Android application to manage and view multiple task types. Users should be able to create their tasks and keep track of them. Your assignment is to collaboratively develop a high-quality software solution that fulfills the requirements listed below. Focus on how to write and structure your source code cleanly, on correctly and reasonably applying design patterns, and on how to determine, track, and gain insights from code metrics. Java is the accepted language of the project.

The application design is using the Model-View-ViewModel (MVVM) software design
pattern that is structured to separate program logic and user interface controls.
The Model layer is responsible for all business logic of the application and provides
all the necessary data.
The View layer represents functionality to a user.
The ViewModel layer is an abstraction of the view exposing public properties and
commands.

# Implemented Patterns
1. Factory Pattern is used in the creation of the Subclasses for the Task class.
And also this pattern is used in order to create the Notification class. As we
have two notification types Email and Popup, sending notifications in these
classes will be implemented in different ways.
2. Proxy Pattern is implemented with instance ProxyTask which will hold all of the
created Tasks,control their creation, access, storing and various manipulations
of the data.
3. Composite Pattern is used to create a system, which contains Tasks, that
contains Subtasks, Attachment etc.
4. Strategy Pattern is suitable for deletion of parent and/or subtasks (e.g. how to
handle the deletion of a task that contains subtasks).
5. Observer Pattern is used for notification sending related functionality. This
pattern provides a solution for the case, when application user should be
notified, when a task is created, updated, deleted and when an appointment is
coming up.
6. Decorator Pattern is used to decorate Email and Pop-up Notifications
according to the action.
7. Facade Pattern provides access to CalendarViewImpl and ListViewImpl
classes. It provides a simplified interface to a complex system of sub-classes.
8. Template Method Pattern defines the steps for editing calendar and list views,
the steps are overridden in subclasses for each view.
9. Adapter Pattern is used to work with and parse JSON and XML files.
10.Iterator Pattern is used to iterate the task list that is to be exported or imported
from the database.

# Member 5 Responsibility (Sofiia Badera)
Task management application has to support import/export of tasks. The export
function supports two serialization file formats JSON and XML. The import function
reads task list from a serialization file and adds them to the database.
Design Patterns: Iterator, Adapter.

For further detail see the final report.
