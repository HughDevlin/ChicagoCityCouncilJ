/**
 * 
 */
package com.hughjdevlin.ccc.page;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hugh
 *
 */
public class MeetingPageTest {
	private static String url = "http://chicago.legistar.com/MeetingDetail.aspx?ID=194860&GUID=A3B1120F-B620-4A26-A9AB-E8E4A8A8254F";
	MeetingPage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new MeetingPage(new URL(url));
	}

	@Test
	public void testLegislationCount() throws MalformedURLException {
		assertEquals("legislation count", 200, page.legislation().size());
	}

	@Test
	public void testPages() {
		assertEquals("pages", 2, page.pages());
	}

	@Test
	public void testPage() throws InterruptedException {
		assertEquals("page", 1, page.page());
	}

	@Test
	public void testNext() throws InterruptedException {
		page.next();
		assertEquals("next", 2, page.page());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
