package com.hughjdevlin.ccc.page;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CalendarPage extends AbstractPage {
	private final static String URL = "http://chicago.legistar.com/Calendar.aspx";
	private final static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public CalendarPage() throws MalformedURLException {
		super(new URL(URL));
	}

	public Map<Date, URL> meetings() throws ParseException, MalformedURLException {
		Map<Date, URL> result = new HashMap<Date, URL>();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_lstYears_Arrow")).click();
		driver.findElement(By.className("rcbItem")).click();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_lstBodies_Arrow")).click();
		driver.findElement(By.xpath("//li[@class='rcbItem ' and .='City Council']")).click();
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSearch")).click();
	    for(WebElement row : getRows()) {
	    	List<WebElement> tds = row.findElements(By.tagName("td"));
	    	if(tds.size() < 5)
	    		continue;
	    	Date date = dateFormat.parse(tds.get(1).getText());
	    	String href = tds.get(4).findElement(By.tagName("a")).getAttribute("href");
	    	if(href==null)
	    		continue;
	    	URL url = new URL(StringUtils.substringBeforeLast(href, "&Options=info|&Search="));
	    	result.put(date, url);
	    }
	 	return result;
	}

}
