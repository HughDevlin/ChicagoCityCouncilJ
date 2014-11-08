/*
 * source of sponsors
 */
package com.hughjdevlin.legislature.page;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.xml.sax.SAXException;

public class LegislationPage extends AbstractWebDriverPage {
	
	/**
	 * @param url
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws MalformedURLException 
	 */
	public LegislationPage(String url) throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		super(url);
	}
	
	public List<String> sponsors() {
		List<String> result = new ArrayList<String>();
		List<WebElement> trs = getRows("ctl00_ContentPlaceHolder1_tblSponsors");
    	WebElement tr = trs.get(0);
    	List<WebElement> as = tr.findElements(By.tagName("a"));
	    for(WebElement a : as) { // skip label
	    	String name = normalize(a.getText());
			result.add(name);
	    }
		return result;
	}

	public String pdf() {
		List<WebElement> trs = getRows("ctl00_ContentPlaceHolder1_tblAttachments");
    	WebElement tr = trs.get(0);
    	List<WebElement> as = tr.findElements(By.tagName("a"));
    	WebElement a = as.get(0);
	    return a.getAttribute("href");
	}

}
