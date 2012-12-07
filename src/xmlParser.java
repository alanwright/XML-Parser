import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class xmlParser {
	
	//XML URL to parse.
	private String xmlUrl;

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

}
