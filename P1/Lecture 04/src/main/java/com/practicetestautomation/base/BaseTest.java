package com.practicetestautomation.base;

import java.lang.reflect.Method;

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

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, ITestContext ctx) {
		log = LogManager.getLogger(ctx.getCurrentXmlTest().getSuite().getName());
		driver = new BrowserDriverFactory(browser, log).createDriver();
		driver.manage().window().maximize();
	}


	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		log.info("Close driver");
		driver.quit();
	}

}
