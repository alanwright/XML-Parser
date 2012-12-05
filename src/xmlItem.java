
public class xmlItem {

	//Variables for xmlItem
	private String title;
	private String link;
	private String publisher;
	private String pubDate;
	private String thumbURL;
	private String description;

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
		return String.format("Title: %s\nDescription: %s\nPublisher: %s\n" +
				"Publish Date: %s\nThumbnail url: %s\nLink: %s\n\n", title, description, publisher, pubDate, thumbURL, link);
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
