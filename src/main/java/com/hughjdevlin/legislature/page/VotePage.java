package com.hughjdevlin.legislature.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.xml.sax.SAXException;

public class VotePage extends AbstractWebDriverPage {
	
	/**
	 * @param url
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws MalformedURLException 
	 */
	public VotePage(String url) throws MalformedURLException, ParserConfigurationException, IOException, SAXException {
		super(url);
	}

	/**
	 * @return map name -> vote
	 */
	public Map<String, String> votes() {
		Map<String, String> result = new HashMap<String, String>();
		for(WebElement tr : getRows()) {
			List<WebElement> tds = tr.findElements(By.tagName("td"));
	    	if(tds.size() < 2)
	    		continue;
	    	String name = normalize(tds.get(0).getText());
	    	String vote = normalize(tds.get(1).getText());
	    	result.put(name, vote);
	    } // end for
	 	return result;
	}

}
