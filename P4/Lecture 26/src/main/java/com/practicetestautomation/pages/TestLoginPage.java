package com.practicetestautomation.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestLoginPage extends BasePageObject {

	private By usernameLocator = By.id("username");
	private By passwordLocator = By.id("password");
	private By submitButtonLocator = By.id("submit");
	private By errorMessageLocator = By.id("error");

	private String url = "https://practicetestautomation.com/practice-test-login/";

	public TestLoginPage(WebDriver driver, Logger log) {
		super(driver, log);
	}


	public TestLoginPage open() {
		driver.get(url);
		return this;
	}


	/** Execute log in */
	public LoggedInSuccessfullyPage logIn(String username, String password) {
		executeLogin(username, password);
		return new LoggedInSuccessfullyPage(driver, log);
	}


	public void negativeLogIn(String username, String password) {
		executeLogin(username, password);
	}


	private void executeLogin(String username, String password) {
		log.info("Executing LogIn with username [" + username + "] and password [" + password + "]");
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(submitButtonLocator);
	}


	/** Wait for error message to be visible on the page */
	public void waitForErrorMessage() {
		waitForVisibilityOf(errorMessageLocator);
	}


	public String getErrorMessageText() {
		return find(errorMessageLocator).getText();
	}

}
