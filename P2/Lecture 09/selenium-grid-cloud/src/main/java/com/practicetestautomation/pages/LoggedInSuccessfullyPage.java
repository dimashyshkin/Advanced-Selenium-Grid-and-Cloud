package com.practicetestautomation.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoggedInSuccessfullyPage extends BasePageObject {

	private By logOutButton = By.xpath("//a[text()='Log out']");

	public LoggedInSuccessfullyPage(WebDriver driver, Logger log) {
		super(driver, log);
	}


	public boolean isLogOutButtonVisible() {
		return find(logOutButton).isDisplayed();
	}
}
