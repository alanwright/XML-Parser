import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class driver{
	
	private static String XML_URL = "http://feeds.feedburner.com/ComixologyDigitalComics?fmt=xml";
	private static boolean PRINT_OUT = false;
	private static List<xmlItem> xmlItems;
	
	//Testing...
	public static void main(String[] args){
		
		try{
			//Parse xml
			xmlParser xmlReader = new xmlParser(XML_URL);
			long initTime = System.currentTimeMillis();
			xmlItems = xmlReader.getItems();
			long finalTime = System.currentTimeMillis();
			
			System.out.println("XML parsing of " + xmlItems.size() + " comics complete in " +
					(finalTime - initTime) + " milliseconds.");
			
			//Check if comic is in database, if it is delete from list
			
			//Parse HTML
			htmlParser htmlReader = new htmlParser();
			htmlReader.parseItems(xmlItems);
			
			//Upload images
			FTP_FileUpload ftp = new FTP_FileUpload(xmlItems);
			
			//Update Database
			MySqlUpdater sql = new MySqlUpdater(xmlItems);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not parse XML File");
			return;
		}
			
		
		if(PRINT_OUT){
			try{
				System.out.print("Writing output to file...");
				//Open Output
				FileOutputStream fstream = new FileOutputStream("XML_Parse_out.txt");
				PrintStream out = new PrintStream(fstream);
				
				//Print items
				int count = 0;
				for(xmlItem x : xmlItems){
					out.println(x.toString());
					
					count++;
				}
				
				//Finish
				out.close();
				System.out.println("Complete!");
			} catch(FileNotFoundException e){
				System.out.println("File not found!");
			} 
		}
		
	}
}