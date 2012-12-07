import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class htmlParser {
	
	//HTML URL to parse.
	private String htmlUrl;
	private xmlItem item;

	public htmlParser(xmlItem item) {
		this.htmlUrl = item.getLink();
		this.item = item;
	}


	public void getItems() throws Exception {
		// SAX parse XML data
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		htmlParseHandler handler = new htmlParseHandler(item);
		
		saxParser.parse(htmlUrl, handler);
		
	}

}