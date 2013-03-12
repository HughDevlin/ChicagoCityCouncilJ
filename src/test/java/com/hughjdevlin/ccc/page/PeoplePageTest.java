/**
 * 
 */
package com.hughjdevlin.ccc.page;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.ccc.page.PeoplePage;

/**
 * @author hugh
 *
 */
public class PeoplePageTest {
	PeoplePage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new PeoplePage();
	}

	@Test
	public void testPeopleCount() throws MalformedURLException {
		assertEquals("people count", 52, page.people().size());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
