package com.hughjdevlin.ccc.page;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractPage {
	protected WebDriver driver = new FirefoxDriver();

	protected AbstractPage(URL url) {
		driver.get(url.toString());
	}

	public void close() {
		driver.quit();
	}

	/**
	 * @return tr elements from the master table
	 */
	protected List<WebElement> getRows() {
		List<WebElement> rows = driver.findElement(By.className("rgMasterTable")).findElements(By.tagName("tr"));
		return rows;
	}

}