package com.hughjdevlin.ccc.page;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractWebDriverPage extends AbstractPage {
	protected WebDriver driver = new FirefoxDriver();
	
	protected AbstractWebDriverPage(URL url) {
		driver.get(url.toString());
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