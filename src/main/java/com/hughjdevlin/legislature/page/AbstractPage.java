package com.hughjdevlin.legislature.page;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractPage {
	private static final String AUTHORITY = "chicago.legistar.com";
	
	/**
	 * File portion to URL; prepend base
	 * @param path file portion
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL toUrl(String path) throws MalformedURLException {
		return new URL("https://" + AUTHORITY + "/" + path);
	}

	/**
	 * trim scheme and authority prefix
	 * @param url URL to shortened 
	 * @return file portion
	 */
	public static String toPath(URL url) {
		return StringUtils.substringAfterLast(url.toString(), "/");
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