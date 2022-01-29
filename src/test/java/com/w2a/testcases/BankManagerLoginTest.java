package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException{
		
		
		verifyEquals("abc", "xyz");  //<--This is a form of soft assertion to validate the title page without using assert.assertEquals which is a hard assertion(but a method was created in the TestBase (public static void verifyEquals(String expected, String actual) for this to work. Hard assertion prevents the test from proceeding if there is a failure.
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login not successful"); // the presence of add customer button (addCustBtn_CSS) shows that the bank manager login test passed hence the use of the element
		
		log.debug("Login successfully executed");
		
		//Assert.fail("Login not successful"); <-- Hard Assertion
		
		
		
	
	
	}

}
