import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class xmlParseHandler extends DefaultHandler {

	//List of parsed items. 
	private List<xmlItem> xmlItems;
	
	// Used to reference the current item while parsing
	private xmlItem currentItem;
	
	//Indicates what you are currently parsing
	private boolean parsingTitle;
	private boolean parsingLink;
	private boolean parsingPublisher;
	private boolean parsingPubDate;
	private boolean parsingDescription;
	private boolean parsingContentEncoded;
	
	//Constants used to parse thumbnail url
	private String EXAMPLE_THUMB_URL = "http://iphone.comixology.com/covers/thumbnails/OCT120587_1_t.jpg";
	private String GENERIC_THUMB_URL = "http://iphone.comixology.com/covers/thumbnails";
	
	public xmlParseHandler() {
		xmlItems = new ArrayList<xmlItem>();
	}
	
	public List<xmlItem> getItems() {
		return xmlItems;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("item".equals(qName)) {
			currentItem = new xmlItem();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
		} else if ("feedburner:origLink".equals(qName)) {
			parsingLink = true;
		} else if("category".equals(qName)){
			parsingPublisher = true;
		} else if("description".equals(qName)){
			parsingDescription = true;
		} else if("pubDate".equals(qName)){
			parsingPubDate = true;
		} else if("content:encoded".equals(qName)){
			parsingContentEncoded = true;
		}
		else if("thumb".equalsIgnoreCase(qName) && parsingContentEncoded){
			if(currentItem!=null){
				currentItem.setthumbURL(attributes.getValue("src"));
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("item".equals(qName)) {
			xmlItems.add(currentItem);
			currentItem = null;
		} else if ("title".equals(qName)) {
			parsingTitle = false;
		} else if ("feedburner:origLink".equals(qName)) {
			parsingLink = false;
		} else if("category".equals(qName)){
			parsingPublisher = false;
		} else if("description".equals(qName)){
			parsingDescription = false;
		} else if("pubDate".equals(qName)){
			parsingPubDate = false;
		} else if("content:encoded".equals(qName)){
			parsingContentEncoded = false;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (parsingTitle) {
			if (currentItem != null)
				currentItem.setTitle(new String(ch, start, length));
		} else if (parsingLink) {
			if (currentItem != null) {
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		} else if(parsingPublisher){
			if(currentItem != null){
				currentItem.setPublisher(new String(ch, start, length));
			}
		} else if(parsingPubDate){
			if(currentItem != null){
				if(length > 20){
					currentItem.setPubDate(new String(ch, start, length-15)); //Trim off extra date info
				}
				else{
					currentItem.setPubDate(new String(ch, start, length));
				}
			}
		} else if(parsingDescription){
			if(currentItem != null){
				currentItem.setDescription(new String(ch, start, length));
			}
		} else if(parsingContentEncoded){
			if(currentItem != null){
				
				//Get everything after content encoded
				String junk = new String(ch);
				
				//Find where the generic url starts
				int thumbUrlStart = junk.indexOf(GENERIC_THUMB_URL);
				
				//If it was found...
				if(thumbUrlStart > 0){
					
					//Get the url, move on
					currentItem.setthumbURL(new String(ch, thumbUrlStart, EXAMPLE_THUMB_URL.length()));
					parsingContentEncoded = false;
				}
			}
		}
	}
}
