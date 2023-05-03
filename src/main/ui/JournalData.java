package ui;

import model.Journal;
import model.SleepLog;

import javax.swing.*;
import java.util.List;

// JournalData processes the inputs from the journalFrame
public class JournalData {
    private Journal journal;

    //EFFECTS: creates a new instance of a journalData
    public JournalData(Journal journal) {
        this.journal = journal;
    }

    //REQUIRES: inputSleepHour >= 0 && inputSleepHour <= 23
    //MODIFIES: this
    //EFFECTS: stores the hour that the user goes to bed, after removing any leading zeros
    public void inputSleepHour(SleepLog s, String sleepField) {
        String refinedSleepHour = removeLeadingZero(sleepField);
        s.setSleepHour(refinedSleepHour);
    }

    //MODIFIES: this
    //EFFECTS: stores the hour that the user wakes up from their sleep, after removing any leading zeros
    public void inputWakeHour(SleepLog s, String wakeField) {
        String refinedWakeHour = removeLeadingZero(wakeField);
        s.setWakeHour(refinedWakeHour);
    }

    //REQUIRES: inputSleepHour >= 0 && inputSleepHour <= 23
    //MODIFIES: this
    //EFFECTS: returns true if the answers to 3 and 4 is not a negative number, else returns false and shows an error
    public boolean checkUserInput(String userAnswer) {
        try {
            int intAnswer = Integer.parseInt(userAnswer);
            return intAnswer >= 0 && intAnswer <= 23;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Empty Answer or wrong number format.",
                    "Number Format Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: s
    // EFFECTS: sets the answer to qn1 after removing any leading zeros.
    protected void checkSetAns1(SleepLog s, String ans1Input) {
        String refinedAns1 = removeLeadingZero(ans1Input);
        s.setAns1(String.valueOf(refinedAns1));
    }

    // MODIFIES: s
    // EFFECTS: sets the answer to qn2 after removing any leading zeros.
    protected void checkSetAns2(SleepLog s, String ans2Input) {
        String refinedAns2 = removeLeadingZero(ans2Input);
        s.setAns2(String.valueOf(refinedAns2));
    }

    // MODIFIES: s
    // EFFECTS: sets the answer to qn3 after removing any leading zeros.
    protected void checkSetAns3(SleepLog s, String ans3Input) {
        String refinedAns3 = removeLeadingZero(ans3Input);
        s.setAns3(String.valueOf(refinedAns3));
    }

    // MODIFIES: s
    // EFFECTS: sets the answer to qn4 after removing any leading zeros.
    protected void checkSetAns4(SleepLog s, String ans4Input) {
        String refinedAns4 = removeLeadingZero(ans4Input);
        s.setAns4(String.valueOf(refinedAns4));
    }

    // MODIFIES: s
    // EFFECTS: sets the answer to qn5.
    protected void checkSetAns5(SleepLog s, String ans5Input) {
        setAns5(s, ans5Input);
    }

    // EFFECTS: returns true if the answer input is equal to c or a or b or n else returns false
    protected boolean checkAns5(String ans5Input) {
        return ans5Input.equals("c") || ans5Input.equals("a") || ans5Input.equals("b") || ans5Input.equals("n");
    }

    //REQUIRES: intAns1 >= 0 && intAns1 <= 4
    // EFFECTS: checks that the answer to qn1 is valid and returns true,
    // else returns false and JOptionPane with error message pops up
    public boolean checkAns1(String userAns1) {
        try {
            int intAns1 = Integer.parseInt(userAns1);
            return intAns1 >= 0 && intAns1 <= 4;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Empty answer or wrong number format.",
                    "Number Format Error", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    //REQUIRES: intAnswer >= 0
    //EFFECTS: checks that the answers to 3 and 4 is not a negative number and returns true if right
    // else returns false and JOptionPane with error message pops up
    public boolean checkValidAnswer(String userAnswer) {
        try {
            int intAnswer = Integer.parseInt(userAnswer);
            return intAnswer >= 0;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Empty answer or wrong number format.",
                    "Number Format Error", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    //REQUIRES: characters c, a, b, n
    //MODIFIES: s
    //EFFECTS: sets the answer to question five to the answerinput given
    public void setAns5(SleepLog s, String userAns5) {
        switch (userAns5) {
            case "c":
                s.setAns5("caffeine");
                break;
            case "a":
                s.setAns5("alcohol");
                break;
            case "b":
                s.setAns5("both");
                break;
            default:
                s.setAns5("none");
                break;
        }
    }

    //EFFECTS: returns true if the given journal date is one that hasn't been used so far and is a valid date
    // else returns false
    public boolean checkDate(String chosenDate) {
        return checkDateExists(chosenDate) && checkDateValid(chosenDate);
    }

    // EFFECTS: checks that the date has not been used to create a log already.
    // If there is a log created already, returns false.
    // Otherwise, in any other cases including when the journal log list is empty, returns true
    public boolean checkDateExists(String chosenDate) {
        if (journal.getLogList().isEmpty()) {
            return true;
        } else {
            for (SleepLog log : journal.getLogList()) {
                if (log.getDate().equals(chosenDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    // EFFECTS: takes the month, date and year of the chosenDate, turns it into an integer and
    // checks that the date is a valid date in the year of 2022.
    // If it is, returns true, else returns false.
    public boolean checkDateValid(String chosenDate) {
        int month = getIntMonth(chosenDate);
        int day = getIntDay(chosenDate);
        int year = getIntYear(chosenDate);
        if (year == 22) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                return day >= 1 && day <= 31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                return day >= 1 && day <= 30;
            } else if (month == 2) {
                return day >= 1 && day <= 28;
            }
        }
        return false;
    }

    // EFFECTS: checks that there is a journal with the given date.
    // If journal with the given date exists, returns the journal, else returns null
    public SleepLog checkViewDate(String viewDate) {
        for (SleepLog log : journal.getLogList()) {
            if (log.getDate().equals(viewDate)) {
                return log;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sorts and adds the given sleeplog in the right place in journal (from oldest to latest)
    // if the journal's log list is empty the sleeplog is added at index 0
    // if the given sleeplog is more recent than a compared log, then it is compared to the next log on the list
    // if the given log has a later date than the compared log, it is added at the index before the compared one
    public void sortAndAdd(SleepLog sleeplog) {
        String sleepLogDate = sleeplog.getDate();
        List<SleepLog> logList = journal.getLogList();

        if (logList.isEmpty()) {
            journal.addIndex(0, sleeplog);
        } else {
            int index = 0;
            for (SleepLog s : logList) {
                String prevDate = s.getDate();
                String resultingString = compareDates(sleepLogDate, prevDate);
                if (resultingString.equals("greater")) {
                    index++;
                    if (index == logList.size()) {
                        journal.addIndex(index, sleeplog);
                        break;
                    }
                } else {
                    journal.addIndex(index, sleeplog);
                    break;
                }
            }
        }
    }

    // EFFECTS: compares the given log and a previous log and checks the month first to see if the given log is the
    // latest, oldest or the same month. If the month is the same, checks the latest day out of the two.
    // Returns the string "greater" if the log is more recent, or "less" if it is a more previous log than the previous
    // log that was added in.
    private String compareDates(String sleepLogDate, String prevDate) {
        int month = getIntMonth(sleepLogDate);
        int day = getIntDay(sleepLogDate);
        int prevMonth = getIntMonth(prevDate);
        int prevDay = getIntDay(prevDate);
        String status = "";

        if (month > prevMonth) {
            status = "greater";
        } else if (month < prevMonth) {
            status = "less";
        } else {
            if (day > prevDay) {
                status = "greater";
            } else if (day < prevDay) {
                status = "less";
            }
        }
        return status;
    }

    // MODIFIES: date
    // EFFECTS: removes the leading zeros of a give answer
    private String removeLeadingZero(String answer) {
        String refinedAnswer = answer;
        for (int i = 0; i < refinedAnswer.length(); i++) {
            if (refinedAnswer.length() == 1) {
                break;
            }
            Character c = refinedAnswer.charAt(i);
            if (c.equals('0')) {
                refinedAnswer = refinedAnswer.replaceFirst("0", "");
                i--;
            } else {
                break;
            }
        }

        return refinedAnswer;
    }

    // EFFECTS: checks that the number is of integer format and returns true,
    // if not, states that its of wrong number format and returns false
    protected boolean checkNumber(String stringDate) {
        try {
            Integer.parseInt(stringDate);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Empty answer or wrong number format.",
                    "Number Format Error", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    // EFFECTS: gets only the month integer from the string of date
    private String getStringMonth(String date) {
        return date.substring(0, date.indexOf("/"));
    }

    // EFFECTS: gets only the day integer from the string of date
    private String getStringDay(String date) {
        return date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
    }

    // EFFECTS: gets only the year integer from the string of date
    private String getStringYear(String date) {
        return date.substring(date.lastIndexOf("/") + 1);
    }


    // EFFECTS: gets only the month integer from the string of date
    private int getIntMonth(String date) {
        String month = getStringMonth(date);
        return Integer.parseInt(month);
    }

    // EFFECTS: gets only the day integer from the string of date
    private int getIntDay(String date) {
        String day = getStringDay(date);
        return Integer.parseInt(day);
    }

    // EFFECTS: gets only the year integer from the string of date
    private int getIntYear(String date) {
        String year = getStringYear(date);
        return Integer.parseInt(year);
    }

    //MODIFIES: s
    //EFFECTS: adds user's notes about sleep to the sleep log
    public void setNote(SleepLog s, String note) {
        s.setNote(note);
    }

    // MODIFIES: chosenDate
    // EFFECTS: returns the date with the leading zeros removed from the date
    public String refineDate(String chosenDate) {
        String month = getStringMonth(chosenDate);
        month = removeLeadingZero(month);
        String day = getStringDay(chosenDate);
        day = removeLeadingZero(day);
        String year = getStringYear(chosenDate);
        year = removeLeadingZero(year);
        return  month + "/" + day + "/" + year;
    }
}