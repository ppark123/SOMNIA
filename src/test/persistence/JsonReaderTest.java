package persistence;

import model.Journal;
import model.SleepLog;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    void testInvalidFile(){
        JsonReader jsonReader = new JsonReader("./data/testReaderInvalidFilel.json");
        try {
            Journal journal = jsonReader.read();
            fail("Invalid file.");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testReaderEmptyJournal(){
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals("empty", journal.getUserName());
            assertEquals(0, journal.getLogList().size());
        } catch (IOException e) {
            fail("The file could not be read.");
        }
    }

    @Test
    void testReaderGeneralJournal(){
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal journal = reader.read();
            assertEquals("perl", journal.getUserName());
            List<SleepLog> logList = journal.getLogList();
            assertEquals(2, logList.size());
            checkSleepLog("4/9/22", journal.getLogList().get(0));
            checkSleepLog("4/21/22",journal.getLogList().get(1));
        } catch (IOException e) {
            fail("The file could not be read.");
        }

    }
}
