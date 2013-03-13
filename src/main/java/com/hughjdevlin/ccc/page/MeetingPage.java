package com.hughjdevlin.ccc.page;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.hughjdevlin.ccc.Legislation;

public class MeetingPage extends AbstractPage {
	
	public MeetingPage(URL url) {
		super(url);
	}
	
	private String getVotesHref(WebElement a) {
		a.click(); // link to votes
		String href = driver.findElement(By.name("HistoryDetail")).getAttribute("src");
		driver.switchTo().defaultContent();
		return href;
	}

	public List<Legislation> legislation() throws MalformedURLException {
		List<Legislation>  result = new ArrayList<Legislation> ();
	    for(WebElement tr : getRows()) {
	    	List<WebElement> tds = tr.findElements(By.tagName("td"));
	    	if(tds.size() < 8)
	    		continue;
	    	WebElement td = tds.get(0); // 1st column
	    	String name = td.getText();
	    	if(name.length()==0)
	    		continue;
	    	String href = td.findElement(By.tagName("a")).getAttribute("href");
	    	URL url = new URL(StringUtils.substringBeforeLast(href, "&Options=&Search="));
	    	String title = tds.get(4).getText();
	    	String votesHref = getVotesHref(tds.get(7).findElement(By.tagName("a")));
	    	if(votesHref==null)
	    		continue;
	    	URL votesUrl = new URL(votesHref);
	    	result.add(new Legislation(name, title, url, votesUrl));
	    }
	 	return result;
	}

}
