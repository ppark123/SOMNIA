package persistence;

import org.json.JSONObject;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Interface that contains a method for changing objects into JSON objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
