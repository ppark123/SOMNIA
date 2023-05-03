package ui;

import model.Journal;
import model.SleepLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//JPanel to get the date of the sleep log
public class GetDatePanel extends JPanel {
    private String state;
    private String refinedDate;
    private JTextField monthText;
    private JTextField dayText;
    private JTextField yearText;
    private KeyAdapter key;

    private JournalData journalData;
    private JournalFrame frame;
    private Journal journal;


    //EFFECTS: creates a new instance of a GetDatePanel of a given state, journal and frame
    public GetDatePanel(String state, Journal journal, JournalFrame frame) {
        this.state = state;
        this.journal = journal;
        this.frame = frame;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        journalData = new JournalData(journal);
        addLabelText();
        defineKey(this);
    }

    private void defineKey(JPanel panel) {
        key = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String month = monthText.getText();
                    String day = dayText.getText();
                    String year = yearText.getText();
                    if (!journalData.checkNumber(month)) {
                        return;
                    }
                    if (!journalData.checkNumber(day)) {
                        return;
                    }
                    if (!journalData.checkNumber(year)) {
                        return;
                    }
                    refinedDate = journalData.refineDate(month + "/" + day + "/" + year);
                    checkSetDate();
                    } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        panel.setVisible(false);
                        frame.showMainMenu();
                    }
                }
            };
    }

    //MODIFIES: frame
    //EFFECTS: checks the date and views, deletes or creates a new sleeplog.
    private void checkSetDate() {
        if (state.equals("view") || state.equals("delete")) {
            SleepLog s = journalData.checkViewDate(refinedDate);
            if (s == null) {
                JOptionPane.showMessageDialog(null,
                        "A sleep log entry with this date does not exist.", "Invalid date",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                if (state.equals("view")) {
                    frame.getLogStatus(s);
                } else if (state.equals("delete")) {
                    frame.removeLog(s);
                }
            }
        } else {
            createLog();
        }
    }

    //MODIFIES: frame
    //EFFECTS: creates a new sleepLog with the desired date
    private void createLog() {
        if (!journalData.checkDate(refinedDate)) {
            JOptionPane.showMessageDialog(null, "The date that you have entered is either "
                    + "invalid or has been used already.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            clear();
            frame.fillOutLog(refinedDate);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds the label and texts to the jpanel
    public void addLabelText() {
        JLabel instruction = new JLabel("Press 'Enter' to enter the date or 'Shift' to go back.");
        JLabel monthLabel = new JLabel("Choose a month MM");
        JLabel dayLabel = new JLabel("Choose a day DD");
        JLabel yearLabel = new JLabel("Choose a year YY");
        monthText = new JTextField(10);
        dayText = new JTextField(10);
        yearText = new JTextField(10);
        this.add(instruction, BorderLayout.LINE_START);
        this.add(monthLabel, BorderLayout.LINE_START);
        this.add(monthText);
        this.add(dayLabel, BorderLayout.LINE_START);
        this.add(dayText);
        this.add(yearLabel, BorderLayout.LINE_START);
        this.add(yearText);
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: clears the panel from the frame
    public void clear() {
        this.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: gets the chosen date and checks that its valid, if valid moves onto the nextAction
    public void fillDate() {
        yearText.addKeyListener(key);
        monthText.addKeyListener(key);
        dayText.addKeyListener(key);
        frame.addKeyListener(key);
    }
}
