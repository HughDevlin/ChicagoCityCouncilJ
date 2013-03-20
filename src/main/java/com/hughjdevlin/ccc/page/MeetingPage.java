/*
 * source of legislation information
 * page-able
 * 
 */
package com.hughjdevlin.ccc.page;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hughjdevlin.ccc.Legislation;

public class MeetingPage extends AbstractWebDriverPage {
	
	public MeetingPage(URL url) {
		super(url);
	}
	
	public List<Legislation> legislation() throws MalformedURLException {
		List<Legislation> returnValue = new ArrayList<Legislation> ();
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
	    	String status = tds.get(5).getText();
	    	String result = tds.get(6).getText();    	
	    	String votesHref = StringUtils.substringBefore(StringUtils.substringAfter(tds.get(7).findElement(By.tagName("a")).getAttribute("onclick"), "'"), "'");
	    	if(votesHref==null)
	    		continue;
	    	URL votesUrl = getUrl(votesHref);
	    	returnValue.add(new Legislation(name, title, status, result, url, votesUrl));
	    }
	 	return returnValue;
	}

	public int pages() {
		return driver.findElement(By.className("rgPager")).findElements(By.tagName("a")).size();
	}
	
	public int page() throws InterruptedException {
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.className("rgMasterTable")));
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.className("rgCurrentPage")));
		Thread.sleep(10000);
		getRows(); // delay
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.className("rgMasterTable")));
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.presenceOfElementLocated(By.className("rgCurrentPage")));
		Wait wait = new WebDriverWait(driver, 120);
		ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.findElement(By.className("rgCurrentPage")).getText().length() > 0;
			}
		};
		wait.until(condition);
		String text = getMasterTable().findElement(By.className("rgCurrentPage")).getText();
		int page = Integer.parseInt(text);
		return page;
	}
	
	public void next() throws InterruptedException {
		int page = page(); // one based
		WebElement tr = driver.findElement(By.className("rgPager"));
		List<WebElement> as = tr.findElements(By.tagName("a"));
		WebElement a = as.get(page); // zero based
		a.click();
	}

}
