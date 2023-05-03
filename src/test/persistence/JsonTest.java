package persistence;

import model.SleepLog;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {

    protected void checkSleepLog(String date, SleepLog sleepLog) {
        assertEquals(date, sleepLog.getDate());

    }
}
