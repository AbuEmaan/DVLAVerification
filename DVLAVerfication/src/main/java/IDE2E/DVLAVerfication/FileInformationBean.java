package IDE2E.DVLAVerfication;

import java.io.File;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.activation.MimetypesFileTypeMap;
import java.util.HashMap;


public class FileInformationBean implements Serializable {
	
private String directoryPath;
private String[] mimeType;
private ArrayList<HashMap<String, String>> filesInformation =new ArrayList<>();


// Default constructor for Bean
public FileInformationBean()
{
	
}


// Set the path to the directory
public String getDirectoryPath()
{
	
	return this.directoryPath;
}

public void  setDirectoryPath(String directoryPath)
{
	
	this.directoryPath = directoryPath;
}


public String[] getMimeType()
{
	return this.mimeType;
}

public void setMimeType(String[] mimeType)
{
	
	this.mimeType = mimeType;
}

// Return an ArrayList of HashMaps containing file information
public ArrayList<HashMap<String, String>> returnFileInformation()
{

	File folder = new File(this.directoryPath);
	
	// Return a list of all files from the directory
	File[] listOfFiles = folder.listFiles();
	
	// Read in file info for all files and store in a Hash Table 	
	for (int i = 0; i < listOfFiles.length; i++) 
	{
		// Instantiate a Map to hold our file information
		HashMap<String, String> FileInfo = new HashMap<>();
		
		if (listOfFiles[i].isFile()) 
		{
			
			File file = listOfFiles[i];
			
			// file name
			FileInfo.put("FileName", file.getAbsolutePath());
			
			
			//file size in bytes
			FileInfo.put("FileSize", String.valueOf(file.length()));
			
			//File extension
			String extension = "";

			int index = file.getName().lastIndexOf('.');
			if (index > 0) {
			    extension = file.getName().substring(index);
			}
			FileInfo.put("FileExtension", extension);
			
			// FileMimeType			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	        if( mimeType == null)
	        {
	        	mimeType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(file.getName());
	        }
	        
			FileInfo.put("FileMimeType", mimeType);
			
			
			// Only add supported Mime Types
			// Add the file info for supported mime types only  for to the array list
			
			System.out.println("Files found in directory matching MimeType '"+ mimeType + "' File Name:"+ file.getAbsolutePath());
			
			//NOTE: I was having a problem with getting the correct mime type back for excel and csv files(Problem was my machine setup)  hence using extension here
			if(doesValueExist(this.mimeType,mimeType) && (extension.contains("csv") || extension.contains("xlsx")))
			{
				System.out.println("Files found in directory matching MimeType '"+ mimeType + "' File Name:"+ file.getAbsolutePath());
				filesInformation.add(FileInfo);
				
			}

	}
	
}

	return filesInformation;
}

private  static boolean doesValueExist(String[] mimeTypesArray, String mimeTypeStr) 
{
	
    for (String s: mimeTypesArray) {
        if (s.equals(mimeTypeStr))
            return true;
    }
    return false;
}


}
