package ao.apbot.drools.plugin.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalcTest {

    @Test
    public void testGet5_5_25() {
        String expected = "5*5 = 25.0";
        String actual = Calc.get("5*5");
        assertEquals(expected, actual);
    }

    @Test
    public void testGet5_5_1() {
        String expected = "5/5 = 1.0";
        String actual = Calc.get("5/5");
        assertEquals(expected, actual);
    }

    @Test
    public void testGet300_08_240() {
        String expected = "300*.8 = 240.0";
        String actual = Calc.get("300*.8");
        assertEquals(expected, actual);
    }
}
