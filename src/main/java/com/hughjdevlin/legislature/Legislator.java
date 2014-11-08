package com.hughjdevlin.legislature;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;

public class Legislator {
	public static int MAYOR = 51;
	public static int CLERK = 52;
	private String name;
	private URL url;
	private int role;

	/**
	 * @param name
	 * @param url
	 * @param role
	 */
	public Legislator(String name, URL url, String role) {
		this.name = name;
		this.url = url;
		if(StringUtils.isNumeric(role))
			this.role = Integer.parseInt(role);
		else if(role.equals("Mayor"))
			this.role = 51;
		else if(role.equals("Clerk"))
			this.role = 52;
		else
			throw new IllegalArgumentException("Role unrecognized: " + role);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}
}
