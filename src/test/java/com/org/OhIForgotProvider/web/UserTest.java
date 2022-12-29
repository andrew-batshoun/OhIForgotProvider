package com.org.OhIForgotProvider.web;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserTest {
public static WebDriver driver = null; 
	


	@BeforeTest
	public void setup() throws Exception{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().window().maximize();
	}
	
	@AfterTest
    public void cleanUp() {
		driver.close();
    }
	
	@Test (priority=1)
	public void register_User() {
		driver.get("http://localhost:8081/signup");
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.id("submitUser"));
		
		username.sendKeys("user");
		email.sendKeys("user@email.com");
		password.sendKeys("abc123");
		
		new Actions(driver).moveToElement(submit).pause(Duration.ofSeconds(3)).click().perform();
		
		
		
	}
	
	@Test (priority=2)
	public void login_User() {
		driver.get("http://localhost:8081/login");
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement submit = driver.findElement(By.id("submitLogin"));
		
		username.sendKeys("user");
		password.sendKeys("abc123");
		
		submit.click();
		
		String currentUrl = driver.getCurrentUrl();
		assertEquals(c, "http://localhost:8081:/tasks");
	}
}
