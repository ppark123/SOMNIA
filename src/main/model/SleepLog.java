package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a sleep log distinguished by date (MM/DD/YY).
// The log records the user's sleepHour, wakeUpHour, totalHourSlept, answers to questions 1 through 5 and notes.
public class SleepLog implements Writable {

    private String date;        // date of sleep log
    private String sleepHour;      // hour that user slept
    private String wakeUpHour;     // hour that user woke up
    private String totalHourSlept; // total hour slept

    private String ans1;        // answer to question 1
    private String ans2;        // answer to question 2
    private String ans3;        // answer to question 3
    private String ans4;        // answer to question 4
    private String ans5;        // answer to question 5
    private String notes;       // any notes

    // EFFECTS: creates an empty sleep log with a given date.
    public SleepLog(String date) {
        this.date = date;
        sleepHour = "";
        wakeUpHour = "";
        totalHourSlept = "";

        ans1 = "";
        ans2 = "";
        ans3 = "";
        ans4 = "";
        ans5 = "";
        notes = "";
    }

    // Requires: 0 <= totalHour <= 23
    // EFFECTS: calculates total hour of sleep with the inputSleepHour and inputWakeHour
    // If the inputSleepHour >= 20 and the inputWakeHour < 20, the total hour is 24 - inputSleepHour + inputWakeHour
    // else if the input
    public void calculateTotalHour() {
        int totalHour;
        int sleep = Integer.parseInt(sleepHour);
        int wake = Integer.parseInt(wakeUpHour);
        if (sleep > wake) {
            totalHour = (24 - sleep) + wake;
        } else {
            totalHour = wake - sleep;
        }
        this.totalHourSlept = String.valueOf(totalHour);
    }

    // getters
    public String getDate() {
        return date;
    }

    public String getSleepHour() {
        return sleepHour + ":00";
    }

    public String getWakeUpHour() {
        return wakeUpHour + ":00";
    }

    public String getTotalHour() {
        return totalHourSlept + " hours";
    }

    public String getAnswer1() {
        return ans1 + "/4";
    }

    public String getAnswer2() {
        return ans2 + ":00";
    }

    public String getAnswer3() {
        return ans3;
    }

    public String getAnswer4() {
        return ans4;
    }

    public String getAnswer5() {
        return ans5;
    }

    public String getNotes() {
        return notes;
    }

    //setters
    public void setSleepHour(String inputSleepHour) {
        sleepHour = inputSleepHour;
    }

    public void setWakeHour(String inputWakeHour) {
        wakeUpHour = inputWakeHour;
    }

    public void setTotalHourSlept(String totalHourSlept) {
        this.totalHourSlept = totalHourSlept;
    }

    public void setAns1(String ansOneInput) {
        ans1 = ansOneInput;
    }

    public void setAns2(String ansTwoInput) {
        ans2 = ansTwoInput;
    }

    public void setAns3(String ansThreeInput) {
        ans3 = ansThreeInput;
    }

    public void setAns4(String ansFourInput) {
        ans4 = ansFourInput;
    }

    public void setAns5(String ansFiveInput) {
        ans5 = ansFiveInput;
    }

    public void setNote(String userNote) {
        notes = userNote;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Date", date);
        json.put("Sleep time", sleepHour);
        json.put("Wake time", wakeUpHour);
        json.put("Total hour slept", totalHourSlept);
        json.put("Level of comfort", ans1);
        json.put("Actual time of sleep", ans2);
        json.put("Number of times user woke up", ans3);
        json.put("Number of naps", ans4);
        json.put("Caffeine or alcohol consumption", ans5);
        json.put("Note", notes);
        return json;
    }


}
