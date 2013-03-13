package com.hughjdevlin.ccc.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class VotePage extends AbstractPage {
	
	public VotePage(URL url) {
		super(url);
	}
	
	public Map<String, String> votes() {
		Map<String, String> result = new HashMap<String, String>();
		List<WebElement> trs = getRows(By.id("ctl00_ContentPlaceHolder1_gridVote_ctl00"));
		if(trs.size() > 1) { // placed on file?
		    for(WebElement row : trs) {
		    	List<WebElement> tds = row.findElements(By.tagName("td"));
		    	if(tds.size() < 2)
		    		continue;
		    	String name = tds.get(0).getText();
		    	String vote = tds.get(1).getText();
		    	result.put(name, vote);
		    }			
		}
	 	return result;
	}

}
