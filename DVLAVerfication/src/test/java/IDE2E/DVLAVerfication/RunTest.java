package IDE2E.DVLAVerfication;

import java.io.File;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", 



plugin = { 
		"pretty", "html:target/cucumber",
		"json:src/test/resources/cucumber.json" 
		}, 

monochrome 	= true, 
tags 		= { "@Regression" })


public class RunTest {
	
	private WebDriver driver;
	private WebDriverManager webDriverManager;
	private Helper helper = new Helper();
	
	@After
	public void tearDown(Scenario scenario)
	{
		if (scenario.isFailed()) 
		{
			
			webDriverManager = new WebDriverManager();
			driver = webDriverManager.getDriver();
			try {
				helper.takeScreenShot(driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  
	}
	
}

