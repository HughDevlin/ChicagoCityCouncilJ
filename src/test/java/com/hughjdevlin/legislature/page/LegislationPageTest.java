/**
 * 
 */
package com.hughjdevlin.legislature.page;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.legislature.page.LegislationPage;

/**
 * @author hugh
 */
public class LegislationPageTest {
	private static String url = "LegislationDetail.aspx?ID=1271504&GUID=EB4A3ED4-DD2C-488E-BD27-2386E9A2267D";
	LegislationPage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new LegislationPage(url);
	}

	@Test
	public void testSponsorCount() {
		assertEquals("sponsor count", 2, page.sponsors().size());
	}

	@Test
	public void testPdf() {
		assertEquals("pdf url", "https://chicago.legistar.com/View.ashx?M=F&ID=2282443&GUID=2ACA9159-E392-463F-9E32-214BCB4EE267", page.pdf());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
