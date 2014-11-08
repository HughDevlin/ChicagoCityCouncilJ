/**
 * 
 */
package com.hughjdevlin.legislature.page;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hughjdevlin.legislature.page.VotePage;

/**
 * @author hugh
 */
public class VotePageTest {
	private static String url = "HistoryDetail.aspx?ID=6688873&GUID=E6BD8439-E2BB-4310-A02F-71B71F862111";
	VotePage page;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		page = new VotePage(url);
	}

	@Test
	public void testVotesCount() {
		assertEquals("votes count", 50, page.votes().size());
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		page.close();
	}

}
