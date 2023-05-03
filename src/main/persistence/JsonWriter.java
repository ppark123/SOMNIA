package persistence;

import model.Journal;
import org.json.JSONObject;


import java.io.*;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A writer that writes the JSON representation of journal
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: instantiates a writer to write on the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer and throws a FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of journal to file
    public void write(Journal journal) {
        JSONObject json = journal.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
