package persistence;

import model.Journal;
import model.SleepLog;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {

    @Test
    void testInvalidFile(){
        try {
            Journal journal = new Journal("perl");
            JsonWriter writer = new JsonWriter("./data/\0testReaderInvalidFile.json");
            writer.open();
            fail("Expected FileNotFoundException to be thrown.");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testEmptyJournal(){
        try {
            Journal journal = new Journal("empty");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            journal = reader.read();
            assertEquals("empty", journal.getUserName());
            assertEquals(0, journal.getLogList().size());
            // expected
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException thrown.");
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }
    }

    @Test
    void testGeneralJournal(){

        try {
            Journal journal = new Journal("p123");
            journal.loadToList(new SleepLog("4/9/22"));
            journal.loadToList(new SleepLog("4/21/22"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");

            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            journal = reader.read();
            List<SleepLog> sleepLogs = journal.getLogList();
            assertEquals(2, sleepLogs.size());
            checkSleepLog("4/9/22", journal.getLogList().get(0));
            checkSleepLog("4/21/22", journal.getLogList().get(1));

        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException thrown.");
        } catch (IOException e) {
            fail("Unexpected IOException thrown.");
        }

    }
}
