import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class xmlParser {
	
	//XML URL to parse.
	private String xmlUrl;
	
	private static String XML_URL = "http://feeds.feedburner.com/ComixologyDigitalComics?fmt=xml";

	public xmlParser(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}


	public List<xmlItem> getItems() throws Exception {
		// SAX parse XML data
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		xmlParseHandler handler = new xmlParseHandler();
		
		saxParser.parse(xmlUrl, handler);

		return handler.getItems();
		
	}
	
	//Testing...
	public static void main(String[] args){
		
		try{
			//Open Output
			FileOutputStream fstream = new FileOutputStream("XML_Parse_out.txt");
			PrintStream out = new PrintStream(fstream);
			
			//Parse
			xmlParser xmlReader = new xmlParser(XML_URL);
			long initTime = System.currentTimeMillis();
			List<xmlItem> xmlItems = xmlReader.getItems();
			long finalTime = System.currentTimeMillis();
			
			//Print items
			for(xmlItem x : xmlItems){
				out.println(x.toString());
			}
			
			//Finish
			out.close();
			System.out.println("Execution Complete. Parsing of " + xmlItems.size() + " comics took " +
					(finalTime - initTime) + " milliseconds.");
		} catch(IOException e){
			System.out.println("Could not open output file...");
		} catch(Exception e){
			System.out.println("Could not parse XML File");
		}
	}

}
