package ao.apbot.codec;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeFact {

    // http://xkcd.com/1179/
    private final static SimpleDateFormat format = new SimpleDateFormat("YYYYY-MM-dd 'T' HH:mm:ss");
    static {
        format.setTimeZone(TimeZone.getTimeZone("UTZ"));
    }

    private Calendar rk = null;
    private String greet = "";
    
    public TimeFact(Calendar now) {
        this.rk = now;
        rk.add(Calendar.YEAR, 27474);
    }

    public int getMonth() {
        return rk.get(Calendar.MONTH);
    }

    public int getDay() {
        return rk.get(Calendar.DAY_OF_MONTH);
    }

    public String toString() {
        return String.format("It is currently %s Rubi-Ka Universal Time. %s", format.format(rk.getTime()), greet);
    }

    public void setGreating(String greating){
        this.greet = greating;
    }
}
