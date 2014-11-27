package ao.apbot.codec;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeFact {

	private SimpleDateFormat format = new SimpleDateFormat("YYYYY-MM-DD T hh:mm:ss");
	private Calendar rk = null;

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
		return String.format("It is currently %s Rubi-Ka Universal Time. %s", format.format(rk.getTime()), greet());
	}

	private String greet() {
		String greet = "";
		if (rk.get(Calendar.MONTH) == Calendar.DECEMBER && rk.get(Calendar.DAY_OF_MONTH) == 24) {
			greet += "\n ##red##Merry##end## ##lime##Christmas##end## ##red##from##end## ##lime##Santa##end## ##red##Leet !!##end##";
		} else if (rk.get(Calendar.MONTH) == Calendar.OCTOBER && rk.get(Calendar.DAY_OF_MONTH) == 31) {
			greet += "\n ##darkorange##Happy Halloween from Uncle Pumpkin-head !!##end##";
		} else if (rk.get(Calendar.MONTH) == Calendar.JUNE && rk.get(Calendar.DAY_OF_MONTH) == 27) {
			greet += "\n Happy Birthday Rubi Ka";
		}
		return greet;
	}
}
