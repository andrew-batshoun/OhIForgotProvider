package com.org.OhIForgotProvider.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TaskTest {

	public static WebDriver driver = null;

	@SuppressWarnings("deprecation")
	@BeforeTest
	public void setup() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize(); 
	}
  
	@AfterTest
	public void cleanUp() {
		driver.close();
		driver.quit();
		
	}

	//add task test
	@Test(priority = 1)
	public void create_Task() {

		driver.get("http://localhost:8081/tasks");
		WebElement addButton = driver.findElement(By.id("addTask"));
		WebElement description = driver.findElement(By.id("description"));
		WebElement date = driver.findElement(By.id("date"));
		WebElement submit = driver.findElement(By.id("submitTask"));

		addButton.click();
		description.sendKeys("get groceries");	
		submit.click();
		
		description.sendKeys("study for exam");
		date.sendKeys("12/01/2022");
		submit.click();
		
		description.sendKeys("walk the dog");

		new Actions(driver).moveToElement(submit).pause(Duration.ofSeconds(2)).click().perform();
		
		driver.navigate().refresh();
		
		WebElement currentDescription = driver.findElement(By.id("description-0"));
		
		assertEquals(currentDescription.getText(), "get groceries");
	}

	//edit task test
	@Test(priority = 2)
	public void edit_Task() {
		driver.get("http://localhost:8081/tasks");
		WebElement editButton = driver.findElement(By.id("editTask-0"));
		
		
		new Actions(driver).moveToElement(editButton).pause(Duration.ofSeconds(2)).click().perform();
		
		WebElement description = driver.findElement(By.id("descriptionEdit-0"));
		WebElement date = driver.findElement(By.id("dateEdit-0"));
		WebElement submit = driver.findElement(By.id("submitEdit-0"));
		
		description.clear();
		description.sendKeys("this text is edited");
		
		date.sendKeys("12/01/2022");
 
		new Actions(driver).moveToElement(submit).pause(Duration.ofSeconds(2)).click().perform();
		
		driver.navigate().refresh();
		
		WebElement currentDescription = driver.findElement(By.id("description-0"));
		WebElement currentDate = driver.findElement(By.id("date-0")); 
		assertEquals(currentDescription.getText(), "this text is edited");
		assertEquals(currentDate.getText(), "12/01/2022");

	}

	//delete task test
	@Test(priority = 3)
	public void delete_Task() {
		driver.get("http://localhost:8081/tasks");
		WebElement checkBox = driver.findElement(By.id("checkBox-0"));
		checkBox.click();
		WebElement removeButton = driver.findElement(By.id("removeTask-0"));
		removeButton.click();
		
		driver.switchTo().alert().accept();
		
		driver.get("http://localhost:8081/tasks");
		
		WebElement currentDescription = driver.findElement(By.id("description-0"));
		
		assertNotEquals(currentDescription, "this text is edited");
	}
	
	//when description field is blank
	@Test(priority = 4)
	public void whenAddingTask_DescriptionIsBlank_validationError() {

		driver.get("http://localhost:8081/tasks");
		WebElement addButton = driver.findElement(By.id("addTask"));
		WebElement description = driver.findElement(By.id("description"));
		WebElement submit = driver.findElement(By.id("submitTask"));

		addButton.click();
		description.sendKeys("");
		submit.click();
		
		
		
		assertEquals(description.getAttribute("validationMessage"), "Please fill out this field." );

	}
	
	//edit task description is blank will not save blank
		@Test(priority = 5)
		public void editTask_NoDescription() {
			driver.get("http://localhost:8081/tasks");
			WebElement editButton = driver.findElement(By.id("editTask-0"));
			
			editButton.click();
			
			WebElement description = driver.findElement(By.id("descriptionEdit-0"));

			WebElement submit = driver.findElement(By.id("submitEdit-0"));
			
			description.clear();
			
	 
			submit.click();
			
			driver.get("http://localhost:8081/tasks");
			WebElement currentDescription = driver.findElement(By.id("description-0"));
			assertEquals(currentDescription.getText(), "study for exam");
			

		}

}
