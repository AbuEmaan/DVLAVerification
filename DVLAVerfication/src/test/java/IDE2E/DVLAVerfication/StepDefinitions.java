package IDE2E.DVLAVerfication;


import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;




public class StepDefinitions {
	
	
	WebDriver driver;
	WebDriverManager webDriverManager;
	ArrayList<String> supportedMimetypefiles = new ArrayList<>();
	ArrayList<HashMap<String,String>> vehicleDetailsFromFiles;
	Helper vehicleDetailsHelper = new Helper();
	
		
	@When("^I Navigate to Get vehicle information from DVLA website$")
	public void open_my_browser() throws Throwable 
	{
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();
		
		driver.get(vehicleDetailsHelper.getProperty("url"));
		
		// screen shot
		vehicleDetailsHelper.takeScreenShot(driver);
	}
	
	
	@When("^I Click on the Start Now link$")
	public void i_Click_on_the_Start_Now_link() throws Throwable {
		
		driver.findElement(By.linkText("Start now")).click();
		// screen shot
		vehicleDetailsHelper.takeScreenShot(driver);
	    
	}
	
	
	
	@Given("^Read Vehicle Data from Files for supported mime types$")
	public void read_Vehicle_Data_from_Files_for_supported_mime_types() throws Throwable {
		
		// Get supported files from the directory
		 supportedMimetypefiles = vehicleDetailsHelper.returnSupportedFiles();
			
		// Get Vehicle info from files
		vehicleDetailsFromFiles = vehicleDetailsHelper.getVehicleDetailsFromFile(supportedMimetypefiles);
		
	}



	@Then("^Verify Vehicle Data on our files Matches what is on DVLA$")
	public void verify_Vehicle_Data_on_our_files_Matches_what_is_on_DVLA() throws Throwable {
	    
	    
			// for each vehicle check and verify details on DVLA
	 		for (HashMap<String, String> entry : vehicleDetailsFromFiles) {
	 			
	 			String Registration = entry.get("Registration").toLowerCase();
	 			String Make = entry.get("Make").toLowerCase();
	 			String Colour = entry.get("Colour").toLowerCase();
	 			
	 			// Enter the registration
	 			driver.findElement(By.id("Vrm")).sendKeys(Registration);
	 		// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			driver.findElement(By.name("Continue")).click();
	 		// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			
	 			
	 			// Confirm this is the vehicle we are looking for
	 			driver.findElement(By.id("Correct_True")).click();
	 		// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			driver.findElement(By.name("Continue")).click();
	 		// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			
	 			String DVLAMake = driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/ul/li[1]/span[2]/strong")).getText().toLowerCase();
	 			
	 			// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			
	 			String DVLAColour = driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/ul/li[9]/span[2]/strong")).getText().toLowerCase();
	 				
	 			Assert.assertEquals(Make,DVLAMake);
	 			Assert.assertEquals(Colour,DVLAColour);
	 			
	 			
	 			// return to the search screen
	 			driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div/p[2]/a")).click();
	 		// screen shot
	 			vehicleDetailsHelper.takeScreenShot(driver);
	 			
	 		   
	 		}
	 		
	 		webDriverManager.closeDriver();
	   
	}

	

}
