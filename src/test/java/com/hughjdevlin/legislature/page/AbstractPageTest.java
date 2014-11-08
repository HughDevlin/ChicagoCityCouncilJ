/**
 * 
 */
package com.hughjdevlin.legislature.page;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hughjdevlin.legislature.page.AbstractPage;

/**
 * @author hugh
 *
 */
public class AbstractPageTest {
	final static String host = "https://chicago.legistar.com/";
	final static String path = "PersonDetail.aspx?ID=64196&GUID=0254E721-8ED0-4EAD-9BFF-2B927E07B0B9";
	static URL url;

	/**
	 * @throws MalformedURLException 
	 */
	@BeforeClass
	public static void setUp() throws MalformedURLException  {
		url = new URL(host + path);
	}

	@Test
	public void testToUrl() throws MalformedURLException {
		assertEquals("to path", url.toString(), AbstractPage.toUrl(path).toString());
	}

	@Test
	public void testToPath() {
		assertEquals("to path", path, AbstractPage.toPath(url));
	}

}
