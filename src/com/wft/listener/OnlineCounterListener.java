package com.wft.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineCounterListener implements HttpSessionListener {
	public static long online=0;

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		online++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		online--;
	}

}
