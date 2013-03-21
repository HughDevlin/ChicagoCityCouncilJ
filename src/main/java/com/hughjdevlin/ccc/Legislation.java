package com.hughjdevlin.ccc;

import java.net.URL;

public class Legislation {

	private final String name;
	private final String type;
	private final String title;
	private final String status;
	private final String result;
	private final URL url;
	private final URL voteUrl;

	/**
	 * @param name
	 * @param url
	 * @param role
	 * @throws MalformedURLException 
	 */
	public Legislation(String name, String type, String title, String status, String result, URL url, URL voteUrl) {
		this.name = name;
		this.type = type;
		this.title = title;
		this.status = status;
		this.result = result;
		this.url = url;
		this.voteUrl = voteUrl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the vote_url
	 */
	public URL getVoteUrl() {
		return voteUrl;
	}
}
