package com.org.OhIForgotProvider.web;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class UserTest {

	public static WebDriver driver = null; 
	
	@SuppressWarnings("deprecation")
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
		driver.quit();
		driver = null; 
		
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
		
		submit.click();
		
		 new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe("http://localhost:8081/login"));
		 

		assertEquals(driver.getCurrentUrl(), "http://localhost:8081/login");
		
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
		
		new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe("http://localhost:8081/tasks"));
		
		String currentUrl = driver.getCurrentUrl();
		
		assertEquals(currentUrl, "http://localhost:8081/tasks");
	}
	
		@Test(priority = 3)
		public void whenAddingUser_UsernameIsBlank_validationError() {

			driver.get("http://localhost:8081/signup");
			WebElement username = driver.findElement(By.id("username"));
			WebElement email = driver.findElement(By.id("email"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement submit = driver.findElement(By.id("submitUser"));
			
			username.sendKeys("");
			email.sendKeys("user@email.com");
			password.sendKeys("abc123");
			
			submit.click();
			
			
			
			assertEquals(username.getAttribute("validationMessage"), "Please fill out this field." );

		}
		
		@Test (priority= 4)
		public void whenLoginUserInvalid_URL_RedirectsToLogin() {
			driver.get("http://localhost:8081/login");
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement submit = driver.findElement(By.id("submitLogin"));
			
			username.sendKeys("invalid");
			password.sendKeys("abc123");
			
			submit.click();
			
			new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlToBe("http://localhost:8081/login"));
			
			String currentUrl = driver.getCurrentUrl();
			
			assertEquals(currentUrl, "http://localhost:8081/login");
		}
		
		
		
	
}
