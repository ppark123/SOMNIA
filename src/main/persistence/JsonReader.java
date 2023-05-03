package persistence;

import model.Journal;
import model.SleepLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Reads the Strings in the data file, that JsonWriter writes, to the users
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it
    // throws IOException if an error occurs reading the data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        Journal journal = new Journal(name);
        addSleepLogs(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: parses sleepLogs from JSON object and adds them to journal
    private void addSleepLogs(Journal journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Sleep logs");
        for (Object json : jsonArray) {
            JSONObject nextSleepLog = (JSONObject) json;
            addSleepLog(journal, nextSleepLog);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses sleep log from JSON object and adds it to journal
    private void addSleepLog(Journal journal, JSONObject jsonObject) {
        String date = jsonObject.getString("Date");
        String sleepHour = jsonObject.getString("Sleep time");
        String wakeHour = jsonObject.getString("Wake time");
        String calculateHour = jsonObject.getString("Total hour slept");
        String ans1 = jsonObject.getString("Level of comfort");
        String ans2 = jsonObject.getString("Actual time of sleep");
        String ans3 = jsonObject.getString("Number of times user woke up");
        String ans4 = jsonObject.getString("Number of naps");
        String ans5 = jsonObject.getString("Caffeine or alcohol consumption");
        String note = jsonObject.getString("Note");

        SleepLog sleepLog = new SleepLog(date);
        sleepLog.setSleepHour(sleepHour);
        sleepLog.setWakeHour(wakeHour);
        sleepLog.setTotalHourSlept(calculateHour);
        sleepLog.setAns1(ans1);
        sleepLog.setAns2(ans2);
        sleepLog.setAns3(ans3);
        sleepLog.setAns4(ans4);
        sleepLog.setAns5(ans5);
        sleepLog.setNote(note);

        journal.loadToList(sleepLog);
    }
}