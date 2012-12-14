import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;

/**
 * This class uploads file in the local machine to the remote FTP Server using FTP4j client library
 */
public class FTP_FileUpload
{
        public FTP_FileUpload(String filePath)
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
                                    System.out.println("Current Working directory = " + client.currentDirectory());
                                    
                                    //Define the File with complete path to be uploaded
                                    File fileUpload = new File(filePath);
                                    System.out.println("Uploading the " + filePath + " to Remote Machine");
                                    
                                    //Upload the file
                                    client.upload(fileUpload);
                                    System.out.println("Successfully Uploaded the " + filePath + " File to Remote Machine");
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
                System.exit(0);
        }
}