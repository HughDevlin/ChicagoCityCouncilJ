package com.hughjdevlin.legislature.page;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.hughjdevlin.legislature.Legislator;

public class LegislatorPage extends AbstractWebDriverPage {
	
	public LegislatorPage() throws MalformedURLException, IOException {
		super("People.aspx");
	}
	
	public List<Legislator> legislators() throws IOException {
		List<Legislator> result = new ArrayList<Legislator>();
	    for(WebElement tr : getRows()) {
	    	List<WebElement> tds = tr.findElements(By.tagName("td"));
	    	if(tds.size() < 2)
	    		continue;
	    	WebElement td0 = tds.get(0); // 1st column
	    	String name = normalize(td0.getText());
	    	WebElement a = td0.findElements(By.tagName("a")).get(0);
	    	String href = a.getAttribute("href");
	    	URL url = AbstractWebDriverPage.toUrl(StringUtils.substringBeforeLast(href, "&Search="));
	    	String role = normalize(tds.get(1).getText()); // 2nd column
	    	if(role.length() < 2)
	    		continue; // Carole Brown
			result.add(new Legislator(name, url, role));
	    }
		return result;
	}

}
