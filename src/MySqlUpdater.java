import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUpdater{
	String dbtime;
	String dbUrl = "jdbc:mysql://alan-wright.com/alanwrig_comics";
	String username = "alanwrig";
	String password = "@123Victory";
	String dbClass = "com.mysql.jdbc.Driver";
	
	public MySqlUpdater(List<xmlItem> xmlItems) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			//Open connection
			Connection con = DriverManager.getConnection (dbUrl, username, password);
			
			//Create statement
			Statement stmt = con.createStatement();
			PreparedStatement pstmt;
			int count = -1;
			for(xmlItem comic : xmlItems){
				int paramCount = 0;
				ArrayList<String> stringParams = new ArrayList<String>();
				ArrayList<Integer> intParams = new ArrayList<Integer>();
				ArrayList<Integer> intLocs = new ArrayList<Integer>();
				
				count++;
				if(count > 10)
					break;
				
				//Get the imgurl for the comic, this should be unique.
				String image_link = comic.getImgURL();
				
				//Query for the comic
				String query = "SELECT image_link FROM comics WHERE image_link = \"" + image_link + "\"";
				ResultSet rs = stmt.executeQuery(query);
				
				//If it exists in the DB, skip
				if(rs.first()){
					System.out.println("Found");
					continue;
				}
				
				//Otherwise, get all data
				String queryBegin = "INSERT into comics (";
				String queryEnd = "VALUES (";
				
				//Title
				String title = comic.getTitle();
				if(!title.equals(null)){
					queryBegin += "title"; //add the param name
					queryEnd += "?"; //this is for addition later
					stringParams.add(title); //save the value
					paramCount++; //Up page count
				}
				
				//Generic Title
				String gen_title = comic.getGenericTitle();
				if(!gen_title.equals(null)){
					queryBegin += ", generic_title";
					queryEnd += ", ?";
					stringParams.add(gen_title);
					paramCount++;
				}
				
				//Issue Num
				int issue = comic.getIssueNum();
				if(issue > -1){
					queryBegin += ", issue_num";
					queryEnd += ", ?"; 
					intParams.add(issue);
					paramCount++;
					intLocs.add(paramCount);
				}
				
				//Short Description
				String short_descr = comic.getShortDescription();
				if(!short_descr.equals(null)){
					queryBegin += ", short_description";
					queryEnd += ", ?";
					stringParams.add(short_descr);
					paramCount++;
				}
				
				//Publisher
				String publisher = comic.getPublisher();
				if(!publisher.equals(null)){
					queryBegin += ", publisher";
					queryEnd += ", ?"; 
					stringParams.add(publisher);
					paramCount++;
				}
				
				//Print Date
				String print_date = comic.getPrintPubDateNum();
				if(!print_date.equals(null)){
					queryBegin += ", print_date";
					queryEnd += ", ?"; 
					stringParams.add(print_date);
					paramCount++;
				}
				
				//Digital Date
				String digital_date = comic.getDigitalPubDateNum();
				if(!digital_date.equals(null)){
					queryBegin += ", digital_date";
					queryEnd += ", ?"; 
					stringParams.add(digital_date);
					paramCount++;
				}
				
				//Thumbnail URL
				String thumb = comic.getthumbURL();
				if(!thumb.equals(null)){
					queryBegin += ", thumbnail";
					queryEnd += ", ?"; 
					stringParams.add(thumb);
					paramCount++;
				}
				
				//Long Description
				String descr = comic.getLongDescription();
				if(!descr.equals(null)){
					queryBegin += ", long_description";
					queryEnd += ", ?"; 
					stringParams.add(descr);
					paramCount++;
				}
				
				//Link URL
				String link = comic.getLink();
				if(!link.equals(null)){
					queryBegin += ", link";
					queryEnd += ", ?";
					stringParams.add(link);
					paramCount++;
				}
				
				//Cover Artists
				ArrayList<String> coverArtists = comic.getCoverArtists();
				if(!coverArtists.isEmpty()){
					queryBegin += ", cover_artist1";
					queryEnd += ", ?"; 
					stringParams.add(coverArtists.get(0));
					paramCount++;
				}
				
				//Authors
				ArrayList<String> authors = comic.getAuthors();
				if(!authors.isEmpty()){
					queryBegin += ", author1";
					queryEnd += ", ?";
					stringParams.add(authors.get(0));
					paramCount++;
				}
				
				//Inker
				ArrayList<String> inker = comic.getInkers();
				if(!inker.isEmpty()){
					queryBegin += ", inker1";
					queryEnd += ", ?";
					stringParams.add(inker.get(0));
					paramCount++;
				}
				
				//Colors
				ArrayList<String> color = comic.getColors();
				if(!color.isEmpty()){
					queryBegin += ", color1";
					queryEnd += ", ?";
					stringParams.add(color.get(0));
					paramCount++;
				}
				
				//Number of pages
				int pages = comic.getPageCount();
				if(pages > 0){
					queryBegin += ", page_count";
					queryEnd += ", ?"; 
					intParams.add(pages);
					paramCount++;
					intLocs.add(paramCount);
				}
				
				//Add image link
				queryBegin += ", image_link";
				queryEnd += ", ?";
				stringParams.add(image_link);
				paramCount++;
				
				//Is the comic digital?
				boolean isDigital = comic.isDigital();
				queryBegin += ", isDigital) ";
				queryEnd += ", ?)"; 
				paramCount++;
				System.out.println(paramCount);
				
				//Combine
				query = queryBegin + queryEnd;
				pstmt = con.prepareStatement(query);
				//System.out.println(query);
				
				//Add params
				int stringCount = 0;
				int intCount = 0;
				for(int i = 1; i <= paramCount; i++){
					//Int location
					if(intLocs.indexOf(i) != -1){
						pstmt.setInt(i, intParams.get(intCount));
						intCount++;
					}
					//isDitial
					else if(i == paramCount){
						pstmt.setBoolean(i, isDigital);
					}
					//String
					else{
						pstmt.setString(i, stringParams.get(stringCount));
						stringCount++;
					}
				}
				
				//Update table
				pstmt.executeUpdate();
				
			}
			con.close();
		} //end try
		
			catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}  //end class