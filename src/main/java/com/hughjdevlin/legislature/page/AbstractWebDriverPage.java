package com.hughjdevlin.legislature.page;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractWebDriverPage extends AbstractPage {
	protected WebDriver driver = new FirefoxDriver();
	
	/**
	 * WebDriver
	 * http://docs.seleniumhq.org/docs/03_webdriver.jsp
	 * http://selenium.googlecode.com/svn/trunk/docs/api/java/index.html
	 * 
	 * @param url
	 */
	protected AbstractWebDriverPage(URL url) {
		driver.get(url.toString());
	}

	public AbstractWebDriverPage(String url) throws MalformedURLException, IOException {
		this(toUrl(url));
	}

	public void close() {
		driver.quit();
	}
	
	protected WebElement getMasterTable() {
		return driver.findElement(By.className("rgMasterTable"));
	}

	/**
	 * @return tr elements from an element
	 */
	protected List<WebElement> getRows(WebElement webElement) {
		return webElement.findElements(By.tagName("tr"));
	}

	protected List<WebElement> getRows(String id) {
		return getRows(driver.findElement(By.id(id)));	
	}

	/**
	 * @return tr elements from an element
	 */
	protected List<WebElement> getRows(By by) {
		return getRows(driver.findElement(by));
	}

	/**
	 * @return tr elements from the master table
	 */
	protected List<WebElement> getRows() {
		return getRows(getMasterTable());
	}
	
	public String getPageSource() {
		return driver.getPageSource();
	}

}