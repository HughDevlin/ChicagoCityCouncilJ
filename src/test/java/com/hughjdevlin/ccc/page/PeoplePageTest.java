/**
 * 
 */
package com.hughjdevlin.ccc.page;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.hughjdevlin.ccc.page.PeoplePage;

/**
 * @author hugh
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
	public void testPeopleCount() throws IOException {
		assertEquals("people count", 52, page.persons().size());
	}

}
