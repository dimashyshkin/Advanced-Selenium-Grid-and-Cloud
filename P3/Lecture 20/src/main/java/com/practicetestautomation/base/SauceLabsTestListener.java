package com.practicetestautomation.base;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.saucelabs.saucerest.SauceREST;

public class SauceLabsTestListener extends TestListenerAdapter {
	private boolean sauce; 
	private String sessionId;
	private SauceREST sauceREST;
	
	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		sauce = (boolean) result.getTestContext().getAttribute("sauce"); 	
		if (sauce) {
			this.sessionId = (String) result.getTestContext().getAttribute("sessionId");
			this.sauceREST = new SauceREST(System.getProperty("sauce.username"), System.getProperty("sauce.accesskey"));
			
		}
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		if (sauce) {
			sauceREST.jobPassed(sessionId);
		}
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		if (sauce) {
			sauceREST.jobFailed(sessionId);
		}		
	}

}
