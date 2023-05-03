package ui;

import model.Journal;
import model.SleepLog;

import javax.swing.*;
import java.awt.*;

//A panel that fills out a new sleep log of given journal
public class FillLogPanel extends JPanel {
    private static final String qn1Text = "1. From a scale of 1-4 how well did you sleep?";
    private static final String qn2Text = "2. What time do you remember falling asleep?";
    private static final String qn3Text = "3. How many times during the night did you wake up?";
    private static final String qn4Text = "4. How many naps did you take during the day?";
    private static final String qn5Text = "5. Did you drink any caffeine or alcohol on this day?";
    private static final String noteText = "Add any other notes about your sleep or enter N/A if none are needed: ";

    private JournalData journalData;
    private JournalFrame journalFrame;
    private SleepLog sleepLog;
    private Journal journal;

    JTextField sleepField;
    JTextField wakeField;
    JTextField ans1;
    JTextField ans2;
    JTextField ans3;
    JTextField ans4;
    JTextField ans5;
    JTextField noteField;

    JLabel sleepLogDate;
    JLabel enterSleepLabel;
    JLabel noticeLabel;
    JLabel enterWakeLabel;
    JLabel qnsLabel;
    JLabel qn1;
    JLabel qn2;
    JLabel qn3;
    JLabel qn4;
    JLabel qn5;
    JLabel clarification;
    JLabel note;
    JButton next;

    // EFFECTS: creates a new panel of a given journal and lays it out on a journal frame.
    public FillLogPanel(SleepLog sleepLog, Journal journal, JournalFrame journalFrame) {
        this.sleepLog = sleepLog;
        this.journal = journal;
        this.journalFrame = journalFrame;

        journalData = new JournalData(this.journal);
        this.setPreferredSize(new Dimension(400, 90));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        initializeTextLabel();
        addTextLabel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the texts and labels of the panel
    public void initializeTextLabel() {
        sleepField = new JTextField(5);
        wakeField = new JTextField(5);
        ans1 = new JTextField(5);
        ans2 = new JTextField(5);
        ans3 = new JTextField(5);
        ans4 = new JTextField(5);
        ans5 = new JTextField(5);
        noteField = new JTextField(10);

        sleepLogDate = new JLabel("Sleep Log for " + sleepLog.getDate());
        enterSleepLabel = new JLabel("Enter the time you went to bed: ");
        noticeLabel = new JLabel("(Enter time to the nearest hour in 24-hour format.)");
        enterWakeLabel = new JLabel("Enter the time you woke up: ");
        qnsLabel = new JLabel("Question 1-5: ");

        qn1 = new JLabel(qn1Text);
        qn2 = new JLabel(qn2Text);
        qn3 = new JLabel(qn3Text);
        qn4 = new JLabel(qn4Text);
        qn5 = new JLabel(qn5Text);
        clarification = new JLabel("Enter 'c' for caffeine, 'a' for alcohol, 'b' for both or 'n' for none.");
        note = new JLabel(noteText);
        next = new JButton("Next");
        next.setActionCommand("next");

    }

    // MODIFIES: this
    // EFFECTS: adds the initialized texts and labels to the panel
    public void addTextLabel() {
        this.add(sleepLogDate);
        this.add(enterSleepLabel);
        this.add(noticeLabel, BorderLayout.LINE_START);
        this.add(sleepField, BorderLayout.PAGE_END);
        this.add(enterWakeLabel, BorderLayout.LINE_START);
        this.add(wakeField, BorderLayout.LINE_END);
        this.add(qnsLabel, BorderLayout.LINE_START);
        this.add(qn1, BorderLayout.LINE_START);
        this.add(ans1, BorderLayout.LINE_END);
        this.add(qn2, BorderLayout.LINE_START);
        this.add(ans2, BorderLayout.LINE_END);
        this.add(qn3, BorderLayout.LINE_START);
        this.add(ans3, BorderLayout.LINE_END);
        this.add(qn4, BorderLayout.LINE_END);
        this.add(ans4, BorderLayout.LINE_END);
        this.add(qn5, BorderLayout.LINE_START);
        this.add(clarification, BorderLayout.LINE_START);
        this.add(ans5, BorderLayout.LINE_END);
        this.add(note, BorderLayout.LINE_END);
        this.add(noteField, BorderLayout.LINE_END);
        this.add(next, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: fills out the panel
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    protected void fillLogPanel() {
        next.addActionListener(e -> {
            if (!checkSleepHour()) {
                return;
            }
            if (!checkWakeHour()) {
                return;
            }
            if (!answer1Valid()) {
                return;
            }
            if (!answer2Valid()) {
                return;
            }
            if (!answer3Valid()) {
                return;
            }
            if (!answer4Valid()) {
                return;
            }
            if (!answer5Valid()) {
                return;
            }
            setOtherAnswers();
            addLog(sleepLog, journal);
        });
    }

    // MODIFIES: this
    // EFFECTS: sets the remaining answers to the sleep log
    private void setOtherAnswers() {
        String noteInput = noteField.getText();
        journalData.setNote(sleepLog, noteInput);
        sleepLog.calculateTotalHour();
    }

    // MODIFIES: this
    // EFFECTS: adds the sleep log to the journal
    private void addLog(SleepLog sleepLog, Journal journal) {
        journalData.sortAndAdd(sleepLog);
        JOptionPane.showMessageDialog(null, "Your sleep entry has been created "
                + journal.getUserName());
        journalFrame.getLogStatus(sleepLog);
    }

    // EFFECTS: returns true if the answer to qn1 is valid, otherwise, returns false
    private boolean answer1Valid() {
        String ans1Input = ans1.getText();
        return checkAns1(ans1Input);
    }

    // EFFECTS: returns true if the answer to qn2 is valid, otherwise, returns false
    private boolean answer2Valid() {
        String ans2Input = ans2.getText();
        return checkAns2(ans2Input);
    }

    // EFFECTS: returns true if the answer to qn3 is valid, otherwise, returns false
    private boolean answer3Valid() {
        String ans3Input = ans3.getText();
        return checkAns3(ans3Input);
    }

    // EFFECTS: returns true if the answer to qn4 is valid, otherwise, returns false
    private boolean answer4Valid() {
        String ans4Input = ans4.getText();
        return checkAns4(ans4Input);
    }

    // EFFECTS: returns true if the answer to qn5 is valid, otherwise, returns false
    private boolean answer5Valid() {
        String ans5Input = ans5.getText();
        return checkAns5(ans5Input);
    }

    // try catch code: referred lab 7 exercise
    // MODIFIES: this
    // EFFECTS: checks answer1 and sets the answer, otherwise shows new jlabel error saying that the answer is invalid
    private boolean checkAns1(String ans1Input) {
        if (journalData.checkAns1(ans1Input)) {
            journalData.checkSetAns1(sleepLog, ans1Input);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a number from 1-4.",
                    "Question 1 Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks answer2 and sets the answer, otherwise shows new jlabel error saying that the answer is invalid
    private boolean checkAns2(String answer) {
        if (journalData.checkUserInput(answer)) {
            journalData.checkSetAns2(sleepLog, answer);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a hour from 0-23.",
                    "Question 2 Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks answer3 and sets the answer, otherwise shows new jlabel error saying that the answer is invalid
    private boolean checkAns3(String answer) {
        if (journalData.checkValidAnswer(answer)) {
            journalData.checkSetAns3(sleepLog, answer);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid answer.",
                    "Question 3 Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks answer4 and sets the answer, otherwise shows new jlabel error saying that the answer is invalid
    private boolean checkAns4(String answer) {
        if (journalData.checkValidAnswer(answer)) {
            journalData.checkSetAns4(sleepLog, answer);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid answer.",
                    "Question 4 Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks answer5 and sets the answer, otherwise shows new jlabel error saying that the answer is invalid
    private boolean checkAns5(String ans5Input) {
        if (journalData.checkAns5(ans5Input)) {
            journalData.checkSetAns5(sleepLog, ans5Input);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid answer for "
                            + "question 5.",
                    "Question 5 Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks sleepHour and sets the answer, otherwise shows new jlabel error saying that the hour is invalid
    private boolean checkSleepHour() {
        String sleepInput = sleepField.getText();
        if (journalData.checkUserInput(sleepInput)) {
            journalData.inputSleepHour(sleepLog, sleepInput);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a hour from 0-23.",
                    "24 Hour Sleep Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: checks wakeHour and sets the answer, otherwise shows new jlabel error saying that the hour is invalid
    private boolean checkWakeHour() {
        String wakeInput = wakeField.getText();
        if (journalData.checkUserInput(wakeInput)) {
            journalData.inputWakeHour(sleepLog, wakeInput);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a hour from 0-23.",
                    "24 Hour Wake Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
