package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a journal for the year of 2022 that contains a userName and a sleepLogList.
public class Journal implements Writable {

    private String userName;                                        //user's chosen username
    private List<SleepLog> sleepLogList;                            //list of sleep logs

    // EFFECTS: creates a new empty journal
    public Journal(String name) {
        this.userName = name;
        sleepLogList = new ArrayList<>();
    }

    // EFFECTS: adds a filled sleep log to the list of sleep logs specifically used for reading in JsonReader
    public void loadToList(SleepLog sleepLog) {
        sleepLogList.add(sleepLog);
        EventLog.getInstance().logEvent(new Event("\t" + getUserName() + "'s journal: A sleep log for "
                + sleepLog.getDate()
                + " has been loaded."));
    }

    // EFFECTS: adds a filled sleep log to the list of sleep logs at the appropriate index
    public void addIndex(int index, SleepLog newLog) {
        sleepLogList.add(index, newLog);
        EventLog.getInstance().logEvent(new Event("\t" + getUserName() + "'s journal: A sleep log for "
                + newLog.getDate()
                + " has been added."));
    }

    //getters
    public List<SleepLog> getLogList() {
        return sleepLogList;
    }

    public String getUserName() {
        return userName;
    }

    //MODFIES: this
    //EFFECTS: removes a given sleep log from the list of sleeplogs
    public void removeLog(SleepLog s) {
        sleepLogList.remove(s);
        EventLog.getInstance().logEvent(new Event("\t" + getUserName() + "'s journal: The sleep log for "
                + s.getDate()
                + " has been deleted."));
    }

    //setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //below codes for Json referred from JsonSerializationDemo; link below:
    //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", userName);
        json.put("Sleep logs", sleepLogToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray sleepLogToJson() {
        JSONArray jsonArray = new JSONArray();
        for (SleepLog s : sleepLogList) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
