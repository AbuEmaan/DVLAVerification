package IDE2E.DVLAVerfication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class Helper {
	
	
	private ArrayList <HashMap<String, String>> VehicleDetails = new ArrayList<>();
	private ArrayList <String> supportedFiles = new ArrayList<>();
	
	
	public ArrayList<String> returnSupportedFiles()
	{
									
			// Instantiate Bean
			FileInformationBean finfo = new FileInformationBean();
			
			// Set the directory path
			finfo.setDirectoryPath("files");
			
			// Read supported Mime types from property file
		    String[] mTypes  = this.getProperty("supportedMimeTypes").split(",");
			finfo.setMimeType(mTypes);
			
			// Return File info for set MimeTypes
			ArrayList <HashMap<String, String>> infoList = finfo.returnFileInformation();
			
			// Return all files names that are supported into a string array list	
			for (HashMap<String, String> entry : infoList) {
			    
				supportedFiles.add(entry.get("FileName"));
				
			}
			
			
			return supportedFiles;
	}
	
	
	
	
	public ArrayList<HashMap<String,String>> getVehicleDetailsFromFile(ArrayList<String> fileNames)
	{
		for (String fileName : fileNames)
		{

			System.out.println("Reading file: "+ fileName);
			
			
			try {
				
			if(!fileName.contains(".xlsx"))
			{
				// read the file
				Scanner scn;
				
				scn = new Scanner(new File (fileName));
			
				// ignore the first header line
				scn.nextLine();
			
				//For each line entry add the details to a HashMap and add to our Vehicle Details Arraylist
				while(scn.hasNextLine())
				{
				
					String line = scn.nextLine();
					if( line != null && line != "")
					{
				
						VehicleDetails.add(ReturnVehicleInfo(line));  
					}
				}
				
				scn.close();
				
			}else {
				
				
				System.out.println("in excel");
					// For excel files
				 	File myFile = new File(fileName);
		            FileInputStream fis = new FileInputStream(myFile);

		            // Finds the workbook instance for XLSX file
		            XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

		            // Return first sheet from the XLSX workbook
		            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		            // Get iterator to all the rows in current sheet
		            Iterator<Row> rowIterator = mySheet.iterator();
		            
		            //Skip the header row
		            rowIterator.next();

		            // Traversing over each row of XLSX file
		            while (rowIterator.hasNext()) {
		            	String line = "";
		                Row row = rowIterator.next();

		                // For each row, iterate through each columns
		                Iterator<Cell> cellIterator = row.cellIterator();
		                while (cellIterator.hasNext()) {

		                    Cell cell = cellIterator.next();

		                    line = line + cell.getStringCellValue()+ ",";

		                    }
		                	
		                
		                System.out.println("line:" + line);
		                	VehicleDetails.add(ReturnVehicleInfo(line));  
		                
		                }

			}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return VehicleDetails;
	}
	
	
	
	
	public HashMap<String,String> ReturnVehicleInfo(String line)
	{
		 HashMap<String,String>  VehicleInfoFromFile = new HashMap<>();
		
		 //line split by
	     String[] carInfo=line.split(",");
	     VehicleInfoFromFile.put("Registration",carInfo[0] );
	     VehicleInfoFromFile.put("Make",carInfo[1] );
	     VehicleInfoFromFile.put("Colour",carInfo[2] );
	     
	 
	     System.out.println("Vehicle info" + VehicleInfoFromFile.toString());
		
		// return vehicle details
		return VehicleInfoFromFile;
	}
	
	
	public  String getProperty(String property) {
		
		Properties properties = new Properties();
		InputStream input = null;

		try {
			
			input = new FileInputStream("Configuration.properties");
			
			// load a properties file
			properties.load(input);

			

		} catch (final Exception exception) {
			
			exception.getMessage();
			properties = null;
		}

		if (null == properties) {
			return null;
		} else {
			return properties.getProperty(property);
		}
	}
	
	
	
	
	public void takeScreenShot(WebDriver driver) throws Exception
	{
		
		Thread.sleep(2000);
		try {
			   String fileName =  Long.toString(System.currentTimeMillis() / 1000)+  ".jpg";
		       String directory = "C:\\IDE2ETest\\DVLAVerfication\\target\\";

		        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(sourceFile, new File(directory+ fileName));
	    
	     
	      
	        
		} 
		catch (Exception e) 
		{
		    System.out.println("Exception while taking ScreenShot "+e.getMessage());
		}
		
	}

	

	
	
	
}
	
	
	


