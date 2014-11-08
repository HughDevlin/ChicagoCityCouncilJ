/**
 * 
 */
package com.hughjdevlin.legislature.page;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.legislature.page.LegislatorPage;

/**
 * @author hugh
 */
public class LegislatorPageTest {
	LegislatorPage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new LegislatorPage();
	}

	@Test
	public void testLegislatorCount() throws IOException {
		assertEquals("legislator count", 52, page.legislators().size());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
