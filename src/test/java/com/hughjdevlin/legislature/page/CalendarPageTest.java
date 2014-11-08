/**
 * 
 */
package com.hughjdevlin.legislature.page;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.collections4.MultiMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.legislature.page.CalendarPage;

/**
 * @author hugh
 *
 */
public class CalendarPageTest {
	CalendarPage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new CalendarPage();
	}

	@Test
	public void testCounts() throws ParseException, MalformedURLException {
		MultiMap<Date, URL> meetings = page.meetings();
		assertEquals("dates count", 60, meetings.size());
		assertEquals("meetings count", 61, meetings.values().size());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
