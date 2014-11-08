/*
 * source of meeting information
 */
package com.hughjdevlin.legislature.page;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CalendarPage extends AbstractWebDriverPage {
	private final static String URL = "Calendar.aspx";
	
	public CalendarPage() throws MalformedURLException {
		super(toUrl(URL));
	}

	public MultiMap<Date, URL> meetings() throws ParseException, MalformedURLException {
		final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		MultiMap<Date, URL> result = new MultiValueMap<Date, URL>();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_lstYears_Arrow")).click();
		driver.findElement(By.className("rcbItem"));
		driver.findElement(By.xpath("//li[@class='rcbItem' and .='All Years']")).click();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_lstBodies_Arrow")).click();
		driver.findElement(By.className("rcbItem"));
		driver.findElement(By.xpath("//li[@class='rcbItem' and .='City Council']")).click();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSearch")).click();
	    for(WebElement tr : getRows()) {
	    	List<WebElement> tds = tr.findElements(By.tagName("td"));
	    	if(tds.size() < 6)
	    		continue;
	    	Date date = dateFormat.parse(tds.get(1).getText());
	    	String href = tds.get(5).findElement(By.tagName("a")).getAttribute("href");
	    	if(href == null)
	    		continue;
	    	URL url = new URL(StringUtils.substringBefore(href, "&Options=info|&Search="));
	    	result.put(date, url);
	    }
	    return result;
	}

}
