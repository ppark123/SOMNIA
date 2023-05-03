package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The following code for Json was referred from JsonSerializationDemo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

class SleepLogTest {
    private SleepLog sleepLog1;
    private SleepLog sleepLog2;
    private SleepLog sleepLog3;
    private SleepLog sleepLog4;

    @BeforeEach
    void runBefore(){
        sleepLog1 = new SleepLog("01/12/22");
        sleepLog2 = new SleepLog("01/14/22");
        sleepLog3 = new SleepLog("03/24/22");
        sleepLog4 = new SleepLog("05/04/22");
    }

    @Test
    // tests an empty sleep log for any given date.
    void testEmptySleepLog(){
        assertEquals("01/12/22", sleepLog1.getDate());
        assertEquals(":00", sleepLog1.getSleepHour());
        assertEquals(":00", sleepLog1.getWakeUpHour());
        assertEquals(" hours", sleepLog1.getTotalHour());

        assertEquals("/4", sleepLog1.getAnswer1());
        assertEquals(":00", sleepLog1.getAnswer2());
        assertEquals("", sleepLog1.getAnswer3());
        assertEquals("", sleepLog1.getAnswer4());
        assertEquals("", sleepLog1.getAnswer5());
        assertEquals("", sleepLog1.getNotes());
    }

    @Test
    void testSetHours(){
        sleepLog1.setSleepHour("0");
        sleepLog1.setWakeHour("0");
        assertEquals("0:00", sleepLog1.getSleepHour());
        assertEquals("0:00", sleepLog1.getWakeUpHour());

        sleepLog2.setSleepHour("12");
        sleepLog2.setWakeHour("12");
        assertEquals("12:00", sleepLog2.getSleepHour());
        assertEquals("12:00", sleepLog2.getWakeUpHour());

        sleepLog3.setSleepHour("23");
        sleepLog3.setWakeHour("23");
        assertEquals("23:00", sleepLog3.getSleepHour());
        assertEquals("23:00", sleepLog3.getWakeUpHour());
    }

    @Test
    void testTotalHours() {
        //test for sleepHour > wakeHour
        sleepLog1.setSleepHour("22");
        sleepLog1.setWakeHour("9");
        sleepLog1.calculateTotalHour();
        assertEquals("11 hours", sleepLog1.getTotalHour());

        //test for sleepHour < wakeHour
        sleepLog2.setSleepHour("0");
        sleepLog2.setWakeHour("9");
        sleepLog2.calculateTotalHour();
        assertEquals("9 hours", sleepLog2.getTotalHour());

        //test for sleepHour = wakeHour
        sleepLog3.setSleepHour("22");
        sleepLog3.setWakeHour("22");
        sleepLog3.calculateTotalHour();
        assertEquals("0 hours", sleepLog3.getTotalHour());

        // another test for sleepHour > wakeHour at max totalHour
        sleepLog4.setSleepHour("22");
        sleepLog4.setWakeHour("21");
        sleepLog4.calculateTotalHour();
        assertEquals("23 hours", sleepLog4.getTotalHour());
    }

    @Test
    void testSetAnswers(){
        sleepLog1.setAns1("3");
        sleepLog1.setAns2("21");
        sleepLog1.setAns3("0");
        sleepLog1.setAns4("1");
        sleepLog1.setAns5("caffeine");
        sleepLog1.setNote("Took a nap for 30 minutes at 3:00PM.");

        assertEquals("3/4", sleepLog1.getAnswer1());
        assertEquals("21:00", sleepLog1.getAnswer2());
        assertEquals("0", sleepLog1.getAnswer3());
        assertEquals("1", sleepLog1.getAnswer4());
        assertEquals("caffeine", sleepLog1.getAnswer5());
        assertEquals("Took a nap for 30 minutes at 3:00PM.", sleepLog1.getNotes());
    }
}