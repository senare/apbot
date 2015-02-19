package ao.apbot.drools.core;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class TimeTest {

    @Test
    @Ignore
    public void test() {
        String expected = "It is currently 29489-02-19 T 14:39:25 Rubi-Ka Universal Time. ";
        String actual = Time.get();
        assertEquals(expected, actual);
    }
}
