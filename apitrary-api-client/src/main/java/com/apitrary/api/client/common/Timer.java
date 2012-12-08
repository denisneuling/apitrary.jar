package com.apitrary.api.client.common;

import java.util.Date;

/**
 * <p>
 * Timer class.
 * </p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 *
 */
public class Timer {

	private Date start;
	private long difference = 0;

	public static Timer tic() {
		Timer timer = new Timer();
		timer.start = new Date();
		return timer;
	}

	public long toc() {
		Date end = new Date();
		difference = end.getTime() - start.getTime();

		return difference;
	}

	public long getDifference() {
		return difference;
	}
}