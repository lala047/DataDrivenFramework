package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.w2a.listeners.CustomListeners;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

		//again test2
		/*
		 * WebDriver - done Properties - done 
		*Logs - log4j jar (downloaded to the pom.xml), .log (auto generated), log4j.properties (pasted to logs package), Logger (eclipse class which an object of it is created by the testlogs to read log4j)
		*ExtentReports 
		*DB 
		*Excel 
		*Mail 
		*ReportNG, ExtentReports 
		*Jenkins
		 * 
		 */

		public static WebDriver driver;
		public static Properties config = new Properties();
		public static Properties OR = new Properties();
		public static FileInputStream fis;
		public static Logger log = Logger.getLogger(TestBase.class);
		public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
		public static WebDriverWait wait;  //explicit wait

		public static String browser;

		@BeforeSuite     // This is executed before any test cases is called

		public void setUp() {

			if (driver == null) {  // This means all things being equal i.e when everything is going as planned)
				
				PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");

				try {     // add throws declaration or surround with try catch block
					fis = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					config.load(fis);
					log.debug("Config file loaded !!!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					fis = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					OR.load(fis);
					log.debug("OR file loaded !!!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){   //This code is to enable the Jenkins to run on your chosen Browser and environment
					
					browser = System.getenv("browser");
				}else{
					
					browser = config.getProperty("browser");
					
				}
				
				config.setProperty("browser", browser);
				
				
				

				if (config.getProperty("browser").equals("firefox")) {

					// System.setProperty("webdriver.gecko.driver", "gecko.exe"); //<-- add this to (driver = new FirefoxDriver() below ( as used here) if you are using the latest 3 release of geckodriver use only (driver = new FirefoxDriver()) if you are using other versions/releases
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();

				} else if (config.getProperty("browser").equals("chrome")) {

					/*System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				*/	
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					log.debug("Chrome Launched !!!");
				} else if (config.getProperty("browser").equals("ie")) {

					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();

				}

				driver.get(config.getProperty("testsiteurl"));
				log.debug("Navigated to : " + config.getProperty("testsiteurl"));
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),TimeUnit.SECONDS); // the implicit wait is defined in the config.getProperty
				wait = new WebDriverWait(driver, 5);  //explicit wait (used bcos of presence of alert. Implicit wait is for presence of element

			}

		}
    //CREATING USABLE FUNCTIONS FOR PARAMETARIZATION OF YOUR TESTS
		
		public void click(String locator) {

			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
			CustomListeners.testReport.get().log(Status.INFO, "Clicking on : " + locator);
		}

		public void type(String locator, String value) {

			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
			}

			CustomListeners.testReport.get().log(Status.INFO, "Typing in : " + locator + " entered value as " + value);

		}
		
		static WebElement dropdown;    

		public void select(String locator, String value) {  //DROPDOWN GOES WITH SELECT

			if (locator.endsWith("_CSS")) {
				dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator))); // take note of the dropdown" =" driver.findelement.. unlike the others that has driver.findelement without having "="
			} else if (locator.endsWith("_XPATH")) {
				dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				dropdown = driver.findElement(By.id(OR.getProperty(locator)));
			}
			
			Select select = new Select(dropdown); //DROPDOWN GOES WITH SELECT
			select.selectByVisibleText(value);

			CustomListeners.testReport.get().log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

		}

		public boolean isElementPresent(By by) {  // This is created so as to assert that Element is present

			try {

				driver.findElement(by);
				return true;

			} catch (NoSuchElementException e) {

				return false;

			}

		}
	//This method prompts the test to continue even after a failure when using a HARD ASSERTION because a hard assertion presents the test from proceeding to another test if there is a failure unlike a soft assertion.

		public static void verifyEquals(String expected, String actual) throws IOException {

			try {

				Assert.assertEquals(actual, expected);

			} catch (Throwable t) {

				
				
				
				TestUtil.captureScreenshot();
				// ReportNG
				Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
				Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
						+ " height=200 width=200></img></a>");
				Reporter.log("<br>");
				Reporter.log("<br>");
				
				// Extent Reports
				CustomListeners.testReport.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
				CustomListeners.testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName)
						.build());

			}

		}

		@AfterSuite   // This is executed after all test cases are called
		public void tearDown() {

			if (driver != null) {
				driver.quit();
			}

			log.debug("test execution completed !!!");
		}
	}


