package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
@Test(dataProviderClass=TestUtil.class,dataProvider="dp")

public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException{ //NO parameters required bcos you are using hashtable. example of parameter->public void addCustomerTest(String FirstName, String LastName){
	
	if(!data.get("runmode").equals("Y")){
		
		throw new SkipException("Skipping the test case as the Run mode for data set is NO");
	}
		
		
		click("addCustBtn_CSS");
		type("firstname_XPATH",data.get("firstname"));   //"firstname" is from the header in the excel sheet
		type("lastname_XPATH",data.get("lastname"));
		type("postcode_XPATH",data.get("postcode"));
		click("addcust_XPATH");
	//	Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());  // Alert was present to show that customer registration successful, hence this was used before the Assertion. 
		// The above wait is for alert unlike implicit wait which is for presence of element
		
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		
	//	Thread.sleep(2000);
		
		
	
}



}
