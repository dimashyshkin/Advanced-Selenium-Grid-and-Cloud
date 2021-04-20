package com.practicetestautomation.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GridFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private Logger log;

	public GridFactory(String browser, Logger log) {
		this.browser = browser.toLowerCase();
		this.log = log;
	}


	public WebDriver createDriver() {
		log.info("Connecting to the node with: " + browser);
		DesiredCapabilities capabilities = new DesiredCapabilities();

		switch (browser) {
		case "chrome":
			capabilities.setBrowserName("chrome");
			break;

		case "firefox":
			capabilities.setBrowserName("firefox");
			break;

		default:
			capabilities.setBrowserName("chrome");
			break;
		}
		
		URL url = null;
		try {
			url = new URL("http://localhost:4444");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.set(new RemoteWebDriver(url, capabilities));
		
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver.get();
	}
}
