package com.hughjdevlin.ccc;

import java.net.URL;

public class Legislation {

	private String name;
	private String title;
	private URL url;
	private URL voteUrl;

	/**
	 * @param name
	 * @param url
	 * @param role
	 * @throws MalformedURLException 
	 */
	public Legislation(String name, String title, URL url, URL voteUrl) {
		this.name = name;
		this.title = title;
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
	 * @return the vote_url
	 */
	public URL getVoteUrl() {
		return voteUrl;
	}
}
