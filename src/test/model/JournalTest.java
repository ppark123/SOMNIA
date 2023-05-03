package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {
    private Journal journal;
    private Journal emptyJournal;
    private SleepLog sleepLog1;
    private SleepLog sleepLog2;

    @BeforeEach
    void runBefore(){
        journal = new Journal("pearl");
        emptyJournal = new Journal("");

        sleepLog1 = new SleepLog("2/30/22");
        sleepLog1.setSleepHour("22");
        sleepLog1.setWakeHour("6");
        sleepLog1.calculateTotalHour();
        sleepLog1.setAns1("1");
        sleepLog1.setAns2("20");
        sleepLog1.setAns3("0");
        sleepLog1.setAns4("1");
        sleepLog1.setAns5("caffeine");
        sleepLog1.setNote("Took a nap for 30 minutes at 3:00PM.");
        journal.addIndex(0, sleepLog1);

        sleepLog2 = new SleepLog("3/1/22");
        sleepLog2.setSleepHour("21");
        sleepLog2.setWakeHour("8");
        sleepLog2.calculateTotalHour();
        sleepLog2.setAns1("3");
        sleepLog2.setAns2("22");
        sleepLog2.setAns3("1");
        sleepLog2.setAns4("0");
        sleepLog2.setAns5("both");
        sleepLog2.setNote("Exercised for an hour during the day.");
    }

    @Test
    //test for empty journal with no username
    void testEmptyJournal(){
        assertTrue(emptyJournal.getLogList().isEmpty());
        assertEquals("", emptyJournal.getUserName());
    }

    @Test
    void testSetUserName(){
        emptyJournal.setUserName("Alex");
        assertEquals("Alex", emptyJournal.getUserName());
    }

    @Test
    void testAddList(){
        assertEquals(0, emptyJournal.getLogList().size());
        emptyJournal.loadToList(sleepLog1);
        assertTrue(emptyJournal.getLogList().contains(sleepLog1));
        assertEquals(1, emptyJournal.getLogList().size());

        emptyJournal.loadToList(sleepLog2);
        assertTrue(emptyJournal.getLogList().contains(sleepLog2));
        assertEquals(2, emptyJournal.getLogList().size());
    }

    @Test
    void testRemoveSleepLog(){
        journal.loadToList(sleepLog2);
        assertEquals(2, journal.getLogList().size());

        journal.removeLog(sleepLog1);
        assertFalse(journal.getLogList().contains(sleepLog1));
        assertEquals(1, journal.getLogList().size());

        journal.removeLog(sleepLog2);
        assertFalse(journal.getLogList().contains(sleepLog2));
        assertEquals(0, journal.getLogList().size());
    }
}


