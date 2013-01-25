import java.util.ArrayList;


public class xmlItem {

	//Variables for xmlItem
	private String title;
	private String genericTitle;
	private String link;
	private String publisher;
	private String pubDate;
	private String thumbURL;
	private String imgURL;
	private String imgDlURL;
	private String shortDescription;
	private String longDescription;
	private String printPubDateNum;
	private String digitalPubDateNum;
	private String imgExtension;
	private boolean isDigital;
	private int pageCount;
	private int issueNum;
	private String issueStr;
	private ArrayList<String> coverArtists;
	private ArrayList<String> authors;
	private ArrayList<String> artists;
	private ArrayList<String> inkers;
	private ArrayList<String> colors;
	
	public xmlItem(){
		//Initialize all instance variables. 
		title = "";
		genericTitle = "";
		link = "";
		publisher = "";
		pubDate = "";
		thumbURL = "";
		shortDescription = "";
		longDescription = "";
		printPubDateNum = "";
		digitalPubDateNum = "";
		imgExtension = "";
		imgURL = "";
		imgDlURL = "";
		isDigital = false;
		pageCount = -1;
		issueNum = -1;
		issueStr = "";
		coverArtists = new ArrayList<String>();
		authors = new ArrayList<String>();
		artists = new ArrayList<String>();
		inkers = new ArrayList<String>();
		colors = new ArrayList<String>();
	}

	public String getTitle() {
		return title;
	}
	
	public String getGenericTitle() {
		return genericTitle;
	}

	public void setTitle(String title) {
		//Issue number will be after a # sign
		int issueIndex = title.indexOf('#');
		
		//If there is an issue number...
		if(issueIndex >=0){
			
			//try to parse the issue number regularly #XXX
			try{
				this.issueNum = Integer.parseInt(title.substring(issueIndex+1));
				this.issueStr = title.substring(issueIndex+1);
				
				//Catch the exception if it is formatted differently
			} catch(NumberFormatException e){
				
				//Possible formats: #XX.X or # X (out of X)
				int periodIndex = title.lastIndexOf('.');
				int parenIndex = title.lastIndexOf('(');
				
				//If there is a period after the #, parse accordingly
				if(periodIndex > issueIndex){
					this.issueStr = title.substring(issueIndex+1);
					this.issueNum = Integer.parseInt(title.substring(issueIndex+ 1, periodIndex));
				}

				//If there is a left paren ( after the #, parse accordingly
				else if(parenIndex > issueIndex && issueIndex > 0){
					this.issueNum = Integer.parseInt(title.substring(issueIndex+ 1, parenIndex-1));
					this.issueStr = title.substring(issueIndex+ 1, parenIndex);
				}
				
			}
			//Parse the title
			int leftParenIndex = title.lastIndexOf('(');
			int rightParenIndex = title.lastIndexOf(')');

			//If there is both a left and right paren, delete the middle
			if(leftParenIndex < rightParenIndex && leftParenIndex > 0){
				String toBeReplaced = title.substring(leftParenIndex, rightParenIndex + 1);
				this.title = title.replace(toBeReplaced, "");
				issueIndex = this.title.indexOf('#');
				this.genericTitle = this.title.substring(0, issueIndex);
			}
			//Otherwise parse normally
			else{
				this.title = title; 
				this.genericTitle = title.substring(0, issueIndex);
			}
		}
		else{
			//Parse the title
			int leftParenIndex = title.lastIndexOf('(');
			int rightParenIndex = title.lastIndexOf(')');

			//If there is both a left and right paren, delete the middle
			if(leftParenIndex < rightParenIndex && leftParenIndex > 0){
				String toBeReplaced = title.substring(leftParenIndex, rightParenIndex + 1);
				this.title = title.replace(toBeReplaced, "");
				this.genericTitle = this.title;
			}
			//Otherwise parse normally
			else{
				this.title = title;
				this.genericTitle = title;
			}
		}
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link.replace("?utm_campaign=rss", "");
	}
	
	public String getImgExtension(){
		return this.imgExtension;
	}
	
	public void setImgExtension(String ext){
		this.imgExtension = ext;
		this.imgURL = "http://alan-wright.com/comics/" + ext;
	}
	
	public String getImgURL(){
		return this.imgURL;
	}
	
	public String getImgDlURL(){
		return this.imgDlURL;
	}
	
	public void setImgDlURL(String url){
		this.imgDlURL = url;
	}
	
	
	@Override
	public String toString() {
		
		return String.format("Title: %s\nGeneric Title: %s\nIssue #: %d\nIssue Str: %s\nShort Description: %s\nPublisher: %s\n" +
				"Publish Date: %s\nPrint Date: %s\nDigital Date: %s\nThumbnail url: %s\n" +
				"Link: %s\nLong Description: %s\nCover Artists: %s\nAuthors: %s\nInkers: %s\nColors: %s\n" +
				"Page Count: %d\nDigital: %B\nImage Extension: %s\nImage Link: %s\n", title, genericTitle, issueNum, issueStr, shortDescription, publisher, pubDate, printPubDateNum, digitalPubDateNum, thumbURL, link, longDescription, coverArtists, authors, inkers, colors, pageCount, isDigital, imgExtension, imgURL);
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
		this.longDescription = longDescription;
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
		//Input format: MM/DD/YYYY
		//Output format: YYYY-MM-DD
		String output = printDate.substring(6) + "-" + printDate.substring(0, 2) + "-" + printDate.substring(3, 5);
		this.printPubDateNum = output;
	}
	
	public String getPrintPubDateNum() {
		return printPubDateNum;
	}
	
	public int getIssueNum() {
		return issueNum;
	}
	
	public void setDigitalPubDateNum(String digitalDate) {
		//Input format: MM/DD/YYYY
		//Output format: YYYY-MM-DD
		String output = digitalDate.substring(6) + "-" + digitalDate.substring(0, 2) + "-" + digitalDate.substring(3, 5);
		this.digitalPubDateNum = output;
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
