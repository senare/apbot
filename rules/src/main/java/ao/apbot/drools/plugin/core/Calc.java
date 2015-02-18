package ao.apbot.drools.plugin.core;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class Calc {

    private static final DoubleEvaluator evaluator = new DoubleEvaluator();

    public static String get(String eval) {
        return String.format("%s = %s", eval, evaluator.evaluate(eval));
    }
}
