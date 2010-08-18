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

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Eugene Prokopiev <enp@itx.ru>
 *
 */
public class EventManager {

	private Socket eventSocket;
	private BufferedReader eventSocketReader;
	private PrintWriter eventSocketWriter;

	public void open(String host, int port, String password, String events, EventListener eventListener) {
		try {
			eventSocket = new Socket(InetAddress.getByName(host), port);
			eventSocketReader = new BufferedReader(new InputStreamReader(eventSocket.getInputStream()));
			eventSocketWriter = new PrintWriter(eventSocket.getOutputStream(), true);
			eventSocketWriter.println("auth " + password + "\n\n");
			eventSocketWriter.println("event plain " + events + "\n\n");
			new Thread(new EventReader(eventListener, eventSocketReader)).start();
		} catch (Exception e) {
			throw new Error("Event socket open error : "+e);
		}
	}

	public void close() {
		try {
			eventSocket.close();
		} catch (IOException e) {
			throw new Error("Event socket close error : "+e);
		}
	}
}
