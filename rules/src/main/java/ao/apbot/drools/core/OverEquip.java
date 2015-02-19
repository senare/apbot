package ao.apbot.drools.core;

public class OverEquip {

    public static String get(String oe) {
        final int skill = parse(oe);
        int a = (int) (skill / 0.8);
        int b = (int) (skill * 0.8);
        return String.format("With a skill of <font color=#ffff00> %d </font>, you will be OE above <font color=#ffff00> %d </font> skill, with a requirement of <font color=#ffff00> %d </font> skill, you can have <font color=#ffff00> %d </font> without being OE.", skill, a, skill, b);
    }

    private static int parse(String level) {
        int lvl = 0;
        try {
            lvl = Integer.valueOf(level);
        } catch (Exception x) {
        }
        return lvl;
    }
}
