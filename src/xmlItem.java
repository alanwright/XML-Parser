import java.util.ArrayList;
import java.util.Arrays;


public class xmlItem {

	//Variables for xmlItem
	private String title;
	private String link;
	private String publisher;
	private String pubDate;
	private String thumbURL;
	private String shortDescription;
	private String longDescription;
	private String printPubDateNum;
	private String digitalPubDateNum;
	private boolean isDigital;
	private int pageCount;
	private ArrayList<String> coverArtists;
	private ArrayList<String> authors;
	private ArrayList<String> artists;
	private ArrayList<String> inkers;
	private ArrayList<String> colors;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link.replace("?utm_campaign=rss", "");
	}
	
	@Override
	public String toString() {
		//Check cover artists
		String coverArt = "";
		if(coverArtists != null){
			for (String s : coverArtists)
			{
			    coverArt += s + "\t";
			}
		}
		else{
			coverArt = "none";
		}
		
		//Check authors
		String auth = "";
		if(authors != null){
			for (String s : authors)
			{
			    auth += s + "\t";
			}
		}
		else{
			auth = "none";
		}
		
		return String.format("Title: %s\nDescription: %s\nPublisher: %s\n" +
				"Publish Date: %s\nThumbnail url: %s\nLink: %s\n" +
				"Cover Artists: %s\nAuthors: %s\n", title, shortDescription, publisher, pubDate, thumbURL, link, coverArt, auth);
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	
	public void setthumbURL(String thumbURL) {
		this.thumbURL = thumbURL;
	}
	
	public String getthumbURL() {
		return thumbURL;
	}
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public void setLongDescription(String longDescription) {
		this.longDescription = shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	
	public void setCoverArtists(ArrayList<String> coverArtists) {
		this.coverArtists = coverArtists;
	}
	
	public ArrayList<String> getCoverArtists() {
		return coverArtists;
	}
	
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	
	public ArrayList<String> getAuthors() {
		return authors;
	}
	
	public void setArtists(ArrayList<String> artists) {
		this.artists = artists;
	}
	
	public ArrayList<String> getArtists() {
		return artists;
	}
	
	public void setInkers(ArrayList<String> inkers) {
		this.inkers = inkers;
	}
	
	public ArrayList<String> getInkers() {
		return inkers;
	}
	
	public void setColors(ArrayList<String> colors) {
		this.colors = colors;
	}
	
	public ArrayList<String> getColors() {
		return colors;
	}
	
	public void setPrintPubDateNum(String printDate) {
		this.printPubDateNum = printDate;
	}
	
	public String getPrintPubDateNum() {
		return printPubDateNum;
	}
	
	public void setDigitalPubDateNum(String digitalDate) {
		this.digitalPubDateNum = digitalDate;
	}
	
	public String getDigitalPubDateNum() {
		return digitalPubDateNum;
	}
	
	public void setDigital(boolean isDigital) {
		this.isDigital = isDigital;
	}
	
	public boolean isDigital() {
		return isDigital;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}
}
