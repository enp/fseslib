/*
 * Copyright 2010 Eugene Prokopiev <enp@itx.ru>
 *
 * This file is part of FSESLib (FreeSWITCH Event Socket Library).
 *
 * FSESLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSESLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TXMLib. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package ru.itx.fseslib;


import junit.framework.TestCase;
import org.junit.Test;

import java.net.URL;
import java.util.Properties;

/**
 * @author Eugene Prokopiev <enp@itx.ru>
 *
 */
public class EventManagerTest {

	@Test
	public void run() {

		URL url;
		Properties params = new Properties();

		try {
			url = ClassLoader.getSystemResource("conf/fseslib.conf");
			if (url != null) {
				params.load(url.openStream());
				String enable = params.getProperty("enable");
				if (enable != null && enable.equals("yes")) {

					EventManager em = new EventManager();
					em.open(
						params.getProperty("host"),
						Integer.parseInt(params.getProperty("port")),
						params.getProperty("password"),
						"ALL",
						new EventListener() {
							public void handleEvent(Event event) {
								System.out.println(event.getDate() + " - " + event);
							}
						}
					);
					Thread.sleep(25000);
					em.close();
				}
			}
		} catch (Exception e) {
			System.out.println("Loading configuration error");
			e.printStackTrace();
		}
	}
}
