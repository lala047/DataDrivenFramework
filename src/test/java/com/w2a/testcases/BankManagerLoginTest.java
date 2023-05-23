package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException{
		
	
		
		click("bmlBtn_XPATH");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login not successful"); // the presence of add customer button (addCustBtn_CSS) shows that the bank manager login test passed hence the use of the element
		
		log.debug("Login successfully executed");
		
		
		
		
	
	
	}

}
