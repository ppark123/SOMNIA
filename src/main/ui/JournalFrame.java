package ui;

import model.Event;
import model.EventLog;
import model.Journal;
import model.SleepLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//The ui of the journal app; displays the frame containing the user interface
public class JournalFrame extends JFrame {
    //questions from journal are based on website mayoclinic; link below:
    //https://www.mayoclinic.org/healthy-lifestyle/adult-health/in-depth/how-a-sleep-diary-can-transform-how-you-
    //feel/art-20342128
    private static final String qn1Text = "1. From a scale of 1-4 how well did you sleep?";
    private static final String qn2Text = "2. What time did you actually fall asleep?";
    private static final String qn3Text = "3. How many times during the night did you wake up?";
    private static final String qn4Text = "4. How many naps did you take during the day?";
    private static final String qn5Text = "5. Did you drink any caffeine or alcohol on this day?";
    private static final String noteText = "Add any other notes about your sleep or enter N/A if none are needed: ";

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/myFile.text";

    private JPanel imagePanel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Journal journal;

    // code based on trafficLight; link below
    //https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git
    // EFFECTS: initializes a new journal frame with a title and initializes graphics and fields
    public JournalFrame() {
        super("My Sleep Journal");
        intializeGraphics();
        initializeFields();
    }

    // EFFECTS: initializes the field of necessary variables
    private void initializeFields() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes the specifics of the graphics
    private void intializeGraphics() {
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.firstMenu();
        this.addWindowListener(new WindowAdapter() {
            @Override
            //EFFECTS: prints the events in the eventLog onto the console
            public void windowClosing(WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString());
                }
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: creates the bg image and create or load options of the journal
    private void firstMenu() {
        imagePanel = createImagePanel();
        JPanel firstOption = createFirstOption();
        JButton createButton = new JButton("Create new journal");
        createButton.setActionCommand("createJournal");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createJournal();
            }
        });
        JButton loadJournalButton = new JButton("Load Journal");
        loadJournalButton.setActionCommand("loadJournal");
        loadJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadJournal();
            }
        });
        firstOption.add(createButton);
        firstOption.add(loadJournalButton);
        this.add(firstOption, BorderLayout.PAGE_END);
        this.add(imagePanel, BorderLayout.PAGE_START);
        setUpFrame();
    }

    // EFFECTS: creates an image panel with bg image
    private JPanel createImagePanel() {
        ImageIcon image = new ImageIcon("./data/art.jpg");
        JLabel welcomeImage = new JLabel(image);
        JPanel imagePanel = new JPanel();
        imagePanel.add(welcomeImage);
        return imagePanel;
    }

    // EFFECTS: creates a first option panel of desired shape and size
    private JPanel createFirstOption() {
        JPanel firstOption = new JPanel();
        firstOption.setLayout(new GridLayout(2, 1));
        firstOption.setPreferredSize(new Dimension(200, 150));
        return firstOption;
    }

    // MODIFIES: this
    // EFFECTS: loads the journal from JSON_STORE else returns an error that the file cannot be read.
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            JOptionPane.showMessageDialog(null, "The journal for " + journal.getUserName()
                    + " has been loaded.", "Load journal", JOptionPane.PLAIN_MESSAGE);
            showMainMenu();

        } catch (IOException ie) {
            JOptionPane.showMessageDialog(null, "The file " + JSON_STORE
                    + " cannot be read.", "Failed load journal", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // EFFECTS: creates a new panel with the choice to add a new journal or add onto an existing one
    private void createJournal() {
        String name = getUserName();
        if (name == null || name.isEmpty()) {
            firstMenu();
        } else {
            journal = new Journal(name);
            showMainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the main menu panel with appropriate buttons added
    protected void showMainMenu() {
        clearFrame();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setPreferredSize(new Dimension(500, 200));
        JButton createSLButton = makeCreateButton(buttonPanel);
        JButton viewJournalButton = makeViewButton(buttonPanel);
        JButton mainMenuButton = makeReturnButton(buttonPanel);
        buttonPanel.add(createSLButton, BorderLayout.PAGE_END);
        buttonPanel.add(viewJournalButton, BorderLayout.PAGE_END);
        buttonPanel.add(mainMenuButton, BorderLayout.PAGE_END);
        buttonPanel.setVisible(true);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.add(imagePanel, BorderLayout.PAGE_START);
        setUpFrame();
    }

    // MODIFIES: buttonPanel
    // EFFECTS: returns a jbutton for making the home menu button, asks user to save the journal before leaving.
    // lays button out on buttonPanel
    private JButton makeReturnButton(JPanel buttonPanel) {
        JButton mainMenuButton = new JButton("Home menu");
        mainMenuButton.setActionCommand("homeMenu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to save your "
                                + "current progress before leaving?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    saveJournal();
                } else {
                    buttonPanel.setVisible(false);
                    firstMenu();
                }
            }
        });
        return mainMenuButton;
    }

    // MODIFIES: buttonPanel
    // EFFECTS: creates a make view button which shows the sleep lgo entries user made so far
    // lays button on buttonPanel
    private JButton makeViewButton(JPanel buttonPanel) {
        JButton viewJournalButton = new JButton("Manage journal");
        viewJournalButton.setActionCommand("manageJournal");
        viewJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                manageJournal();
            }
        });
        return viewJournalButton;
    }

    // MODIFIES: buttonPanel
    // returns a jButton of a create button for creating a new sleep log
    // lays button out on buttonPanel
    private JButton makeCreateButton(JPanel buttonPanel) {
        JButton createSLButton = new JButton("Create a new sleep log");
        createSLButton.setActionCommand("createSleepLog");
        createSLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                createSleepLog();
            }
        });
        return createSLButton;
    }

    //EFFECTS: creates a new sleeplog by first getting the date. enters state as "create"
    private void createSleepLog() {
        clearFrame();
        getDate("create");
    }

    // code based on JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves a journal with their corresponding sleep log entries or presents a FileNotFoundException if there
    // are no files saved
    protected void saveJournal() {
        clearFrame();
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "The journal for " + journal.getUserName()
                    + " has been saved to " + JSON_STORE + ".", "Save journal", JOptionPane.PLAIN_MESSAGE);

            firstMenu();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "FileNotFoundException: this file cannot be found.", "Invalid file",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // code based on JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: prints the sleep logs saved in a journal on a scroll pane
    private void printJournal() {
        List<SleepLog> sleepLogs = journal.getLogList();
        JPanel panel = new JPanel();
        for (SleepLog s : sleepLogs) {
            addLogPanel(panel, s);
        }
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(scroll);
        JButton quitBtn = new JButton("quit");
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                reset();
                showMainMenu();
            }
        });
        this.add(quitBtn, BorderLayout.PAGE_END);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: buttonPanel
    // EFFECTS: creates a JButton for viewing all sleep logs in journal; lays button on buttonPanel of viewJournal
    private JButton viewJournalButton(JPanel buttonPanel) {
        JButton viewJournalButton = new JButton("View journal");
        viewJournalButton.setActionCommand("viewJournal");
        viewJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                clearFrame();
                printJournal();
            }
        });
        return viewJournalButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a buttonPanel with the button choices to either view a journal or a single sleep log
    protected void manageJournal() {
        clearFrame();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setPreferredSize(new Dimension(200, 150));
        JButton viewLogButton = viewLogButton(buttonPanel);
        JButton viewJournalButton = viewJournalButton(buttonPanel);
        JButton deleteLogButton = deleteLogButton(buttonPanel);
        buttonPanel.add(viewLogButton, BorderLayout.PAGE_END);
        buttonPanel.add(viewJournalButton, BorderLayout.PAGE_END);
        buttonPanel.add(deleteLogButton, BorderLayout.PAGE_END);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.add(imagePanel, BorderLayout.PAGE_START);
        setUpFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates a JBUtton with the choice to delete a sleeplog
    protected JButton deleteLogButton(JPanel buttonPanel) {
        JButton deleteLogButton = new JButton("Delete a sleep log");
        deleteLogButton.setActionCommand("deleteLog");
        deleteLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                clearFrame();
                getDate("delete");
            }
        });
        return deleteLogButton;
    }


    // MODIFIES: buttonPanel
    // EFFECTS: creates a viewLogButton for viewing a single log and lays it out on the button panel
    private JButton viewLogButton(JPanel buttonPanel) {
        JButton viewLogButton = new JButton("View a sleep log");
        viewLogButton.setActionCommand("viewSleepLog");
        viewLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGetLog();
            }
        });
        return viewLogButton;
    }

    // MODIFIES: this
    // EFFECTS: Checks if there is a log in the journal if not, returns error message if yes, takes user to view.
    private void checkGetLog() {
        if (journal.getLogList().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "There are no sleep logs to view.", "Empty journal",
                    JOptionPane.PLAIN_MESSAGE);
            showMainMenu();
        } else {
            getDate("view");
        }
    }

    // EFFECTS: asks for the username of the journal and returns the input
    private String getUserName() {
        return JOptionPane.showInputDialog(null, "Enter a username: ",
                "Journal setup", JOptionPane.INFORMATION_MESSAGE);
    }


    //MODIFIES: this
    //EFFECTS: gets the date that the user wants to create their sleeplog
    private void getDate(String state) {
        clearFrame();
        GetDatePanel datePanel = new GetDatePanel(state, journal, this);
        datePanel.fillDate();
        this.add(datePanel);
        setUpFrame();
    }

    //MODIFIES: this
    //EFFECT: fills out a sleep log of a given date
    protected void fillOutLog(String date) {
        clearFrame();
        SleepLog sleepLog = new SleepLog(date);
        FillLogPanel getDatePanel = new FillLogPanel(sleepLog, journal, this);
        getDatePanel.fillLogPanel();
        this.setVisible(true);
        this.add(getDatePanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: prints the information of a given sleep log on a new panel
    protected void getLogStatus(SleepLog sleepLog) {
        clearFrame();
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 90));
        addLogPanel(panel, sleepLog);

        JButton quitBtn = new JButton("Quit");
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                showMainMenu();
            }
        });
        this.add(panel, BorderLayout.CENTER);
        this.add(quitBtn, BorderLayout.SOUTH);
        setUpFrame();
    }

    // MODIFIES: panel
    // EFFECTS: adds the sleepLog details onto the panel
    private void addLogPanel(JPanel panel, SleepLog s) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel dateLabel = new JLabel("A sleep log entry for: " + s.getDate());
        dateLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(dateLabel);

        JLabel sleepLabel = new JLabel("Sleep time: " + s.getSleepHour());
        sleepLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(sleepLabel);

        JLabel wakeLabel = new JLabel("Wake time: " + s.getWakeUpHour());
        wakeLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(wakeLabel);

        JLabel totalHourLabel = new JLabel("Total hours of sleep: " + s.getTotalHour());
        totalHourLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(totalHourLabel);

        getAnswers(panel, s);

        JLabel notesLabel = new JLabel("Notes: " + s.getNotes());
        notesLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(notesLabel);

        JLabel gap = new JLabel(" ");
        gap.setVerticalAlignment(JLabel.CENTER);
        panel.add(gap);
    }

    // MODIFIES: panel
    // EFFECTS: gets the answers of a previously made sleep log
    private void getAnswers(JPanel panel, SleepLog sleepLog) {

        JLabel ansOneLabel = new JLabel("Sleep comfort: " + sleepLog.getAnswer1());
        ansOneLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(ansOneLabel);

        JLabel ansTwoLabel = new JLabel("Actual hour of sleep: " + sleepLog.getAnswer2());
        ansTwoLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(ansTwoLabel);

        JLabel ansThreeLabel = new JLabel("Number of times user woke up: " + sleepLog.getAnswer3());
        ansThreeLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(ansThreeLabel);

        JLabel ans4Label = new JLabel("Number of naps taken during the day: " + sleepLog.getAnswer4());
        ans4Label.setVerticalAlignment(JLabel.CENTER);
        panel.add(ans4Label);

        JLabel ans5Label = new JLabel("Caffeine and/or alcohol consumption: " + sleepLog.getAnswer5());
        ans5Label.setVerticalAlignment(JLabel.CENTER);
        panel.add(ans5Label);
    }

    // MODIFIES: this
    // EFFECTS: sets up the panels of the frame
    private void setUpFrame() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: clears the frame
    private void clearFrame() {
        this.getContentPane().removeAll();
    }

    //code referred from traffic light lecture lab
    //https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git
    // MODIFIES: this
    // EFFECTS: clears the frame
    private void reset() {
        this.getContentPane().invalidate();
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

    //MODIFIES: journal, this
    //EFFECTS: removes a desired sleeplog from the list of sleeplogs in journal
    public void removeLog(SleepLog s) {
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the "
                + "sleep log for " + s.getDate() + "?", "Delete sleep log", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            journal.removeLog(s);
        }
        reset();
        showMainMenu();
    }

    public static void main(String[] args) {
        new JournalFrame();
    }

}