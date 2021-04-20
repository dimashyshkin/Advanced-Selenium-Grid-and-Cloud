package com.practicetestautomation.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

public class SauceLabsFactory implements SauceOnDemandAuthenticationProvider {

	private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private String browser;
	private String platform;
	private Logger log;

	private static final SauceOnDemandAuthentication AUTHENTICATION = new SauceOnDemandAuthentication(
			System.getProperty("sauce.username"), System.getProperty("sauce.accesskey"));

	public SauceLabsFactory(String browser, String platform, Logger log) {
		this.browser = browser.toLowerCase();
		this.platform = platform;
		this.log = log;
	}

	public WebDriver createDriver() {
		log.info("Creating SauceLabs instance for: " + browser + " on " + platform);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("browserVersion", "latest");
		capabilities.setCapability("platformName", platform);

		MutableCapabilities sauceOptions = new MutableCapabilities();

		if (platform.contains("Windows")) {
			sauceOptions.setCapability("screenResolution", "1920x1080");
		} else {
			sauceOptions.setCapability("screenResolution", "1920x1440");
		}

		capabilities.setCapability("sauce:options", sauceOptions);

		URL url = null;
		try {
			url = new URL("https://" + AUTHENTICATION.getUsername() + ":" + AUTHENTICATION.getAccessKey()
					+ "@ondemand.us-west-1.saucelabs.com:443/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.set(new RemoteWebDriver(url, capabilities));

		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
		return driver.get();
	}

	@Override
	public SauceOnDemandAuthentication getAuthentication() {
		return AUTHENTICATION;
	}
}
