package com.hughjdevlin.ccc.page;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.hughjdevlin.ccc.Person;



public class PeoplePage extends AbstractPage {
	private static final String URL = "http://chicago.legistar.com/People.aspx";

	public PeoplePage() throws MalformedURLException {
		super(new URL(URL));
	}
	
	public List<Person> people() throws MalformedURLException {
		List<Person> result = new ArrayList<Person>();
	    for(WebElement tr : getRows()) {
	    	List<WebElement> tds = tr.findElements(By.tagName("td"));
	    	if(tds.size() < 2)
	    		continue;
	    	WebElement td = tds.get(0); // 1st column
	    	String name = td.getText();
	    	String href = td.findElement(By.tagName("a")).getAttribute("href");
	    	URL url = new URL(StringUtils.substringBeforeLast(href, "&Search="));
	    	String role = tds.get(1).getText(); // 2nd column
			result.add(new Person(name, url, role));
	    }
		return result;
	}

}
