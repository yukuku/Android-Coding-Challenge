package com.carousell.codingchallenge.util;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;

public class Formatter {
	public static String formatTime(final int time) {
		final int delta = (int) (System.currentTimeMillis() / 1000L) - time;
		if (delta >= DAYS.toSeconds(366)) {
			final int y = (int) (delta / 86400.0 / 365.25);
			return y == 1 ? "1 year" : (y + " years");
		} else if (delta >= DAYS.toSeconds(31)) {
			final int y = (int) (delta / 86400.0 / 30.44);
			return y == 1 ? "1 month" : (y + " months");
		} else if (delta >= HOURS.toSeconds(24)) {
			final int y = (int) (delta / 86400.0);
			return y == 1 ? "1 day" : (y + " days");
		} else {
			return "0 days";
		}
	}
}
