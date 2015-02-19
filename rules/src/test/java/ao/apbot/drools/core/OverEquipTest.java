package ao.apbot.drools.core;

import static org.junit.Assert.*;

import org.junit.Test;

import ao.apbot.drools.core.OverEquip;

public class OverEquipTest {

    @Test
    public void test199() {
        String expected = "With a skill of <font color=#ffff00> 199 </font>, you will be OE above <font color=#ffff00> 248 </font> skill, with a requirement of <font color=#ffff00> 199 </font> skill, you can have <font color=#ffff00> 159 </font> without being OE.";
        String actual = OverEquip.get("199");
        assertEquals(expected, actual);
    }

    @Test
    public void testNotNumber() {
        String expected = "With a skill of <font color=#ffff00> 0 </font>, you will be OE above <font color=#ffff00> 0 </font> skill, with a requirement of <font color=#ffff00> 0 </font> skill, you can have <font color=#ffff00> 0 </font> without being OE.";
        String actual = OverEquip.get("this is not a number");
        assertEquals(expected, actual);
    }
}
