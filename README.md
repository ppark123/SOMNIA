# 2021 Sleep Journal: Somnia. 

## Software Description:
**Somnia** is a software  that allows users to log the number of hours they sleep each day. 
As well, users can record their sleep patterns and habits by answering questions related to their sleep for a given day.
This will allow them to manage their time and determine whether they should dedicate more or less of their time towards
sleeping. Recently, I have realized that the business of the current society often disturbs students and workers from 
finding an appropriate work-life balance; it often tends to skew towards giving up sleep for greater productivity. 
By making this software, I wish to remind users of the importance of keeping an appropriate and consistent sleep 
schedule. Somnia is for students, workers, people diagnosed with medical conditions related to sleep and any 
users who would like to monitor their sleeping habits or patterns to improve their well-being.

Some features of the software include:
- logging the number of hours a user sleeps or naps for a given day
- logging descriptions or patterns of their sleep by rating or answering given questions 
(eg. How many times did you wake up from your sleep during the night?)
- adding any additional notes to a sleep log in order to describe user's experience of their sleep 
- viewing existing sleep logs they have created already

## User Stories:
- User1: "As a user, I want to add sleep logs to My Sleep Journal."
- User2: "As a user, I want to be able to look back at the sleep logs I have made."
- User3: "As a user, I want to be able to record details of sleep hours and behaviours."
- User4: "As a user, I want to personalize My Sleep Journal by adding a username."
- User5: "As a user, I want to be able to either save the sleep logs I added to the journal in a file when I enter quit."
- User6: "As a user, I want to be given the option to reload a previously saved journal from a file."

## Instructions for Grader

- You can generate the first required event by pressing the create sleep log button in the second menu, which will lead 
the user to adding their sleep log to the journal.
- You can generate the second required event by deleting a pre-existing sleeplog by pressing enter after inputting the
desired date.
- You can locate my visual component by pressing the view journal button in the second menu.
- You can save the state of my application by pressing the main menu button, which will open a JOption pane that ask the
user if they would like to save their progress before leaving.
- You can reload the state of my application by pressing load journal in the main menu.

## Phase 4: Task 2

The logEvent class logs three events: 
- load
- create
- delete

Examples include:

Wed Aug 10 09:46:47 PDT 2022

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b123's journal: A sleep log for 6/7/22 has been loaded.

Wed Aug 10 09:48:22 PDT 2022

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b123's journal: A new sleep log for 6/7/22 has been added.

Wed Aug 10 09:49:01 PDT 2022

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b123's journal: The sleep log for 6/7/22 has been deleted.

Wed Aug 10 09:50:22 PDT 2022

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; bb1's journal: A new sleep log for 8/9/22 has been added.

***journal username inputted in description along with the date of the sleepLog and the time and action performed***

## Phase 4 : Task 3
![](../../Desktop/UML_Design_Diagram copy.jpg)
Improvements that could be made:
- an important improvement that could be made is in the JournalData class. The JournalData processes either a sleepLog 
or a journal to internally process the data of the given item. I believe it would be better to refactor and extract a 
new class to perform processes of internal data for the sleepLog and journal separately in their distinct classes to 
improve cohesion.
- Another improvement I could make is to reduce coupling. Once a user inputs a string for the chosenDate 
of their sleep log in the instance of GetDatePanel, the chosenDate is separated into month, day and year to check that 
the date is valid. It would be better to make a new class called Date and have separate String fields for the month, 
day and year so that there is less code separating and processing the string. As well, this would improve cohesion in 
my project as the processing for the date can be done in this class instead of in the JournalData.
- Although I did try my best in reducing redundancy in my code, it would be better to create more helpers in my code
eg. for getting integerMonth, integerDay etc.
- Without the constraints of line limits, I would delete some helper functions in the classes of the ui package whose 
functionality was primarily for reducing the line count.
- I would also add custom exceptions in my ui classes to throw more specific errors of answer inputs instead of having
general errors. For instance, in my answer checker, I used the NumberFormatException to throw exceptions for invalid 
integers and empty answers. It would be better to have specific exceptions specifying what error the inputted answer is
throwing.
- As seen in the diagram, both FillLogPanel and GetDatePanel have an association to journal and to journalData. 
Since there is already an association from journalData to journal, it would be better to remove both associations from the 
Panel classes to journal and instead add a method or call the journal field in the journalData to get access to the 
journal they are currently processing (eg. getJournal() in journalData).
