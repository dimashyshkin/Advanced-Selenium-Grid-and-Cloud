package com.practicetestautomation.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GridFactory {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private String platform;
	private Logger log;

	public GridFactory(String browser, String platform, Logger log) {
		this.browser = browser.toLowerCase();
		this.platform = platform;
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

		case "safari":
			capabilities.setBrowserName("safari");
			break;

		default:
			capabilities.setBrowserName("chrome");
			break;
		}
		
		
		switch (platform) {
		case "WIN10":
			capabilities.setPlatform(Platform.WIN10);
			break;

		case "MAC":
			capabilities.setPlatform(Platform.MAC);
			break;

		default:
			capabilities.setPlatform(Platform.WIN10);
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
