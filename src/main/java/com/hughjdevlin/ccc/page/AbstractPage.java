package com.hughjdevlin.ccc.page;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractPage {
	protected static final String URL_BASE = "http://chicago.legistar.com/"; 
	
	/**
	 * File portion to URL; prepend base
	 * @param url file portion
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getUrl(String url) throws MalformedURLException {
		return new URL(URL_BASE + url);
	}

	/**
	 * URL to shortened Stringl trim base
	 * @param url
	 * @return file portion
	 */
	public static String getUrl(URL url) {
		return StringUtils.substringAfter(url.toString(), URL_BASE);
	}
	
	/**
	 * replace whitespace with blank
	 * right & left trim
	 * @param s String
	 * @return
	 */
	protected static String normalize(String s) {
		return s.replaceAll("\\s", " ").trim();
	}
	
}