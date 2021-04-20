package com.practicetestautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

	protected WebDriver driver;
	protected Logger log;

	@Parameters({ "browser", "environment", "platform" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, @Optional("local") String environment, @Optional("WIN10") String platform, ITestContext ctx) {
		log = LogManager.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
		setProperties();

		switch (environment) {
		case "local":
			driver = new BrowserDriverFactory(browser, log).createDriver();
			break;

		case "grid":
			driver = new GridFactory(browser, platform, log).createDriver();
			break;

		case "sauce":
			driver = new SauceLabsFactory(browser, platform, log).createDriver();
			break;

		default:
			driver = new BrowserDriverFactory(browser, log).createDriver();
			break;
		}
		
		driver.manage().window().maximize();
	}

	private void setProperties() {
		Properties props = System.getProperties();
		try {
			props.load(new FileInputStream(new File("src/main/resources/test.properties")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		log.info("Close driver");
		driver.quit();
	}

}
