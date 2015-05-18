/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;

/**
 *
 * @author anujva
 */

public class SyslogUtility {
	private static enum LEVEL {
		alert, critical, debug, emergency, error, info, notice, warn
	}

	private final Map<LEVEL, Pattern> linePatterns = new EnumMap<>(LEVEL.class);
	private final Map<LEVEL, String> facility = new EnumMap<>(LEVEL.class);
	private SyslogConfigIF config;
	private SyslogIF syslog;

	public SyslogUtility(String instancename, String destinationip,
			String destinationport) {
		config = new UDPNetSyslogConfig(destinationip,
				Integer.parseInt(destinationport));
		try {
			syslog = Syslog.createInstance(instancename, config);
		} catch (SyslogRuntimeException e) {
			syslog = Syslog.getInstance(instancename);
		}
	}

	public void publishString(String str) {
		syslog.info(str);
	}
}
