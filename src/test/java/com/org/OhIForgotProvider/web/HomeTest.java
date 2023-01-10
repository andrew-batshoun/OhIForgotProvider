package com.org.OhIForgotProvider.web;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HomeTest {

	public static WebDriver driver = null; 
	
	@BeforeTest
	public void setup() throws Exception{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@AfterTest
    public void cleanUp() { 
		driver.close();
    }
	
	@Test
	public void getWelcomePage() {
		driver.get("http://localhost:8081/");
		WebElement result = driver.findElement(By.className("display-5"));
		String expected = "What did you forget?";
		
		assertEquals(result.getText(), expected);
	
	}
	
	
	
	
}
