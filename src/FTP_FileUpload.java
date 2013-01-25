import it.sauronsoftware.ftp4j.FTPClient;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class uploads file in the local machine to the remote FTP Server using FTP4j client library
 */
public class FTP_FileUpload
{
        public FTP_FileUpload(List<xmlItem> xmlItems)
        {
                // Assignment
                String ipAddress = "50.22.14.114";
                String userName = "alanwrig";
                String password = "@123Victory";

                System.out.println("Ip Address = " + ipAddress);
                System.out.println("User = " + userName);
                System.out.println("Pass = " + password);

                // FTP Program operations start from here
                FTPClient client = null;
                try
                {
                        // Get the FTP Connection from the Utility class
                        client = FTPUtility.connect(ipAddress, userName, password);

                        if (client != null)
                        {
                                try
                                {
                                	/* Change the directory */
                                    client.changeDirectory("/public_html/comics/test"); // This operation is similar to "cd test"
                                    //System.out.println("Current Working directory = " + client.currentDirectory());
                                    
                                    //Loop through all comics
                                    int count = 0;
                                    for(xmlItem x : xmlItems){
                                    	
	                                    //Define the File with complete path to be uploaded
                                    	System.out.println(x.getImgURL());
                                    	String extension = x.getImgExtension();
                                    	String folder = extension.substring(0, extension.indexOf("/"));
	                                    File fileUpload = new File(extension);
	                                    System.out.println("Uploading the " + extension + " " + folder + " to Remote Machine");
	                                    
	                                    //Get directories
	                                    String[] dir = client.listNames();
	                                    boolean found = false;
	                                    for(String s : dir){
	                                    	
	                                    	//Check if the directory exists
	                                    	if(s.equals(folder)){
	                                    		found = true;
	                                    		break;
	                                    	}
	                                    }
	                                    
	                                    //Create directory if you need to
	                                    if(!found)
	                                    	client.createDirectory(folder);
	                                    
	                                    //Navigate to date directory
	                                    client.changeDirectory(folder);
	                                    
	                                    //Get directory 
	                                    dir = client.listNames();
	                                    found = false;
	                                    String title = x.getGenericTitle().replace(" ", "") + ".jpg";
	                                    System.out.println(title);
	                                    for(String s : dir){
	                                    	
	                                    	//Check if the image exists
	                                    	if(s.equals(title)){
	                                    		found = true;
	                                    		break;
	                                    	}
	                                    }
	                                    
	                                    //File not found on server
	                                    if(!found){
	                                    	
	                                    	//Download image
                        					try{
                        						//Create directory
                    							new File(x.getDigitalPubDateNum()).mkdir();
                    							
                        						//DL
                        						BufferedImage image = ImageIO.read(new URL(x.getImgDlURL()));
                        						ImageIO.write(image, "jpg" , new File(extension));

                        					}catch(IOException e){
                        						System.out.println("Could not DL image file");
                        						e.printStackTrace();
                        					}

	                                    	//Upload
	                                    	client.upload(fileUpload);
	                                    	System.out.println("Successfully Uploaded the " + extension + " File to Remote Machine");
	                                    }
	                                    else{
	                                    	System.out.println(extension + " already exists on the Remote Machine.");
	                                    }
	                                    
	                                    //Go back up
	                                    client.changeDirectoryUp();
	                                    
	                                    if(count > 10)
	                                    	break;
	                                    count++;
                                    }
                                }
                                catch (Exception e)
                                {
                                        System.err.println("ERROR : Error in Transfering File to Remote Machine");
                                        System.exit(3);
                                }
                        }
                }
                catch (Exception e)
                {
                        System.err.println("ERROR : Error in Connecting to Remote Machine");
                        System.exit(1);
                }

                finally
                {
                        if (client != null)
                        {
                                try
                                {
                                        client.disconnect(true);
                                }
                                catch (Exception e)
                                {
                                        System.err.println("ERROR : Error in disconnecting the Remote Machine");
                                }
                        }
                }
        }
}