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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Eugene Prokopiev <enp@itx.ru>
 *
 */
public class Event {

	private Map<String,String> params = new TreeMap<String,String>();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void add(String line) {
		String param[] = line.split(":");
		try {
			params.put(param[0].trim(), URLDecoder.decode(param[1].trim(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			params.put(param[0].trim(), param[1].trim());
		}
	}

	public String getName() {
		return params.get("Event-Name");
	}

	public Date getDate() {
		try {
			return format.parse(params.get("Event-Date-Local"));
		} catch (ParseException e) {
			return null;
		}
	}

	public String toString() {
		return "Event:"+params;
	}
}
