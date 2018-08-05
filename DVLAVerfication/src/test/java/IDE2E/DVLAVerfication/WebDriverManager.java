package IDE2E.DVLAVerfication;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class WebDriverManager {

	private WebDriver driver;
	private static String driverType;
	private Helper helper = new Helper();


 
	public WebDriverManager() {

		driverType= helper.getProperty("browser");
	
	}
 
	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}
 
	
 
 
	private WebDriver createDriver() {
        switch (driverType) {	    
        case "FIREFOX": driver = new FirefoxDriver();
	    	break;
        case "CHROME" : 
        	//System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
        	driver = new ChromeDriver();
    		break;
        case "INTERNETEXPLORER" : driver = new InternetExplorerDriver();
    		break;
        }
    
        // Set driver properties
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        
		return driver;
	}	
 
	public void closeDriver() {
		driver.close();
		driver.quit();
	}
 
}
