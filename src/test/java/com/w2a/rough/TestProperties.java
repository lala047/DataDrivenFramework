package com.w2a.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {   // This is to read/test the properties (Config and OR) file
	
	
	public static void main(String[] args) throws IOException {
		
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();  // Creating an object of the Properties class which is an inbuilt class inside the Java Library. config is one of the files in the property class
		Properties OR = new Properties(); // OR is the other file in the property class. OR is where the LOCATORS are stored
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		
		System.out.println(config.getProperty("browser"));
		
		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		System.out.println(OR.getProperty("bmlBtn_CSS"));
	}

}
