/**
 * 
 */
package com.hughjdevlin.ccc.page;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.ccc.page.CalendarPage;

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
	public void testMeetingCount() throws ParseException, MalformedURLException {
		assertEquals("meetings count", 35, page.meetings().size());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
